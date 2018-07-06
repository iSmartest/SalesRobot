package com.empowerment.salesrobot.uitls;

import android.content.Context;
import android.text.TextUtils;


import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.BaseConfig;
import com.empowerment.salesrobot.app.CommonLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 小火
 * Create time on  2017/3/22
 * My mailbox is 1403241630@qq.com
 */

public class StringUtils {
    /**
     * 判断字符串是否有内容
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !TextUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否符合手机号码（区段由服务器返回，匹配前三位是否符合并且满足为11位）
     */
    public static boolean isMatchesPhone(String telPhone) {
        if (TextUtils.isEmpty(telPhone) || telPhone.length() != 11)
            return false;
        BaseConfig config = null;
        if (config == null || TextUtils.isEmpty(config.getContent()))
            return telPhone.matches("^[1][3-8]\\d{9}");
        else {
            String[] telPrefix = config.getContent().split(",");
            int index = -1;
            if (telPrefix != null) {
                for (int i = 0; i < telPrefix.length; i++) {
                    if (telPhone.substring(0, 3).equals(telPrefix[i])) {
                        index = i;
                        break;
                    }
                }
            }
            if (index == -1)
                return false;
            else {
                return telPhone.matches("\\d{11}");
            }
        }
    }

    /**
     * 判断密码格式是否正确
     * @param pwd
     * @return
     */
    public static boolean isPwd(String pwd) {
        String check = "^[0-9a-zA-Z]{6,16}$";//'/^[a-zA-Z0-9]{6,10}$/'
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(pwd);
        boolean isMatched = matcher.matches();
        return isMatched;
    }
    /**
     * 判断字符串是否符合手机号码的正则表达式规则
     */
    public static boolean isMatchesPhone(Context context, String user) {

        if (TextUtils.isEmpty(user)) {
            ToastUtils.makeText(context,
                    context.getString(R.string.error_input_phone));
            return false;
        }

        if (!StringUtils.isMatchesPhone(user)) {
            ToastUtils.makeText(context,
                    context.getString(R.string.error_input_phone));
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否符合密码的正则表达式规则
     */
    public static boolean isMatchesPwd(String pwd) {
        return pwd.matches("[\\da-zA-Z_]{6,20}");
    }

    /**
     * 判断字符串是否符合密码的正则表达式规则
     */
    public static boolean isMatchesPwd(Context context, String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.makeText(context,
                    context.getString(R.string.error_input_pwd_fail_info_tips));
            return false;
        }
        if (!StringUtils.isMatchesPwd(pwd)) {
            ToastUtils.makeText(context,
                    context.getString(R.string.error_input_pwd_fail_info_tips));
            return false;
        }

        return true;
    }

    /**
     * 判断字符串是否符合验证码的正则表达式规则
     */
    private static boolean isMatchesVerification(String text) {
        return text.matches("[\\d]{4}");
    }

    /**
     * 判断字符串是否符合验证码的正则表达式规则
     */
    public static boolean isMatchesVerification(Context context, String text) {
        if (TextUtils.isEmpty(text)) {
            ToastUtils.makeText(context,
                    context.getString(R.string.error_input_verification));
            return false;
        }
        if (!StringUtils.isMatchesVerification(text)) {
            ToastUtils.makeText(context, context
                    .getString(R.string.error_input_verification_fail_info));
            return false;
        }
        return true;
    }

    /**
     * 获取安全的手机号码，格式为XX******XXX
     *
     * @param tel
     * @return
     */
    public static String getSafeTelephoneStr(String tel) {
        if (TextUtils.isEmpty(tel))
            return null;
        try {
            return tel.substring(0, 2) + "******" + tel.substring(tel.length() - 3);
        } catch (Exception e) {
            CommonLog.e(e);
            return null;
        }
    }

    /**
     * 获取安全的邮箱，格式为XX******X@xx.com
     *
     * @param email
     * @return
     */
    public static String getSafeEmailStr(String email) {
        if (TextUtils.isEmpty(email))
            return null;
        try {
            return email.substring(0, 1) + "******" + email.substring(email.lastIndexOf("@") - 1);
        } catch (Exception e) {
            CommonLog.e(e);
            return null;
        }
    }
    /**
     * @param oldString
     * @param unit
     * @return
     */
    public static String parseStringToNumber(String oldString, String unit) {
        if (TextUtils.isEmpty(oldString)) {
            return null;
        }
        if (!TextUtils.isEmpty(unit)) {
            int index = oldString.indexOf(unit);
            if (index != -1) {
                return oldString.substring(0, index);
            }
        }
        return oldString;
    }

    public static String modifyDataFormat(String str){
        String result;
        result = str.substring(0,4) + "."+ str.substring(5,7) + "."+ str.substring(8,10);
        return result;
    }
}
