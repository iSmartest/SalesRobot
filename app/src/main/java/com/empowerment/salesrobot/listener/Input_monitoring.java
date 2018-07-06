package com.empowerment.salesrobot.listener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.empowerment.salesrobot.R;


public class Input_monitoring implements TextWatcher {


    private static final String TAG = "Input_monitoring";
    private EditText editText;
    private int MAX;
    private Button but;

    public Input_monitoring(EditText editText, int MAX, Button but) {
        this.editText = editText;
        this.MAX = MAX;
        this.but = but;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String trim = s.toString().trim();
        if (!TextUtils.isEmpty(trim)) {
            int length = trim.length();
            String butName = but.getText().toString().trim();
            if (butName.equals("发送验证码") && length <= MAX) {
                but.setClickable(true);
                but.setBackgroundResource(R.drawable.bg_login_sms_1);
                Log.e(TAG, "afterTextChanged: 73");
            } else {
                but.setClickable(false);
                but.setBackgroundResource(R.drawable.bg_login_sms);
                if (butName.equals("登录") && length <= MAX) {
                    but.setClickable(true);
                    but.setBackgroundResource(R.drawable.bg_login_menu_1);
                } else {
                    but.setClickable(false);
                    but.setBackgroundResource(R.drawable.bg_login_menu);
                }
            }

        }

    }
}
