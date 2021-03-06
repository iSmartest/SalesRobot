package com.empowerment.salesrobot.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.dialog.ErrorDialog;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.ui.model.UpdateBean;
import com.empowerment.salesrobot.uitls.GlobalMethod;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.uitls.UpdateManager;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;

/**
 * Created by 小火
 * Create time on  2017/11/9
 * My mailbox is 1403241630@qq.com
 */

public class UpdateActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.a_about_lay_rate)
    TextView mRate;
    @BindView(R.id.a_about_check_version)
    LinearLayout llVersion;
    @BindView(R.id.text_lay_introduce_dec)
    TextView mIntroduce;
    @BindView(R.id.a_about_tv_right)
    TextView tvRight;
    @BindView(R.id.a_about_tv_version)
    TextView tvVersion;
    @BindView(R.id.a_about_version_name)
    TextView tvVersionName;
    private String updataAddress,versionName,descc;
    private int versionCode;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_update;
    }

    @Override
    protected void loadData() {
        Map<String,String> params = new HashMap<>();
        params.put("type","1");
        params.put(STORE_ID,"1");
        MyOkhttp.Okhttp(context, Url.THE_SERVER_URL, "查询中...", params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Log.i("TAG", "onRequestComplete: " + response);
                Gson gson = new Gson();
                UpdateBean mUpdateBean = gson.fromJson(response,UpdateBean.class);
                if (result.equals("1")){
                    ToastUtils.makeText(context,resultNote);
                    return;
                }
                versionCode = Integer.parseInt(mUpdateBean.getData().getNumber());
                updataAddress = mUpdateBean.getData().getAddress();
                versionName = mUpdateBean.getData().getName();
                descc = mUpdateBean.getData().getDesc();
                mIntroduce.setText(descc);
                if (versionCode > GlobalMethod.getVersionCode(context)) {
                    tvVersionName.setText("最新版" + versionName);
                } else {
                    tvVersionName.setText("已是最新版");
                }
            }
        });

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    @Override
    protected void initView() {
        title.setText("版本更新");
        titleBack.setVisibility(View.VISIBLE);


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        tvRight.setText(String.format(getString(R.string.company_copyright), year));
        tvVersion.setText(String.format(getString(R.string.version_format), GlobalMethod.getVersionName(UpdateActivity.this)));
    }

    @OnClick({R.id.title_Back, R.id.a_about_lay_rate, R.id.a_about_check_version})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.a_about_lay_rate:
                rate();
                break;
            case R.id.a_about_check_version:
                if (versionCode > GlobalMethod.getVersionCode(context)) {
                    UpdateManager mUpdateManager = new UpdateManager(context,updataAddress,versionName);
                    mUpdateManager.checkUpdateInfo();
                } else {
                    ToastUtils.makeText(context, "当前已是最新版本");
                }
                break;
        }
    }

    /**
     * 打分
     */
    private void rate() {
        try {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            new ErrorDialog(UpdateActivity.this, null).show();
        }
    }
}
