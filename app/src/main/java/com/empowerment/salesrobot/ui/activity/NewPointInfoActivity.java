package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.model.NewPointInfoBean;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;
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
public class NewPointInfoActivity extends BaseActivity {

    private String id;
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_OK)
    TextView title_OK;
    @BindView(R.id.new_point_list)
    RecyclerView recyclerView;

    private String policy;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_new_point_info;
    }

    @Override
    protected void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put("carId", id);
        MyOkhttp.Okhttp(context, Url.BUYPOINTDETAIL, "加载中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            NewPointInfoBean newPointInfoBean = gson.fromJson(response, NewPointInfoBean.class);
            if (result.equals("1")) {
                ToastUtils.makeText(context, resultNote);
                return;
            }

            policy = newPointInfoBean.getData().getPolicy();
        });
    }

    @Override
    protected void initView() {
        title.setText("新品买点");
        titleBack.setVisibility(View.VISIBLE);
        title_OK.setVisibility(View.VISIBLE);
        title_OK.setText("政策");
        id = getIntent().getStringExtra("carId");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.title_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.title_OK:
                ToastUtils.makeText(context, policy);
                break;
        }

    }
}
