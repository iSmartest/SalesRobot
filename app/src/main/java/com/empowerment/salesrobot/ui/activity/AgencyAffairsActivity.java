package com.empowerment.salesrobot.ui.activity;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.dialog.ProgressDialog;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.AgencyAffairsAdapter;
import com.empowerment.salesrobot.ui.model.AgencyAffairsBean;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.TimeUtils;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.example.xrecyclerview.XRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;

/**
 * 待办事宜
 */
public class AgencyAffairsActivity extends BaseActivity {
    private static final String TAG = "TodoActivity";
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_OK)
    TextView ok;
    @BindView(R.id.tv_company_agency)
    TextView company;
    @BindView(R.id.tv_personal_agency)
    TextView personal;
    @BindView(R.id.affairs_list)
    XRecyclerView xRecyclerView;
    private ColorStateList csl1,csl2;
    private int nowPage = 1;
    private String type = "1";
    private List<AgencyAffairsBean.DataBean.AgentList> mList = new ArrayList<>();
    private AgencyAffairsAdapter mAdapter;
    //Model：定义的数据
    @Override
    protected int getLauoutId() {
        return R.layout.activity_agency_affairs;
    }

    @Override
    protected void loadData() {
        mList.clear();
        mAdapter.notifyDataSetChanged();
        Map<String,String> params = new HashMap<>();
        params.put("storeId",SPUtil.getString(context, "storeId"));
        params.put("page",String.valueOf(nowPage));
        params.put("rows","10");
        params.put("saleId",SPUtil.getString(context, SALE_ID));
        params.put("type",type);
        MyOkhttp.Okhttp(context, Url.AFFAIRS, ProgressDialog.createLoadingDialog(context, "加载中....."), params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                xRecyclerView.refreshComplete();
                Gson gson = new Gson();
                AgencyAffairsBean agencyAffairsBean = gson.fromJson(response,AgencyAffairsBean.class);
                if (result.equals("1")){
                    ToastUtils.makeText(context,resultNote);
                    return;
                }
                List<AgencyAffairsBean.DataBean.AgentList> agentLists = agencyAffairsBean.getData().getAgentList();
                if (agentLists != null && !agentLists.isEmpty() && agentLists.size() > 0){
                    mList.addAll(agentLists);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void initView() {
        title.setText("待办事宜");
        titleBack.setVisibility(View.VISIBLE);
        ok.setText("添加");
        Resources resource = context.getResources();
        csl1 = resource.getColorStateList(R.color.app_main_default);
        csl2 = resource.getColorStateList(R.color.default_color);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new AgencyAffairsAdapter(context,mList);
        xRecyclerView.setAdapter(mAdapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                nowPage = 1;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                loadData();
            }

            @Override
            public void onLoadMore() {
                nowPage++;
                loadData();
            }
        });
        xRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(xRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition() - 1;
                if (position < 0 | position >= mList.size()) {
                    return;
                }

                if (mList.get(position).getIsFinish() == 1){
                    Bundle bundle = new Bundle();
                    bundle.putString("time", TimeUtils.transferLongToDate(mList.get(position).getEndTime()));
                    bundle.putString("content", mList.get(position).getContent());
                    bundle.putString("isFinish", mList.get(position).getIsFinish()+"");
                    MyApplication.openActivity(context, AgencyAffairsInfoActivity.class, bundle);
                }else {
                    sendRead(mList.get(position).getId(),mList.get(position).getType(),mList.get(position).getEndTime()
                            ,mList.get(position).getContent(),mList.get(position).getIsFinish());
                }
            }
        });
    }

    private void sendRead(final int id, final int type, final Long endTime, final String content, final int isFinish) {
        Map<String,String> params = new HashMap<>();
        params.put("aId",id+"");
        params.put("sId", SPUtil.getString(context,SALE_ID));
        params.put("aType",String.valueOf(type));//待办类型
        params.put("storeId",SPUtil.getString(context,STORE_ID));
        params.put("type", "1");//1为阅读，2为完结
        MyOkhttp.Okhttp(context, Url.READ_OR_FINISH, dialog, params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Log.i(TAG, "onRequestComplete: " + response);
                if (result.equals("0")){
                    Intent intent = new Intent();
                    intent.setAction("com.empowerment.salesrobot.home");
                    getApplicationContext().sendBroadcast(intent);
                }
                Bundle bundle = new Bundle();
                bundle.putString("time", TimeUtils.transferLongToDate(endTime));
                bundle.putString("content", content);
                bundle.putString("type", type+"");
                bundle.putString("id", id+"");
                bundle.putString("isFinish", isFinish+"");
                MyApplication.openActivity(context, AgencyAffairsInfoActivity.class, bundle);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.tv_company_agency, R.id.tv_personal_agency,R.id.title_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.tv_company_agency:
                company.setBackgroundResource(R.drawable.shape_company_agency_select_background);
                personal.setBackgroundResource(R.drawable.shape_personal_agency_background);
                company.setTextColor(csl1);
                personal.setTextColor(csl2);
                ok.setVisibility(View.GONE);
                type = "1";
                mList.clear();
                mAdapter.notifyDataSetChanged();
                loadData();
                break;
            case R.id.tv_personal_agency:
                company.setBackgroundResource(R.drawable.shape_company_agency_background);
                personal.setBackgroundResource(R.drawable.shape_personal_agency_select_background);
                company.setTextColor(csl2);
                personal.setTextColor(csl1);
                ok.setVisibility(View.VISIBLE);
                type = "0";
                mList.clear();
                mAdapter.notifyDataSetChanged();
                loadData();
                break;
            case R.id.title_OK:
                MyApplication.openActivity(context,NewAddAgencyAffairsActivity.class);
                break;
        }
    }

}
