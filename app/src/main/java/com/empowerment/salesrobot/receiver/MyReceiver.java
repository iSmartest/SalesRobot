package com.empowerment.salesrobot.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.ui.activity.AgencyAffairsInfoActivity;
import com.empowerment.salesrobot.ui.activity.MainActivity;
import com.empowerment.salesrobot.ui.activity.NoticeTipsActivity;
import com.empowerment.salesrobot.uitls.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadger;

import static com.empowerment.salesrobot.app.Constant.badgeCount;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;


/**
 * Created by 小火
 * Create time on  2017/6/1
 * My mailbox is 1403241630@qq.com
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG";

    // RegistrationID 定义
    // 集成了 JPush SDK 的应用程序在第一次成功注册到 JPush 服务器时，
    // JPush 服务器会给客户端返回一个唯一的该设备的标识 - RegistrationID。
    // JPush SDK 会以广播的形式发送 RegistrationID 到应用程序。
    // 应用程序可以把此 RegistrationID 保存以自己的应用服务器上，
    // 然后就可以根据 RegistrationID 来向设备推送消息或者通知。
    public static String regId;
    private int mCount;
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            switch (intent.getAction()) {
                case JPushInterface.ACTION_REGISTRATION_ID:
                    regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                    Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                    break;
                case JPushInterface.ACTION_MESSAGE_RECEIVED:
                    Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                    // 对应极光后台的 - 自定义消息  默认不会出现在notification上 所以一般都选用发送通知
                    break;
                case JPushInterface.ACTION_NOTIFICATION_RECEIVED:
                    Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
                    if (isApplicationBroughtToBackground(context)){
                        badgeCount ++;
                        ShortcutBadger.applyCount(context,badgeCount);
                    }else {
                        JPushInterface.clearAllNotifications(context);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("extra",bundle.getString(JPushInterface.EXTRA_EXTRA));
                        bundle1.putString("content",bundle.getString(JPushInterface.EXTRA_ALERT));
                        bundle1.putString("title",bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
                        MyApplication.openActivity(context, NoticeTipsActivity.class,bundle1);
                    }
                    break;
                case JPushInterface.ACTION_NOTIFICATION_OPENED:
                    Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
                    openAppOrActivity(context, bundle);
                    break;
                case JPushInterface.ACTION_RICHPUSH_CALLBACK:
                    Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                    //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
                    break;
                case JPushInterface.ACTION_CONNECTION_CHANGE:
                    boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                    Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
                    break;
                default:
                    Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
                    break;
            }
        }catch (Exception e){
        }
    }

    private void openAppOrActivity(Context context, Bundle  extra) {
        badgeCount = 0;
        ShortcutBadger.removeCount(context);
        //判断app进程是否处于前台 此时用户处于登陆状态userId!=0
        if (isLogin(context)) {
            Intent detailIntent = parseJpushBundle(context, extra);
            if (detailIntent == null)
                return;
            detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(detailIntent);
        } else {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            if (launchIntent == null)
                return;
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            launchIntent.putExtra("jpush", extra);
            context.startActivity(launchIntent);
        }
    }

    public static Intent parseJpushBundle(Context context, Bundle bundle) {
        Intent detailIntent = null;

        Log.i("sdfdf", "parseJpushBundle: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
        try {
            JSONObject object = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            String content = object.getString("content");
            String endDate = object.getString("endDate");
            String finish = object.getString("finish");
            String id = object.getString("id");
            String type = object.getString("type");
            switch (type) {
                case "1": //公司待办
                    detailIntent = new Intent(context, AgencyAffairsInfoActivity.class);
                    detailIntent.putExtra("type", type);
                    detailIntent.putExtra("id", id);
                    detailIntent.putExtra("isFinish", finish);
                    detailIntent.putExtra("content", content);
                    detailIntent.putExtra("time", endDate);
                    break;
                case "3": //个人待办
                    detailIntent = new Intent(context, AgencyAffairsInfoActivity.class);
                    detailIntent.putExtra("type", type);
                    detailIntent.putExtra("id", id);
                    detailIntent.putExtra("isFinish", finish);
                    detailIntent.putExtra("content", content);
                    detailIntent.putExtra("time", endDate);
                    break;
                case "4": //系统消息
                    detailIntent = new Intent(context, MainActivity.class);
                    detailIntent.putExtra("content", content);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return detailIntent;
    }

    public static boolean isLogin(Context context) {
        if (!TextUtils.isEmpty(SPUtil.getString(context,SALE_ID)))
            return true;
        else
            return false;
    }
    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            switch (key) {
                case JPushInterface.EXTRA_NOTIFICATION_ID:
                    sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
                    break;
                case JPushInterface.EXTRA_CONNECTION_CHANGE:
                    sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
                    break;
                case JPushInterface.EXTRA_EXTRA:
                    if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                        Log.i(TAG, "This message has no Extra data");
                        continue;
                    }
                    try {
                        JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                        Iterator<String> it = json.keys();

                        while (it.hasNext()) {
                            String myKey = it.next().toString();
                            sb.append("\nkey:" + key + ", value: [" +
                                    myKey + " - " + json.optString(myKey) + "]");
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Get message extra JSON error!");
                    }
                    break;
                default:
                    sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
                    break;
            }
        }
        return sb.toString();
    }

}
