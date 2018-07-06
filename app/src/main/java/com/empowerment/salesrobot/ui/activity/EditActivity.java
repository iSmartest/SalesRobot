package com.empowerment.salesrobot.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.dialog.StopTipsDialog;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.model.InfromationEntity;
import com.empowerment.salesrobot.ui.model.VipOrYxEntity;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.M_ADDRESS;
import static com.empowerment.salesrobot.config.BaseUrl.M_AGE;
import static com.empowerment.salesrobot.config.BaseUrl.M_CONTENT;
import static com.empowerment.salesrobot.config.BaseUrl.M_ID;
import static com.empowerment.salesrobot.config.BaseUrl.M_IDCARD;
import static com.empowerment.salesrobot.config.BaseUrl.M_PHONE;
import static com.empowerment.salesrobot.config.BaseUrl.M_SEX;
import static com.empowerment.salesrobot.config.BaseUrl.M_WORK;


/**
 * 客户资料详情----编辑
 */
public class EditActivity extends BaseActivity {


    private static final String TAG = "EditActivity";
    public Map<String, String> map = new HashMap<>();
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_OK)
    TextView titleOK;
    @BindView(R.id.title_Layout)
    RelativeLayout titleLayout;
    @BindView(R.id.editPic)
    ImageView editPic;
    @BindView(R.id.edit_Name)
    TextView editName;
    @BindView(R.id.edit_Time)
    TextView editTime;
    @BindView(R.id.edit_Perfac)
    TextView editPerfac;
    @BindView(R.id.edit_Get)
    TextView editGet;
    @BindView(R.id.editNames)
    EditText editNames;
    @BindView(R.id.editAge)
    EditText editAge;
    @BindView(R.id.editSex)
    EditText editSex;
    @BindView(R.id.editWork)
    EditText editWork;
    @BindView(R.id.editAddses)
    EditText editAddses;
    @BindView(R.id.edit_IdCred)
    EditText editIdCred;
    @BindView(R.id.editPhone)
    EditText editPhone;
    @BindView(R.id.edit_Content)
    EditText editContent;

    private InfromationEntity.DataBean.CustomerListBean listBean;
    VipOrYxEntity.DataBean.CustListBean custListBean;
    int MAX = 100;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void loadData() {

        if (!TextUtils.isEmpty(editName.getText().toString())
                && !TextUtils.isEmpty(editSex.getText().toString())
                && !TextUtils.isEmpty(editAge.getText().toString())
                && !TextUtils.isEmpty(editPhone.getText().toString())
                && !TextUtils.isEmpty(editIdCred.getText().toString())
//                &&!TextUtils.isEmpty(editHobby.getText().toString())
                && !TextUtils.isEmpty(editWork.getText().toString())
                && !TextUtils.isEmpty(editAddses.getText().toString())
                && !TextUtils.isEmpty(editContent.getText().toString())
                ) {
            editPerfac.setText("资料完整度" + MAX + "%");
        }

        switch (getIntent().getStringExtra("title")) {
            case "今日客户":
                String info = getIntent().getExtras().getString("info").toString();
                listBean = new Gson().fromJson(info, InfromationEntity.DataBean.CustomerListBean.class);
                editName.setText(listBean.getName());
                editNames.setText(listBean.getName()+"");
                editAge.setText(listBean.getAge()+"");
                editWork.setText(listBean.getWork()+"");
                editAddses.setText(listBean.getAddress()+"");
                editIdCred.setText(listBean.getIdCard()+"");
                editPhone.setText(listBean.getPhone()+"");
                editContent.setText(listBean.getContent()+"");
                if (listBean.getSex()==1){
                    editSex.setText("男");

                }else {
                    editSex.setText("女");
                }
                break;
            case "往期客户":
                String info1 = getIntent().getExtras().getString("info").toString();
                listBean = new Gson().fromJson(info1, InfromationEntity.DataBean.CustomerListBean.class);
                editName.setText(listBean.getName());
                editNames.setText(listBean.getName()+"");
                editAge.setText(listBean.getAge()+"");
                editWork.setText(listBean.getWork()+"");
                editAddses.setText(listBean.getAddress()+"");
                editIdCred.setText(listBean.getIdCard()+"");
                editPhone.setText(listBean.getPhone()+"");
                editContent.setText(listBean.getContent()+"");
                if (listBean.getSex()==1){
                    editSex.setText("男");

                }else {
                    editSex.setText("女");
                }
                break;
            case "VIP客户":
                String info2 = getIntent().getExtras().getString("info").toString();
                custListBean = new Gson().fromJson(info2, VipOrYxEntity.DataBean.CustListBean.class);
                editName.setText(custListBean.getName());
                editNames.setText(custListBean.getName()+"");
                editAge.setText(custListBean.getAge()+"");
                editWork.setText(custListBean.getWork()+"");
                editAddses.setText(custListBean.getAddress()+"");
                editIdCred.setText(custListBean.getIdCard()+"");
                editPhone.setText(custListBean.getPhone()+"");
                if (listBean.getSex()==1){
                    editSex.setText("男");

                }else {
                    editSex.setText("女");
                }

                break;
            case "预成交客户":
                String info3 = getIntent().getExtras().getString("info").toString();
                custListBean = new Gson().fromJson(info3, VipOrYxEntity.DataBean.CustListBean.class);
                editName.setText(custListBean.getName());
                editNames.setText(custListBean.getName()+"");
                editAge.setText(custListBean.getAge()+"");
                editWork.setText(custListBean.getWork()+"");
                editAddses.setText(custListBean.getAddress()+"");
                editIdCred.setText(custListBean.getIdCard()+"");
                editPhone.setText(custListBean.getPhone()+"");
                if (custListBean.getSex()==1){
                    editSex.setText("男");

                }else {
                    editSex.setText("女");
                }
                break;
        }
    }

    @Override
    protected void initView() {
        titleBack.setVisibility(View.VISIBLE);
        titleOK.setVisibility(View.VISIBLE);
        titleOK.setText("编辑");
        titleLayout.setBackgroundColor(getResources().getColor(R.color.colorTransParent));
    }


    //是否可编辑
    private void setEditText(EditText editText, boolean b) {
        editText.setFocusableInTouchMode(b);
        editText.setFocusable(b);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.title_OK, R.id.edit_Get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.title_OK:
                //编辑 保存
                switch (titleOK.getText().toString()) {

                    case "编辑":
                        titleOK.setText("保存");
                        //设置可编辑
                        setEditText(editAge, true);
                        setEditText(editSex, true);
                        setEditText(editAddses,true);
                        setEditText(editContent, true);
                        setEditText(editIdCred, true);
                        setEditText(editPhone, true);
                        setEditText(editWork, true);
                        ToastUtils.makeText(context,"现在可以修改资料啦");
                        break;
                    case "保存":
                        if (editSex.getText().toString().equals("男")) {
                            map.put(M_SEX, "1");
                        } else {
                            map.put(M_SEX, "2");
                        }
                        switch (title.getText().toString()){
                            case "今日客户":
                            case "往期客户":
                                map.put(M_ID, listBean.getId() + "");
                                break;
                            case "VIP客户":
                            case "预成交客户":
                                map.put(M_ID, custListBean.getId() + "");
                                break;
                        }

                        map.put(M_AGE, editAge.getText().toString());
                        map.put(M_PHONE, editPhone.getText().toString());
                        map.put(M_IDCARD, editIdCred.getText().toString());
                        map.put(M_WORK, editWork.getText().toString());
                        map.put(M_ADDRESS,editAddses.getText().toString());
                        map.put(M_CONTENT, editContent.getText().toString());
                        MyOkhttp.Okhttp(context, Url.EDIT_CUSTOMER, dialog, map, new MyOkhttp.CallBack() {
                            @Override
                            public void onRequestComplete(String response, String result, String resultNote) {
                                switch (result){
                                    case "0":
                                        ToastUtils.makeText(context,resultNote);
                                        break;
                                    case "1":
                                        ToastUtils.makeText(context,resultNote);
                                        break;
                                }
                            }
                        });
                        titleOK.setText("编辑");
                        setEditText(editAge, false);
                        setEditText(editSex, false);

                        setEditText(editAddses,false);
                        setEditText(editContent, false);
                        setEditText(editIdCred, false);
                        setEditText(editPhone, false);
                        setEditText(editWork, false);

                        //资料已保存
                        break;
                }
                break;
            case R.id.edit_Get:
                StopTipsDialog dialog = new StopTipsDialog(context, R.string.log_out, new StopTipsDialog.OnSureBtnClickListener() {
                    @Override
                    public void sure() {
                        ToastUtils.makeText(context,"是");
                    }
                    @Override
                    public void cancle() {
                        ToastUtils.makeText(context,"否");
                    }
                });
                dialog.show();

                break;
        }
    }


    class  EdidBean{

        /**
         * data : {}
         * resultCode : 1
         * msg : 身份证号格式错误
         */

        private DataBean data;
        private int resultCode;
        private String msg;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public  class DataBean {
        }
    }
}
