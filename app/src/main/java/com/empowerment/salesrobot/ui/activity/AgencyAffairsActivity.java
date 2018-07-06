package com.empowerment.salesrobot.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待办事宜
 */
public class AgencyAffairsActivity extends BaseActivity {
    private static final String TAG = "TodoActivity";
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.company)
    CheckBox company;
    @BindView(R.id.company_TV)
    TextView companyTV;
    @BindView(R.id.Customer)
    CheckBox Customer;
    @BindView(R.id.CustomerTV)
    TextView CustomerTV;
    @BindView(R.id.personal)
    CheckBox personal;
    @BindView(R.id.personalTV)
    TextView personalTV;

    //Model：定义的数据

    @Override
    protected int getLauoutId() {
        return R.layout.activity_agency_affairs ;
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void initView() {
        title.setText("待办事宜");
        titleBack.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.company, R.id.Customer, R.id.personal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.company:
                isCheckBox(company.isChecked(), companyTV);
                break;
            case R.id.Customer:
                isCheckBox(Customer.isChecked(), CustomerTV);
                break;
            case R.id.personal:
                isCheckBox(personal.isChecked(), personalTV);
                break;
        }
    }

    //判断当前状态 显示隐藏控件
    private void isCheckBox(boolean b, TextView tv) {
        if (b) {
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
    }
}
