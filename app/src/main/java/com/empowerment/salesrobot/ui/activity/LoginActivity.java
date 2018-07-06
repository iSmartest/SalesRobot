package com.empowerment.salesrobot.ui.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.listener.Input_monitoring;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.model.UserInfo;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.StringUtils;
import com.empowerment.salesrobot.uitls.TimerUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.app.MyApplication.getWindows;
import static com.empowerment.salesrobot.config.BaseUrl.CODE;
import static com.empowerment.salesrobot.config.BaseUrl.PHONE_NUMBER;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {


    private static final String TAG = "LoginActivity";
    @BindView(R.id.login_Phone)
    EditText loginPhone;
    @BindView(R.id.login_SMS)
    EditText loginSMS;
    @BindView(R.id.but_Login_SMS)
    Button butLoginSMS;
    @BindView(R.id.login_Post)
    Button loginPost;
    private String code;

    @Override
    protected void loadData() {
        getWindows(this);
        loginPhone.addTextChangedListener(new Input_monitoring(loginPhone, 11, butLoginSMS));
        loginSMS.addTextChangedListener(new Input_monitoring(loginPhone, 6, loginPost));

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLauoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.but_Login_SMS, R.id.login_Post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.but_Login_SMS:
                String userphone = loginPhone.getText().toString().trim();
                if (TextUtils.isEmpty(userphone)) {
                    ToastUtils.makeText(context, "电话号码不能为空");
                    return;
                }
                //验证电话号码是否正确
                if (!StringUtils.isMatchesPhone(userphone)) {
                    ToastUtils.makeText(context, "电话号码不正确，请核对后重新输入");
                    return;
                }
                getPhone(userphone);
                break;
            case R.id.login_Post:
//                submit();


                SPUtil.putString(context,"SALE_ID","1");
                MyApplication.openActivity(context,MainActivity.class);

                break;

        }
    }

    private void submit() {
        //验证验证码不能为空
        String passPin = loginSMS.getText().toString().trim();
        if (TextUtils.isEmpty(passPin)) {
            ToastUtils.makeText(context, "验证码不能为空");
            return;
        }
        //验证验证码是否正确
        if (!passPin.equals(code)) {
            ToastUtils.makeText(context, "验证码不正确");
            return;
        }

        //验证电话号码不能为空
        String userphone = loginPhone.getText().toString().trim();
        try {
            findPassword(userphone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findPassword(String userphone) {
        Map<String, String> loginMap = new HashMap<>();
        loginMap.put(PHONE_NUMBER, loginPhone.getText().toString());
        loginMap.put(CODE, code);
        MyOkhttp.Okhttp(context, Url.LOGIN_, dialog, loginMap, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Gson gson = new Gson();
                UserInfo info = gson.fromJson(response,UserInfo.class);
                if (result.equals("1")){
                    ToastUtils.makeText(context,info.getMsg());
                    return;
                }
//                SPUtil.putString(context,"SALE_ID",info.getGroupid());
                SPUtil.putString(context,"SALE_ID","1");
                MyApplication.openActivity(context,MainActivity.class);
            }
        });
    }


    //发送验证码执行
    private void getPhone(String ph) {
        TimerUtil timerUtil = new TimerUtil(butLoginSMS);
        timerUtil.timers();
        Map<String, String> smsMap = new HashMap<>();
        smsMap.put(PHONE_NUMBER, ph);
        MyOkhttp.Okhttp(context, Url.SMS, dialog, smsMap, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Gson gson = new Gson();
                UserInfo info = gson.fromJson(response,UserInfo.class);
                if (result.equals("1")){
                    ToastUtils.makeText(context,info.getMsg());
                    return;
                }
                code = info.getCode();
            }
        });

    }

}
