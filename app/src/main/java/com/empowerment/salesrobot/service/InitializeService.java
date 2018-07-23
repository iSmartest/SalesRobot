package com.empowerment.salesrobot.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.empowerment.salesrobot.uitls.CrashHandler;

import cn.jpush.android.api.JPushInterface;

import static com.empowerment.salesrobot.app.MyApplication.initImageLoader;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/23.
 * Description:
 */
public class InitializeService extends IntentService{

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.empowerment.salesrobot.service.action.INIT";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context)
    {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null)
        {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit()
    {
        initImageLoader(this.getApplicationContext());
        JPushInterface.setDebugMode(true);//如果时正式版就改成false
        JPushInterface.init(this.getApplicationContext());
        //崩溃错误日志写入本地文档
        CrashHandler catchExcep = new CrashHandler(this.getApplication());
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }
}
