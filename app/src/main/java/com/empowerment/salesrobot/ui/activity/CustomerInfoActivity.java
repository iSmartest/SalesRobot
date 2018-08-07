package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.ui.model.CustomerCountBean;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;

/**
 * 客户资料
 */
public class CustomerInfoActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.customer_Todays)
    LinearLayout customerTodays;
    @BindView(R.id.customer_Past)
    LinearLayout customerPast;
    @BindView(R.id.customer_VIP)
    LinearLayout customerVIP;
    @BindView(R.id.customer_Per)
    LinearLayout customerPer;
    @BindView(R.id.tv_today_customer)
    TextView tvTodayCustomer;
    @BindView(R.id.tv_per_customer)
    TextView tvPerCustomer;
    @BindView(R.id.tv_past_customer)
    TextView tvPastCustomer;
    @BindView(R.id.tv_vip_customer)
    TextView tvVIPCustomer;
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
                bundle.putString("mStyle", "1");
                MyApplication.openActivity(context,VIPActivity.class,bundle);
                break;
            case R.id.customer_Per:
                //预成交
                bundle.putString("mStyle", "2");
                MyApplication.openActivity(context,VIPActivity.class,bundle);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String,String> params = new HashMap<>();
        params.put(SALE_ID, SPUtil.getString(context,SALE_ID));
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        MyOkhttp.Okhttp(context, Url.CUSTOMER_COUNT, "加载中...", params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Gson gson = new Gson();
                CustomerCountBean customerCountBean = gson.fromJson(response,CustomerCountBean.class);
                if (result.equals("1")){
                    ToastUtils.makeText(context,resultNote);
                    return;
                }
                tvTodayCustomer.setText(customerCountBean.getData().getNowCustomer()+"");
                tvPerCustomer.setText(customerCountBean.getData().getYiXiangCustomer()+"");
                tvPastCustomer.setText(customerCountBean.getData().getPastCustomer()+"");
                tvVIPCustomer.setText(customerCountBean.getData().getVipCustomer()+"");

            }
        });
    }
}
