package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 客户资料
 */
public class CustomerInfoActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.customer_Todays)
    TextView customerTodays;
    @BindView(R.id.customer_Past)
    TextView customerPast;
    @BindView(R.id.customer_VIP)
    TextView customerVIP;
    @BindView(R.id.customer_Per)
    TextView customerPer;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_customer_info;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        title.setText("客户资料");
        titleBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.customer_Todays, R.id.customer_Past, R.id.customer_VIP, R.id.customer_Per})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.customer_Todays:
                //今日客户
                bundle.putString("title", "今日客户");
                MyApplication.openActivity(context,InformationActivity.class,bundle);
                break;
            case R.id.customer_Past:
                //往期客户
                bundle.putString("title", "往期客户");
                MyApplication.openActivity(context,InformationActivity.class,bundle);
                break;
            case R.id.customer_VIP:
                //VIP
                bundle.putString("titles", "VIP客户");
                MyApplication.openActivity(context,VIPActivity.class,bundle);
                break;
            case R.id.customer_Per:
                //预成交
                bundle.putString("titles", "预成交客户");
                MyApplication.openActivity(context,VIPActivity.class,bundle);
                break;
        }
    }
}
