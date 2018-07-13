package com.empowerment.salesrobot.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.fragment.HomeFragment;
import com.empowerment.salesrobot.ui.fragment.MineFragment;
import com.empowerment.salesrobot.ui.fragment.ReceptionFragment;
import com.empowerment.salesrobot.ui.fragment.TrainFragment;
import com.empowerment.salesrobot.uitls.AppManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements HomeFragment.CallBackListener{

    @BindView(R.id.iv_main_home)
    RadioButton mHome;
    @BindView(R.id.iv_main_train)
    RadioButton mTrain;
    @BindView(R.id.iv_main_reception)
    RadioButton mReception;
    @BindView(R.id.iv_main_mine)
    RadioButton mMy;
    private HomeFragment homeFragmen;

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
        //定义底部标签图片大小和位置
        Drawable drawable_home = getResources().getDrawable(R.drawable.app_main_home_selector);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_home.setBounds(0, 0, 90, 90);
        //设置图片在文字的哪个方向
        mHome.setCompoundDrawables(null, drawable_home, null, null);

        Drawable drawable_train = getResources().getDrawable(R.drawable.app_main_train_selector);
        drawable_train.setBounds(0, 0, 90, 90);
        mTrain.setCompoundDrawables(null, drawable_train, null, null);

        Drawable drawable_reception = getResources().getDrawable(R.drawable.app_main_stop_selector);
        drawable_reception.setBounds(0, 0, 90, 90);
        mReception.setCompoundDrawables(null, drawable_reception, null, null);

        Drawable drawable_mine = getResources().getDrawable(R.drawable.app_main_mine_selector);
        drawable_mine.setBounds(0, 0, 90, 90);
        mMy.setCompoundDrawables(null, drawable_mine, null, null);
        homeFragmen = (HomeFragment) changeFragment(HomeFragment.class,R.id.linear_main_layout_content,true,null,true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_main_home, R.id.iv_main_train, R.id.iv_main_reception, R.id.iv_main_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_main_home:
                changeFragment(HomeFragment.class,R.id.linear_main_layout_content,true,null,false);
                break;
            case R.id.iv_main_train:
                changeFragment(TrainFragment.class,R.id.linear_main_layout_content,true,null,false);
                break;
            case R.id.iv_main_reception:
                changeFragment(ReceptionFragment.class,R.id.linear_main_layout_content,true,null,false);
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

    @Override
    public void onClickListener(int falg) {
        switch (falg){
            case 1:
                changeFragment(TrainFragment.class,R.id.linear_main_layout_content,true,null,false);
                mTrain.setChecked(true);
                break;
            case 2:
                changeFragment(ReceptionFragment.class,R.id.linear_main_layout_content,true,null,false);
                mReception.setChecked(true);
                break;
        }
    }
}
