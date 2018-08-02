package com.empowerment.salesrobot.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.dialog.LogOutDialog;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.uitls.AppManager;
import com.empowerment.salesrobot.uitls.DataCleanManager;
import com.empowerment.salesrobot.uitls.GlideUtils;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.view.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.IMAGE;
import static com.empowerment.salesrobot.config.BaseUrl.NAME;
import static com.empowerment.salesrobot.config.BaseUrl.PHONE_NUMBER;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.Url.HTTP;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/5.
 * Description:
 */
public class PersonalMainActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.my_iconImgview)
    RoundedImageView mUserIcon;
    @BindView(R.id.my_icon_Name)
    TextView mName;
    @BindView(R.id.myPhones)
    TextView mPhone;
    @BindView(R.id.personalText)
    TextView mPersonalText;
    @BindView(R.id.linear_my_setting_clear_cache)
    LinearLayout mClearCache;
    @BindView(R.id.text_my_setting_clear_cache_size)
    TextView mCacheSize;
    @BindView(R.id.tv_common_problem)
    TextView mCommonProblem;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    ToastUtils.makeText(PersonalMainActivity.this, "清理完成");
                    try {
                        mCacheSize.setText(DataCleanManager.getTotalCacheSize(PersonalMainActivity.this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected int getLauoutId() {
        return R.layout.activity_personal_main;
    }

    @Override
    protected void loadData() {
        GlideUtils.imageLoader(context,HTTP+SPUtil.getString(context,IMAGE),mUserIcon);
        mName.setText("账号：" + SPUtil.getString(context,NAME));
        mPhone.setText(SPUtil.getString(context,PHONE_NUMBER));
    }

    @Override
    protected void initView() {
        titleBack.setVisibility(View.VISIBLE);
        try {
            mCacheSize.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back,R.id.personalText, R.id.linear_my_setting_clear_cache, R.id.tv_common_problem, R.id.tv_my_setting_update, R.id.sign_Out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.personalText://个人资料
                MyApplication.openActivity(context, ModificationActivity.class);
                break;
            case R.id.linear_my_setting_clear_cache:
                new Thread(new clearCache()).start();
                break;
            case R.id.tv_common_problem://常见问题
//                MyApplication.openActivity(context, UpdateActivity.class);
                break;
            case R.id.tv_my_setting_update://版本更新
                MyApplication.openActivity(context, UpdateActivity.class);
                break;
            case R.id.sign_Out:
                LogOutDialog dialog = new LogOutDialog(PersonalMainActivity.this, R.string.log_out, () -> {
                    SPUtil.putString(context, SALE_ID, "");//用户ID
                    SPUtil.putString(context, PHONE_NUMBER, "");//手机号码
                    ToastUtils.makeText(context, "已安全退出账号");
                    AppManager.finishAllActivity();
                    MyApplication.openActivity(context, LoginActivity.class);
                });
                dialog.show();
                break;
        }
    }

    class clearCache implements Runnable {
        @Override
        public void run() {
            try {

                DataCleanManager.clearAllCache(PersonalMainActivity.this);
                Thread.sleep(3000);
                if (DataCleanManager.getTotalCacheSize(PersonalMainActivity.this).startsWith("O")) {
                    handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                return;
            }
        }
    }

}
