package com.empowerment.salesrobot.ui.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import cn.jpush.android.api.JPushInterface;

import static com.empowerment.salesrobot.app.MyApplication.getWindows;
import static com.empowerment.salesrobot.config.BaseUrl.AGE;
import static com.empowerment.salesrobot.config.BaseUrl.CODE;
import static com.empowerment.salesrobot.config.BaseUrl.IMAGE;
import static com.empowerment.salesrobot.config.BaseUrl.NAME;
import static com.empowerment.salesrobot.config.BaseUrl.NUMBER;
import static com.empowerment.salesrobot.config.BaseUrl.PHONE_NUMBER;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.SEX;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.WORK;

/**
 * 登录页面
 */
@SuppressWarnings("JavaDoc")
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
    private String sessionId = "";
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
                submit();
                break;

        }
    }

    private void submit() {
        //验证验证码不能为空
        String passPin = loginSMS.getText().toString().trim();
        String userphone = loginPhone.getText().toString().trim();
        if (TextUtils.isEmpty(passPin)) {
            ToastUtils.makeText(context, "验证码不能为空");
            return;
        }
        login(passPin,userphone) ;
    }

    private void login(String passPin,String userphone) {
        Map<String, String> params = new HashMap<>();
        params.put(PHONE_NUMBER, userphone);
        params.put(CODE, passPin);
        params.put("sessionId", sessionId);
        MyOkhttp.Okhttp(context, Url.LOGIN, "登录中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            UserInfo info = gson.fromJson(response, UserInfo.class);
            Log.i("TAG", "onRequestComplete: " + response);
            if (result.equals("1")) {
                ToastUtils.makeText(context, resultNote);
                return;
            }
            SPUtil.putString(context,SALE_ID,info.getData().getUserid());
            SPUtil.putString(context,STORE_ID,info.getData().getStoreId());
            SPUtil.putString(context,AGE,info.getData().getAge());
            SPUtil.putString(context,SEX,info.getData().getSex()+"");
            SPUtil.putString(context,NAME,info.getData().getName());
            SPUtil.putString(context,WORK,info.getData().getWork()+"");
            SPUtil.putString(context,NUMBER,info.getData().getNumber());
            SPUtil.putString(context,IMAGE,info.getData().getImage());
            SPUtil.putString(context,PHONE_NUMBER,info.getData().getPhone());
            JPushInterface.setAlias(LoginActivity.this,1,info.getData().getUserid());
            MyApplication.openActivity(context, MainActivity.class);
        });
    }
    /**
     * 获取短信验证码
     * @param
     */
    public void getPhone(String ph) {
        TimerUtil timerUtil = new TimerUtil(butLoginSMS);
        timerUtil.timers();
        Map<String,String> params = new HashMap<>();
        params.put("mobile",ph);
        MyOkhttp.Okhttp(context, Url.SMS, "获取中...", params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                if (result.equals("0")){
                    Gson gson = new Gson();
                    UserInfo info = gson.fromJson(response, UserInfo.class);
                    sessionId = info.getData().getSessionId();
                    ToastUtils.makeText(context,"短信已发送，请注意查收");
                }else {
                    ToastUtils.makeText(context,resultNote);
                }
            }
        });


    }
}
