package com.empowerment.salesrobot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.MyUnderstandAdapter;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.ui.model.MyUnderstandBean;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.view.swipeLayout.SwipeLayoutManager;
import com.example.xrecyclerview.XRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.PAGE;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;


public class MyUnderstandActivity extends BaseActivity implements MyUnderstandAdapter.ModifyCountInterface {
    private static final String TAG = "MyExperienCeActivity";
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_OK)
    TextView titleOK;
    @BindView(R.id.swipeListView)
    XRecyclerView swipeListView;
    private int nowPage = 1;
    private List<MyUnderstandBean.DataBean.ExperienceListBean> mList = new ArrayList<>();
    private MyUnderstandAdapter mAdapter;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_my_understand;
    }

    @Override
    protected void loadData() {

    }

    protected void getdata() {
        Map<String, String> params = new HashMap<>();
        params.put(SALE_ID, SPUtil.getString(context,SALE_ID));
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put(PAGE, nowPage + "");
        MyOkhttp.Okhttp(context, Url.SALES_MANS, "加载中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            MyUnderstandBean myUnderstandBean = gson.fromJson(response,MyUnderstandBean.class);
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
                return;
            }
            List<MyUnderstandBean.DataBean.ExperienceListBean> experienceListBeans = myUnderstandBean.getData().getExperienceList();
            if (experienceListBeans != null && !experienceListBeans.isEmpty() && experienceListBeans.size() > 0){
                mList.addAll(experienceListBeans);
                mAdapter.notifyDataSetChanged();
                if (experienceListBeans.size() < 10){
                    swipeListView.noMoreLoading();
                }
            }
        });
    }

    @Override
    protected void initView() {
        title.setText("我的心得");
        titleBack.setVisibility(View.VISIBLE);
        titleOK.setVisibility(View.VISIBLE);
        titleOK.setText("编辑");
        swipeListView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new MyUnderstandAdapter(context, mList);
        mAdapter.setModifyCountInterface(this);
        swipeListView.setAdapter(mAdapter);
        swipeListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // 如果listView跟随手机拖动，关闭已经打开的SwipeLayout
                    SwipeLayoutManager.getInstance().closeOpenInstance();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        swipeListView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                nowPage = 1;
                mList.clear();
                getdata();
                mAdapter.notifyDataSetChanged();
                swipeListView.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                nowPage++;
                getdata();
                mAdapter.notifyDataSetChanged();
                swipeListView.refreshComplete();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.title_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.title_OK:
                MyApplication.openActivity(context,EditUnderstandActivity.class);
                break;
        }
    }


    @Override
    public void childDelete(final int position, String itemId) {
        Map<String,String> params = new HashMap<>();
        params.put("eid",itemId);
        params.put(SALE_ID,SPUtil.getString(context,SALE_ID));
        params.put(STORE_ID,SPUtil.getString(context,STORE_ID));
        MyOkhttp.Okhttp(context, Url.DELET_SALE_MANS, "", params, (response, result, resultNote) -> {
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
                return;
            }
            mList.remove(position-1);
            ToastUtils.makeText(context,resultNote);
            mAdapter.notifyDataSetChanged();
            Intent intent = new Intent();
            intent.setAction("com.empowerment.salesrobot.mine");
            getApplicationContext().sendBroadcast(intent);
        });
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
