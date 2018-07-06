package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.MyUnderstandAdapter;
import com.empowerment.salesrobot.ui.model.MyUnderstandBean;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.view.swipeLayout.SwipeLayoutManager;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.PAGE;
import static com.empowerment.salesrobot.config.BaseUrl.PAGE_SIZI;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;


public class MyUnderstandActivity extends BaseActivity implements MyUnderstandAdapter.ModifyCountInterface {
    private static final String TAG = "MyExperienCeActivity";
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_OK)
    TextView titleOK;
    @BindView(R.id.swipeListView)
    RecyclerView swipeListView;

    private List<MyUnderstandBean.DataBean.ExperienceListBean> mList = new ArrayList<>();
    private MyUnderstandAdapter mAdapter;
    private Map<String, String> map = new HashMap<>();
    private Map<String, String> exMap = new HashMap<>();
    private EditText et_search;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_my_understand;
    }

    @Override
    protected void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put(SALE_ID, "1");
//        params.put(SALE_ID, SPUtil.getString(context,SALE_ID));
        params.put(PAGE, PAGE_SIZI + "");
        MyOkhttp.Okhttp(context, Url.SALES_MANS, dialog, params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
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
        params.put(SALE_ID,"1");
//        params.put(SALE_ID,SPUtil.getString(context,SALE_ID));

        MyOkhttp.Okhttp(context, Url.DELET_SALE_MANS, dialog, params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                if (result.equals("1")){
                    ToastUtils.makeText(context,resultNote);
                    return;
                }
                mList.remove(position);
                ToastUtils.makeText(context,resultNote);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
