package com.empowerment.salesrobot.uitls;

import android.app.Application;
import android.os.Environment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * 类说明: 全局异常处理类
 *
 * Created by 小火
 * Create time on  2017/5/15
 * My mailbox is 1403241630@qq.com
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    public static final String TAG = "CatchExcep";
    Application application;

    public CrashHandler(Application application) {
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.application = application;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
//            Intent intent = new Intent(application.getApplicationContext(), TabBottomActivity.class);
//            PendingIntent restartIntent = PendingIntent.getActivity(
//                    application.getApplicationContext(), 0, intent,
//                    Intent.FLAG_ACTIVITY_NEW_TASK);
//            //退出当前程序，跳转到首页 TabBottomActivity.class
//            AlarmManager mgr = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
//            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
//            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

       /* AbLogUtil.e("BCoser", ex.getMessage());
        ex.printStackTrace();*/

        try {
            //将crash log写入文件
            FileOutputStream fileOutputStream
                    = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/机器人销售端错误报告.txt", true);
            PrintStream printStream = new PrintStream(fileOutputStream);
            ex.printStackTrace(printStream);
            printStream.flush();
            printStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*//使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
              //  GlobalApplication.showToast(application.getApplicationContext(),"很抱歉,程序出现异常,即将退出");
                Looper.loop();
            }
        }.start();*/
        return true;
    }


}
