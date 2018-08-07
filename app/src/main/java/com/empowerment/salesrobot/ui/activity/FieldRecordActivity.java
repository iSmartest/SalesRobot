package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.FieldRecordAdapter;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.ui.model.FieldRecordBean;
import com.empowerment.salesrobot.uitls.SPUtil;
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
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/4.
 * Description:
 */
public class FieldRecordActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_empty_data)
    ImageView mEmpty;
    @BindView(R.id.record_recycler)
    XRecyclerView xRecyclerView;
    private int nowPage = 1;
    private int rows = 10;
    private List<FieldRecordBean.DataBean.ConsultList> mList = new ArrayList<>();
    FieldRecordAdapter mAdapter;
    @Override
    protected int getLauoutId() {
        return R.layout.activity_field_record;
    }

    @Override
    protected void loadData() {

    }
    protected void getdata() {
        Map<String,String> params = new HashMap<>();
        params.put("page",String.valueOf(nowPage));
        params.put("rows",rows+"");
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put(SALE_ID, SPUtil.getString(context,SALE_ID));
        MyOkhttp.Okhttp(context, Url.RECORD_LIST, "加载中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            FieldRecordBean fieldRecordBean = gson.fromJson(response,FieldRecordBean.class);
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
                return;
            }
            List<FieldRecordBean.DataBean.ConsultList> consultLists = fieldRecordBean.getData().getConsultList();
            if (consultLists != null && !consultLists.isEmpty() && consultLists.size() > 0){
                mList.addAll(consultLists);
                mAdapter.notifyDataSetChanged();
            }
            if (consultLists.size() < rows){
                xRecyclerView.noMoreLoading();
            }
        });
    }

    @Override
    protected void initView() {
        title.setText("现场记录");
        titleBack.setVisibility(View.VISIBLE);
        mEmpty.setVisibility(View.GONE);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new FieldRecordAdapter(context,mList);
        xRecyclerView.setAdapter(mAdapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                nowPage = 1;
                mList.clear();
                getdata();
                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                nowPage++;
                getdata();
                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }
        });

        xRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(xRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition() - 1;
                if (position < 0 | position >= mList.size()){
                    return;
                }
                Bundle bundle = new Bundle();
                if (mList.get(position).getType() == 1){
                    bundle.putString("type","1");
                    bundle.putString("name",mList.get(position).getName());
                    bundle.putString("sex",mList.get(position).getSex());
                    bundle.putString("age",mList.get(position).getAge());
                    bundle.putString("phone",mList.get(position).getPhone());
                }else {
                    bundle.putString("type","0");
                }
                bundle.putString("id",mList.get(position).getId()+"");
                MyApplication.openActivity(context,FieldRecordInfoActivity.class,bundle);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.title_Img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        nowPage = 1;
        mList.clear();
        mAdapter.notifyDataSetChanged();
        getdata();
    }
}
