package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.uitls.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/4.
 * Description:
 */
public class SeetingsActivity extends BaseActivity {


    private static final String TAG = "SeetingsActivity";

    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.problem_Setting)
    TextView problemSetting;
    @BindView(R.id.language_Setting)
    CheckBox languageSetting;
    @BindView(R.id.standard)
    RadioButton standard;
    @BindView(R.id.english)
    RadioButton english;
    @BindView(R.id.dialect)
    RadioButton dialect;
    @BindView(R.id.seetingsLayout)
    LinearLayout seetingsLayout;
    @BindView(R.id.demo_Setting)
    CheckBox demoSetting;
    @BindView(R.id.personal_Style)
    RadioButton personalStyle;
    @BindView(R.id.standard_Style)
    RadioButton standardStyle;
    @BindView(R.id.seetings_Layouts)
    LinearLayout seetingsLayouts;
    @BindView(R.id.text_Setting)
    CheckBox textSetting;
    @BindView(R.id.englishi)
    RadioButton englishi;
    @BindView(R.id.chinese)
    RadioButton chinese;
    @BindView(R.id.mSeetings_Layouts)
    LinearLayout mSeetingsLayouts;
    @BindView(R.id.mVeesion_Update)
    TextView mVeesionUpdate;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_seetings;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        titleBack.setVisibility(View.VISIBLE);
        title.setText(R.string._settings);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.problem_Setting, R.id.language_Setting, R.id.standard,
            R.id.english, R.id.dialect, R.id.demo_Setting, R.id.personal_Style,
            R.id.standard_Style, R.id.text_Setting, R.id.englishi, R.id.chinese,
            R.id.mVeesion_Update})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.problem_Setting:
                //我的资料库
//                MyApplication.openActivity(context,DataBaseActivity.class);
                break;
            case R.id.language_Setting:
                isSelect(languageSetting.isChecked(), seetingsLayout);
                break;
            case R.id.standard:
                //标准
                ToastUtils.makeText(context, standard.getText().toString());
                break;
            case R.id.english:
                //英语
                ToastUtils.makeText(context, english.getText().toString());
                break;
            case R.id.dialect:
                //方言
                ToastUtils.makeText(context, dialect.getText().toString());
                break;
            case R.id.demo_Setting:
                //演示设置
                isSelect(demoSetting.isChecked(), seetingsLayouts);
                break;
            case R.id.personal_Style:
                //个人风格
                ToastUtils.makeText(context, personalStyle.getText().toString());
                break;
            case R.id.standard_Style:
                //标准风格
                ToastUtils.makeText(context, standardStyle.getText().toString());
                break;
            case R.id.text_Setting:
                //文字设置
                isSelect(textSetting.isChecked(), mSeetingsLayouts);
                break;
            case R.id.englishi:
                //英语
                ToastUtils.makeText(context, englishi.getText().toString());
                break;
            case R.id.chinese:
                //中文
                ToastUtils.makeText(context, chinese.getText().toString());
                break;
            case R.id.mVeesion_Update:
                //版本更新
                ToastUtils.makeText(context, mVeesionUpdate.getText().toString());
                break;
        }
    }


    private void isSelect(boolean ble, LinearLayout rg) {
        if (ble) {
            //选中
            rg.setVisibility(View.VISIBLE);
        } else {
            //未选择中
            rg.setVisibility(View.GONE);
        }
    }

}
