package com.empowerment.salesrobot.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.DataCenter;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.dialog.ProgressDialog;
import com.empowerment.salesrobot.dialog.SelectedDialog;
import com.empowerment.salesrobot.dialog.SelectedTimeDialog;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.uitls.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/19.
 * Description:
 */
public class AddCustomerActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_OK)
    TextView titleOk;
    @BindView(R.id.ll_add_customer_style)
    LinearLayout mCustomerStyle;
    @BindView(R.id.ll_add_customer_sex)
    LinearLayout mCustomerSex;
    @BindView(R.id.ll_add_customer_industry)
    LinearLayout mCustomerIndustry;
    @BindView(R.id.ll_add_customer_intention)
    LinearLayout mCustomerIntention;
    @BindView(R.id.tv_add_sex)
    TextView tvSex;
    @BindView(R.id.tv_add_customer_style)
    TextView tvCustomerStyle;
    @BindView(R.id.tv_add_industry)
    TextView tvIndustry;
    @BindView(R.id.tv_add_intention)
    TextView tvIntention;
    @BindView(R.id.edit_add_names)
    EditText ediName;
    @BindView(R.id.edit_add_age)
    EditText ediAge;
    @BindView(R.id.edit_add_phone)
    EditText ediPhone;
    @BindView(R.id.edit_add_id_card)
    EditText ediIdCard;
    @BindView(R.id.edit_add_work)
    EditText ediWork;
    @BindView(R.id.edit_add_income)
    EditText ediIncome;
    @BindView(R.id.edit_add_address)
    EditText ediAddress;
    private SelectedDialog selectedDialog;
    private String mStyle,mName,mAge,mSex,mPhone,mIdCard,mWork,mIndustry,mIncome,mAddress,mIntention;
    @Override
    protected int getLauoutId() {
        return R.layout.activity_add_costomer;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        title.setText("添加客户");
        titleBack.setVisibility(View.VISIBLE);
        titleOk.setVisibility(View.VISIBLE);
        titleOk.setText("确定");
    }

    @OnClick({R.id.title_Back, R.id.title_OK, R.id.ll_add_customer_style, R.id.ll_add_customer_sex, R.id.ll_add_customer_industry,R.id.ll_add_customer_intention})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.title_OK:
                mStyle = tvCustomerStyle.getText().toString().trim();
                mName = ediName.getText().toString().trim();
                mAge = ediAge.getText().toString().trim();
                mSex = tvSex.getText().toString().trim();
                mPhone = ediPhone.getText().toString().trim();
                mIdCard = ediIdCard.getText().toString().trim();
                mWork = ediWork.getText().toString().trim();
                mIndustry = tvIndustry.getText().toString().trim();
                mIncome = ediIncome.getText().toString().trim();
                mAddress = ediAddress.getText().toString().trim();
                mIntention = tvIntention.getText().toString().trim();
                if (mStyle.isEmpty()){
                    ToastUtils.makeText(context,"请选择要添加的客户类型");
                    return;
                }
                if (mName.isEmpty()){
                    ToastUtils.makeText(context,"请输入要添加的客户姓名");
                    return;
                }
                if (mAge.isEmpty()){
                    ToastUtils.makeText(context,"请输入要添加的客户年龄");
                    return;
                }
                if (mSex.isEmpty()){
                    ToastUtils.makeText(context,"请选择要添加的客户性别");
                    return;
                }
                if (mPhone.isEmpty()){
                    ToastUtils.makeText(context,"请输入要添加的客户电话");
                    return;
                }
                submitCustomer();
                break;
            case R.id.ll_add_customer_style://客户类型
                selectedDialog = new SelectedDialog(context,R.string.customer_style, "VIP客户", DataCenter.getCustomerStyleList(), new SelectedDialog.OnSureBtnClickListener() {
                    @Override
                    public void sure(String style) {
                        tvCustomerStyle.setText(style);
                        mStyle = style;
                        selectedDialog.dismiss();
                    }
                });
                selectedDialog.show();

                break;
            case R.id.ll_add_customer_sex://性别
                selectedDialog = new SelectedDialog(context, R.string.sex, "男", DataCenter.getSexList(), new SelectedDialog.OnSureBtnClickListener() {
                    @Override
                    public void sure(String sex) {
                        tvSex.setText(sex);
                        if (sex.equals("男")){
                            mSex = "0";
                        }else {
                            mSex = "1";
                        }
                        selectedDialog.dismiss();
                    }
                });
                selectedDialog.show();
                break;
            case R.id.ll_add_customer_industry://行业
                selectedDialog = new SelectedDialog(context,R.string.industry, "党政机关人员", DataCenter.getIndustryList(), new SelectedDialog.OnSureBtnClickListener() {
                    @Override
                    public void sure(String industry) {
                        tvIndustry.setText(industry);
                        mIndustry = industry;
                        selectedDialog.dismiss();
                    }
                });
                selectedDialog.show();
                break;
            case R.id.ll_add_customer_intention://购车意向
                selectedDialog = new SelectedDialog(context,R.string.intention, "一个月以内购车", DataCenter.getIntentionList(), new SelectedDialog.OnSureBtnClickListener() {
                    @Override
                    public void sure(String intention) {
                        tvIntention.setText(intention);
                        mIntention = intention;
                        selectedDialog.dismiss();
                    }
                });
                selectedDialog.show();
                break;
        }
    }

    private void submitCustomer() {
        Map<String,String> params = new HashMap<>();
        params.put("","");
        params.put("","");
        params.put("","");
        params.put("","");
        params.put("","");
        params.put("","");
        params.put("","");
        params.put("","");
        params.put("","");
        params.put("","");
        params.put("","");
        params.put("","");
        MyOkhttp.Okhttp(context, Url.SUBMIT_CUSTOMER,"提交中...", params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {

            }
        });
    }
}
