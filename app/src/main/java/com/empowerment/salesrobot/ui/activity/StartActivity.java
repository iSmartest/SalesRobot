package com.empowerment.salesrobot.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.Constant;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;


/**
 * 启动页
 */
public class StartActivity extends AppCompatActivity {
    private static final int SHOW_TIME_MIN = 1000;// 最小显示时间
    private long mStartTime;// 开始时间
    private boolean IsFirst;//第一次进入应用
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    long loadingTime = System.currentTimeMillis() - mStartTime;// 计算一下总共花费的时间
                    if (loadingTime < SHOW_TIME_MIN) {
                        // 如果比最小显示时间还短，就延时进入MainActivity，否则直接进入
                        mHandler.postDelayed(goToMainActivity, SHOW_TIME_MIN - loadingTime);
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
            IsFirst = SPUtil.getBoolean(StartActivity.this, Constant.FIRST_COME, true);
            if (IsFirst) {
                startActivity(new Intent(StartActivity.this,
                        LoginActivity.class));
                SPUtil.putBoolean(StartActivity.this, Constant.FIRST_COME, false);
                finish();
            } else {
                if (!TextUtils.isEmpty(SPUtil.getString(StartActivity.this, SALE_ID))) {
                    MyApplication.openActivity(StartActivity.this, MainActivity.class);
                    finish();
                } else {
                    startActivity(new Intent(StartActivity.this,
                            LoginActivity.class));
                    finish();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setBackgroundResource(R.mipmap.splash);
        super.onCreate(savedInstanceState);
        StatusBarUtil.fullScreen(StartActivity.this);
        mStartTime = System.currentTimeMillis();//记录开始时间1
        mHandler.sendEmptyMessage(1);
        checkPermission();
    }

    private void saveTag() {
        SPUtil.putBoolean(StartActivity.this, Constant.FIRST_COME, false);
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


    private void checkPermission() {
        //判断Android版本   获取需要的权限
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionStrs = new ArrayList<>();
            int hasWriteSdcardPermission =
                    ContextCompat.checkSelfPermission(
                            StartActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteSdcardPermission != PackageManager.PERMISSION_GRANTED) {
                permissionStrs.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                permissionStrs.add(Manifest.permission.CALL_PRIVILEGED);
                permissionStrs.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
                permissionStrs.add(Manifest.permission.READ_CONTACTS);
                permissionStrs.add(Manifest.permission.RECORD_AUDIO);
                permissionStrs.add(Manifest.permission.VIBRATE);
                permissionStrs.add(Manifest.permission.CAMERA);

            }

            String[] stringArray = permissionStrs.toArray(new String[0]);
            if (permissionStrs.size() > 0) {
                requestPermissions(stringArray,
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }
    }
}
