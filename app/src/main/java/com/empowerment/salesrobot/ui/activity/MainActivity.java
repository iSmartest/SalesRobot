package com.empowerment.salesrobot.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.dialog.StopTipsDialog;
import com.empowerment.salesrobot.ui.fragment.HomeFragment;
import com.empowerment.salesrobot.ui.fragment.MineFragment;
import com.empowerment.salesrobot.ui.fragment.TrainFragment;
import com.empowerment.salesrobot.uitls.AppManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_main_home)
    RadioButton mHome;
    @BindView(R.id.iv_main_train)
    RadioButton mTrain;
    @BindView(R.id.iv_main_stop)
    RadioButton mStop;
    @BindView(R.id.iv_main_mine)
    RadioButton mMy;
    private HomeFragment homeFragmen;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected int getLauoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

        checkPermission();
        homeFragmen = (HomeFragment) changeFragment(HomeFragment.class,R.id.linear_main_layout_content,true,null,true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_main_home, R.id.iv_main_train, R.id.iv_main_stop, R.id.iv_main_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_main_home:
                changeFragment(HomeFragment.class,R.id.linear_main_layout_content,true,null,false);
                break;
            case R.id.iv_main_train:
                changeFragment(TrainFragment.class,R.id.linear_main_layout_content,true,null,false);
                break;
            case R.id.iv_main_stop:
                StopTipsDialog dialog = new StopTipsDialog(context, R.string.stop_tips, new StopTipsDialog.OnSureBtnClickListener() {
                    @Override
                    public void sure() {
                        mStop.setChecked(true);
                    }
                    @Override
                    public void cancle() {
                        mStop.setChecked(false);
                    }
                });
                dialog.show();
                break;
            case R.id.iv_main_mine:
                changeFragment(MineFragment.class,R.id.linear_main_layout_content,true,null,false);
                break;
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 3000);
        } else {
            AppManager.finishAllActivity();
            System.exit(0);
        }
    }


    private void checkPermission() {
        //判断Android版本   获取需要的权限
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionStrs = new ArrayList<>();
            int hasWriteSdcardPermission =
                    ContextCompat.checkSelfPermission(
                            MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
