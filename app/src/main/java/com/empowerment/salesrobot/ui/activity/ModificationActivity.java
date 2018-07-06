package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.model.SalePerfectinBean;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.AGE;
import static com.empowerment.salesrobot.config.BaseUrl.IMAGE;
import static com.empowerment.salesrobot.config.BaseUrl.IS_ORPERFECT;
import static com.empowerment.salesrobot.config.BaseUrl.NAME;
import static com.empowerment.salesrobot.config.BaseUrl.NUMBER;
import static com.empowerment.salesrobot.config.BaseUrl.PHONE_NUMBER;
import static com.empowerment.salesrobot.config.BaseUrl.SALEINDEX;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.SEX;
import static com.empowerment.salesrobot.config.BaseUrl.SUCCESSINDEX;
import static com.empowerment.salesrobot.config.BaseUrl.TYPE;
import static com.empowerment.salesrobot.config.BaseUrl.TYPE_VALUE_O;
import static com.empowerment.salesrobot.config.BaseUrl.TYPE_VALUE_ONE;
import static com.empowerment.salesrobot.config.BaseUrl.TYPE_VALUE_TWO;
import static com.empowerment.salesrobot.config.BaseUrl.WORK;
import static com.empowerment.salesrobot.config.Url.SALE;


/**
 * 修改个人资料
 */
public class ModificationActivity extends BaseActivity {


    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_OK)
    TextView titleOK;
    @BindView(R.id.modif_Name)
    EditText modifName;
    @BindView(R.id.modif_Sex)
    EditText modifSex;
    @BindView(R.id.modif_Age)
    EditText modifAge;
    @BindView(R.id.modif_Work)
    EditText modifWork;
    @BindView(R.id.modif_Number)
    EditText modifNumber;
    private Map<String, String> modifMap;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_modification;
    }

    @Override
    protected void loadData() {

        modifMap = new HashMap<>();
        modifMap.put(SALE_ID, (String) SPUtil.getString(context, SALE_ID));
        modifMap.put(TYPE, TYPE_VALUE_TWO);

    }

    @Override
    protected void initView() {
        titleBack.setVisibility(View.VISIBLE);
        titleOK.setVisibility(View.VISIBLE);
        title.setText("修改资料");
        titleOK.setText("确定");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.title_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.title_OK:
                //确定修改
                getEditTextString(modifName.getText().toString()
                        , modifSex.getText().toString()
                        , modifAge.getText().toString()
                        , modifWork.getText().toString()
                        , modifNumber.getText().toString());
                break;
        }
    }

    private void getEditTextString(String name, String sex, String age, String work, String num) {
        if (TextUtils.isEmpty(name)){
            ToastUtils.makeText(context, "请填写姓名");
            return;
        } if (TextUtils.isEmpty(sex)){
            ToastUtils.makeText(context, "请填写性别");
            return;
        } if (TextUtils.isEmpty(age)){
            ToastUtils.makeText(context, "请填写年龄");
            return;
        } if (TextUtils.isEmpty(work)){
            ToastUtils.makeText(context, "请填写职位");
            return;
        } if (TextUtils.isEmpty(num)){
            ToastUtils.makeText(context, "请填写工号");
            return;
        }

            modifMap.put(NAME, name);
            if (sex.equals("男")) {
                modifMap.put(SEX, TYPE_VALUE_O);
            } else {
                modifMap.put(SEX, TYPE_VALUE_ONE);
            }

            modifMap.put(AGE, age);
            modifMap.put(WORK, work);
            modifMap.put(NUMBER, num);
            MyOkhttp.Okhttp(context, Url.SALE, dialog, modifMap, new MyOkhttp.CallBack() {
                @Override
                public void onRequestComplete(String response, String result, String resultNote) {
                    Gson gson = new Gson();
                    SalePerfectinBean salePerfectinBean = gson.fromJson(response,SalePerfectinBean.class);
                    if (result.equals("1")){
                        ToastUtils.makeText(context,resultNote);
                        return;
                    }

                    SalePerfectinBean.DataBean.SaleBean sale = salePerfectinBean.getData().getSale();
                    SPUtil.putString(context,NAME, sale.getName());
                    SPUtil.putString(context,SEX, String.valueOf(sale.getSex()));
                    SPUtil.putString(context,AGE, String.valueOf(sale.getAge()));
                    SPUtil.putString(context,SALE_ID, String.valueOf(sale.getId()));
                    SPUtil.putString(context,PHONE_NUMBER, sale.getPhone());
                    SPUtil.putInt(context,WORK, sale.getWork());
                    SPUtil.putString(context,NUMBER, String.valueOf(sale.getNumber()));
                    SPUtil.putString(context,IMAGE, sale.getImage());
                    SPUtil.putString(context,IS_ORPERFECT, String.valueOf(sale.getIsOrPerfect()));
                    SPUtil.putString(context,SUCCESSINDEX, String.valueOf(sale.getSuccessIndex()));
                    SPUtil.putString(context,SALEINDEX, String.valueOf(sale.getSaleIndex()));
                }
            });
    }
}
