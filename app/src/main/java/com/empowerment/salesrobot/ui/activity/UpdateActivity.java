package com.empowerment.salesrobot.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.dialog.ErrorDialog;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.uitls.GlobalMethod;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.uitls.UpdateManager;
import com.empowerment.salesrobot.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    protected int getLauoutId() {
        return R.layout.activity_update;
    }

    @Override
    protected void loadData() {
        Map<String,String> params = new HashMap<>();
//        params.put("json",json);
//        dialog1.show();
//        OkHttpUtils.post().url(Constant.THE_SERVER_URL).params(params).build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                dialog1.dismiss();
//                ToastUtils.makeText(context,e.getMessage());
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Log.i("response", "onResponse: " + response);
//                Gson gson = new Gson();
//                dialog1.dismiss();
//                UpdateBean mUpdateBean = gson.fromJson(response,UpdateBean.class);
//                if (mUpdateBean.getResult().equals("1")){
//                    ToastUtils.makeText(context,mUpdateBean.getResultNote());
//                    return;
//                }
//                versionCode = mUpdateBean.getVersionNumber();
//                updataAddress = mUpdateBean.getUpdataAddress();
//                versionName = mUpdateBean.getVersionName();
//                descc = mUpdateBean.getDescc();
//                mIntroduce.setText(descc);
//                if (versionCode > GlobalMethod.getVersionCode(context)) {
//                    tvVersionName.setText("最新版" + versionName);
//                } else {
//                    tvVersionName.setText("已是最新版");
//                }
//            }
//        });
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
