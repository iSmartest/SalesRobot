package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.FieldRecordInfoAdapter;
import com.empowerment.salesrobot.ui.model.FieldRecordInfoBean;
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

import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/11.
 * Description:
 */
public class FieldRecordInfoActivity extends BaseActivity {

    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.record_info_list)
    XRecyclerView xRecyclerView;
    TextView mName,mSex,mAge,mPhone;
    LinearLayout item01,item02;
    private View headView;
    private String type,id;
    private int nowPage = 1;
    private List<FieldRecordInfoBean.DataBean.ConList> mList = new ArrayList<>();
    private FieldRecordInfoAdapter mAdapter;
    @Override
    protected int getLauoutId() {
        return R.layout.activity_field_record_info;
    }

    @Override
    protected void loadData() {
        Map<String,String> params = new HashMap<>();
        params.put("storeId", SPUtil.getString(context,STORE_ID));
        params.put("cId",id);
        params.put("page",String.valueOf(nowPage));
        MyOkhttp.Okhttp(context, Url.RECORD_DETAIL, "加载中...", params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Gson gson = new Gson();
                FieldRecordInfoBean fieldRecordInfoBean = gson.fromJson(response,FieldRecordInfoBean.class);
                if (result.equals("1")){
                    ToastUtils.makeText(context,resultNote);
                    return;
                }
                List<FieldRecordInfoBean.DataBean.ConList> conLists = fieldRecordInfoBean.getData().getConList();
                if (conLists != null && !conLists.isEmpty() && conLists.size() > 0){
                    mList.addAll(conLists);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void initView() {
        title.setText("现场记录");
        titleBack.setVisibility(View.VISIBLE);
        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        headView = LayoutInflater.from(context).inflate(R.layout.head_field_record_info,null,false);
        mName = headView.findViewById(R.id.tv_record_info_name);
        mSex = headView.findViewById(R.id.tv_record_info_sex);
        mAge = headView.findViewById(R.id.tv_record_info_age);
        mPhone = headView.findViewById(R.id.tv_record_info_phone);
        item01 = headView.findViewById(R.id.ll_item01);
        item02 = headView.findViewById(R.id.ll_item02);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        if (headView != null) {
            xRecyclerView.addHeaderView(headView);
        }
        mAdapter = new FieldRecordInfoAdapter(context,mList);
        xRecyclerView.setAdapter(mAdapter);
        if (type.equals("1")){
            item01.setVisibility(View.VISIBLE);
            item02.setVisibility(View.VISIBLE);
            mName.setText("姓名：" + getIntent().getStringExtra("name"));
            mSex.setText("性别：" + getIntent().getStringExtra("sex"));
            mAge.setText("年龄：" + getIntent().getStringExtra("age"));
            mPhone.setText("手机号：" + getIntent().getStringExtra("phone"));
        }else {
            item01.setVisibility(View.GONE);
            item02.setVisibility(View.GONE);
        }

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                nowPage = 1;
                mList.clear();
                loadData();
                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                nowPage++;
                loadData();
                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
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
}
