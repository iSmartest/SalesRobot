package com.empowerment.salesrobot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.Constant;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.uitls.SPUtil;


import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 启动页
 */
public class StartActivity extends BaseActivity {
    @BindView(R.id.mStart_Tx)
    TextView mStartTx;
    private static final int SHOW_TIME_MIN = 1000;// 最小显示时间
    private long mStartTime;// 开始时间
    private boolean IsFirst;//第一次进入应用
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    long loadingTime = System.currentTimeMillis() - mStartTime;// 计算一下总共花费的时间
                    if (loadingTime < SHOW_TIME_MIN) {
                        // 如果比最小显示时间还短，就延时进入MainActivity，否则直接进入
                        mHandler.postDelayed(goToMainActivity, SHOW_TIME_MIN
                                - loadingTime);
                    } else {
                        mHandler.post(goToMainActivity);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    //进入下一个Activity
    Runnable goToMainActivity = new Runnable() {
        @Override
        public void run() {
            IsFirst = SPUtil.getBoolean(context, Constant.FIRST_COME, true);
            if (IsFirst) {
                startActivity(new Intent(StartActivity.this,
                        LoginActivity.class));
                SPUtil.putBoolean(context, Constant.FIRST_COME, false);
                finish();
            } else {
                if (!TextUtils.isEmpty(SPUtil.getString(context,"SALE_ID"))){
                    MyApplication.openActivity(context,MainActivity.class);
                    finish();
                }else {
                    startActivity(new Intent(StartActivity.this,
                            LoginActivity.class));
                    finish();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLauoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView() {
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        mStartTx.setAnimation(animation);
        animation.setDuration(3000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mStartTime = System.currentTimeMillis();//记录开始时间1
                mHandler.sendEmptyMessage(1);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void loadData() {

    }

    private void saveTag() {
        SPUtil.putBoolean(context, Constant.FIRST_COME, false);
    }

    @Override
    public void finish() {
        super.finish();
        saveTag();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mHandler.removeCallbacks(goToMainActivity);//移除回调
    }
}
