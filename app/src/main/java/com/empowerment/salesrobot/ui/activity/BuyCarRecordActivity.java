package com.empowerment.salesrobot.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.BuyCarAdapter;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.ui.model.BuyCarRecordBean;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.example.xrecyclerview.XRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.C_ID;
import static com.empowerment.salesrobot.config.BaseUrl.PAGE;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.TYPE;

public class BuyCarRecordActivity extends BaseActivity {
    private static final String TAG = "MaintenanceRecordActivi";
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mListView)
    XRecyclerView xRecyclerView;
    @BindView(R.id.ll_record_err)
    LinearLayout mErr;
    private String cid;
    private int nowPage = 1;
    private List<BuyCarRecordBean.DataBean.CarBuyList> mList = new ArrayList<>();
    private BuyCarAdapter mAdapter;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_maintenance_record;
    }

    @Override
    protected void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put(C_ID, cid);
        params.put(PAGE, nowPage + "");
        params.put(TYPE, "3");
        MyOkhttp.Okhttp(context, Url.MPBLIST, "加载中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            BuyCarRecordBean buyCarRecordBean = gson.fromJson(response,BuyCarRecordBean.class);
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
                mErr.setVisibility(View.VISIBLE);
                xRecyclerView.setVisibility(View.GONE);
                return;
            }
            List<BuyCarRecordBean.DataBean.CarBuyList> carBuyLists = buyCarRecordBean.getData().getCarBuyList();
            if (carBuyLists != null && !carBuyLists.isEmpty() && carBuyLists.size() > 0){
                mErr.setVisibility(View.GONE);
                xRecyclerView.setVisibility(View.VISIBLE);
                mList.addAll(carBuyLists);
                mAdapter.notifyDataSetChanged();
                if (carBuyLists.size() < 10){
                    xRecyclerView.noMoreLoading();
                }
            }else {
                mErr.setVisibility(View.VISIBLE);
                xRecyclerView.setVisibility(View.GONE);
            }

        });
    }

    @Override
    protected void initView() {
        titleBack.setVisibility(View.VISIBLE);
        title.setText("购车情况");
        cid = getIntent().getStringExtra("cid");
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new BuyCarAdapter(context,mList);
        xRecyclerView.setAdapter(mAdapter);

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

    @OnClick(R.id.title_Back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        nowPage = 1;
        mList.clear();
        mAdapter.notifyDataSetChanged();
        loadData();
    }
}
