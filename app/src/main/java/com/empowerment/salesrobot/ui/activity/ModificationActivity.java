package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.AGE;
import static com.empowerment.salesrobot.config.BaseUrl.NAME;
import static com.empowerment.salesrobot.config.BaseUrl.NUMBER;
import static com.empowerment.salesrobot.config.BaseUrl.SEX;
import static com.empowerment.salesrobot.config.BaseUrl.WORK;


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
    TextView modifName;
    @BindView(R.id.modif_Sex)
    TextView modifSex;
    @BindView(R.id.modif_Age)
    TextView modifAge;
    @BindView(R.id.modif_Work)
    TextView modifWork;
    @BindView(R.id.modif_Number)
    TextView modifNumber;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_modification;
    }

    @Override
    protected void loadData() {
        modifName.setText(SPUtil.getString(context,NAME));
        if (SPUtil.getString(context,SEX).equals("0"))
        modifSex.setText("男");
        else modifSex.setText("女");
        modifAge.setText(SPUtil.getString(context,AGE));
        modifWork.setText(SPUtil.getString(context,WORK));
        modifNumber.setText(SPUtil.getString(context,NUMBER));

    }

    @Override
    protected void initView() {
        titleBack.setVisibility(View.VISIBLE);
        titleOK.setVisibility(View.GONE);
        title.setText("个人资料");
        titleOK.setText("确定");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//            Map<String,String> modifMap = new HashMap<>();
//            modifMap.put(NAME, name);
//            if (sex.equals("男")) {
//                modifMap.put(SEX, TYPE_VALUE_O);
//            } else {
//                modifMap.put(SEX, TYPE_VALUE_ONE);
//            }
//
//            modifMap.put(AGE, age);
//            modifMap.put(WORK, work);
//            modifMap.put(NUMBER, num);
//            MyOkhttp.Okhttp(context, Url.SALE, "提交中...", modifMap, new MyOkhttp.CallBack() {
//                @Override
//                public void onRequestComplete(String response, String result, String resultNote) {
//                    Gson gson = new Gson();
//                    SalePerfectinBean salePerfectinBean = gson.fromJson(response,SalePerfectinBean.class);
//                    if (result.equals("1")){
//                        ToastUtils.makeText(context,resultNote);
//                        return;
//                    }
//
//                    SalePerfectinBean.DataBean.SaleBean sale = salePerfectinBean.getData().getSale();
//                    SPUtil.putString(context,NAME, sale.getName());
//                    SPUtil.putString(context,SEX, String.valueOf(sale.getSex()));
//                    SPUtil.putString(context,AGE, String.valueOf(sale.getAge()));
//                    SPUtil.putString(context,SALE_ID, String.valueOf(sale.getId()));
//                    SPUtil.putString(context,PHONE_NUMBER, sale.getPhone());
//                    SPUtil.putInt(context,WORK, sale.getWork());
//                    SPUtil.putString(context,NUMBER, String.valueOf(sale.getNumber()));
//                    SPUtil.putString(context,IMAGE, sale.getImage());
//                    SPUtil.putString(context,IS_ORPERFECT, String.valueOf(sale.getIsOrPerfect()));
//                    SPUtil.putString(context,SUCCESSINDEX, String.valueOf(sale.getSuccessIndex()));
//                    SPUtil.putString(context,SALEINDEX, String.valueOf(sale.getSaleIndex()));
//                }
//            });
    }
}
