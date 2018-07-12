package com.empowerment.salesrobot.ui.activity;


import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.AgencyAffairsAdapter;
import com.empowerment.salesrobot.ui.model.AgencyAffairsBean;
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
    private int noPage = 1;
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
        Map<String,String> params = new HashMap<>();
        params.put("storeId","1");
        params.put("page",String.valueOf(noPage));
        params.put("rows","10");
        params.put("saleId","1");
        params.put("type",type);
        MyOkhttp.Okhttp(context, Url.AFFAIRS, dialog, params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
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
        Resources resource = context.getResources();
        csl1 = resource.getColorStateList(R.color.app_main_default);
        csl2 = resource.getColorStateList(R.color.default_color);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new AgencyAffairsAdapter(context,mList);
        xRecyclerView.setAdapter(mAdapter);
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
                ok.setVisibility(View.VISIBLE);
                type = "1";
                mList.clear();
                loadData();
                break;
            case R.id.tv_personal_agency:
                company.setBackgroundResource(R.drawable.shape_company_agency_background);
                personal.setBackgroundResource(R.drawable.shape_personal_agency_select_background);
                company.setTextColor(csl2);
                personal.setTextColor(csl1);
                ok.setVisibility(View.GONE);
                type = "0";
                mList.clear();
                loadData();
                break;
            case R.id.title_OK:
//                MyApplication.openActivity(context,NewEditAgencyAffairsActivity.class);
                break;
        }
    }
}
