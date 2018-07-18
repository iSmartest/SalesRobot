package com.empowerment.salesrobot.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;


import com.empowerment.salesrobot.uitls.CrashHandler;
import com.empowerment.salesrobot.uitls.ImageManagerUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by 小火
 * Create time on  2017/10/24
 * My mailbox is 1403241630@qq.com
 */

public class MyApplication extends Application {
    public static Context CONTEXT;
    private static MyApplication myApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = getApplicationContext();
        initImageLoader(this);
        JPushInterface.setDebugMode(true);//如果时正式版就改成false
        JPushInterface.init(this);
        myApplication = this;
        //崩溃错误日志写入本地文档
        CrashHandler catchExcep = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);

    }

    public static Context getContext(){
        return CONTEXT;
    }
    public static MyApplication getApplication() {
        return myApplication;
    }

    /**
     * 通过类名启动Activity
     *
     * @param targetClass
     */
    public static void openActivity(Context context, Class<?> targetClass) {
        openActivity(context, targetClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param targetClass
     * @param extras
     */
    public static void openActivity(Context context, Class<?> targetClass,
                                    Bundle extras) {
        Intent intent = new Intent(context, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void openActivityForResult(Activity activity,
                                             Class<?> targetClass, Bundle extras, int requestCode) {
        Intent intent = new Intent(activity, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void openActivityForResult(Activity activity,
                                             Class<?> targetClass, int requestCode) {
        openActivityForResult(activity, targetClass, null, requestCode);
    }

    /**
     * 通过Action启动Activity
     *
     * @param action
     */
    public static void openActivity(Context context, String action) {
        openActivity(context, action, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param action
     * @param extras
     */
    public static void openActivity(Context context, String action,
                                    Bundle extras) {
        Intent intent = new Intent(action);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    public void exit() {
        //使用状态统计-结束
        ImageManagerUtils.imageLoader.destroy();
        android.os.Process.killProcess(android.os.Process.myPid());
        ActivityManager activityMgr = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        activityMgr.killBackgroundProcesses(getPackageName());
        System.exit(0);
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);

        ImageManagerUtils.init();
    }

    public static void getWindows(Activity con) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //实现状态栏图标和文字颜色为暗色
            con.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        con.getWindow().getDecorView().findViewById(android.R.id.content)
                .setPadding(0, 0, 0, 0);
    }
}
