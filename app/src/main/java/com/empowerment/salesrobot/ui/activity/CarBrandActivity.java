package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.CarBrandAdapter;
import com.empowerment.salesrobot.ui.model.CarBrandBean;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class CarBrandActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycler_car_brand)
    RecyclerView mRecyclerView;
    private String brandId;
    private CarBrandAdapter mAdapter;
    private List<CarBrandBean.DataBean.CarList> mList = new ArrayList<>();
    @Override
    protected int getLauoutId() {
        return R.layout.activity_car_brand;
    }

    @Override
    protected void loadData() {
        Map<String,String> params = new HashMap<>();
        params.put("brandId",brandId);
        MyOkhttp.Okhttp(context, Url.CARLIST, "加载中...", params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Gson gson = new Gson();
                CarBrandBean carBrandBean = gson.fromJson(response,CarBrandBean.class);
                if (result.equals("1")){
                    ToastUtils.makeText(context,resultNote);
                    return;
                }

                List<CarBrandBean.DataBean.CarList> carLists = carBrandBean.getData().getCarList();
                if (carLists != null && !carLists.isEmpty() && carLists.size() > 0){
                    mList.addAll(carLists);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void initView() {
        String mTitle = getIntent().getStringExtra("mTitle");
        brandId = getIntent().getStringExtra("brandId");
        title.setText(mTitle);
        titleBack.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        mAdapter = new CarBrandAdapter(context, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.title_Back)
    public void onViewClicked() {
        finish();
    }
}
