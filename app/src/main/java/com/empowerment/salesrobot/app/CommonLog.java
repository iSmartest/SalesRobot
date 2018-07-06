package com.empowerment.salesrobot.app;

import android.util.Log;

/**
 * Created by 小火
 * Create time on  2017/3/22
 * My mailbox is 1403241630@qq.com
 */

public class CommonLog {
    /**
     * 设置显示的Log等级。 <li>在调试模式下，会显示所有的Log <li>在非调试模式下，当Log等级高于该等级时才会进行显示。<br>
     * 默认的Log等级为VERBOSE。
     */
    private static int logLevel = Log.VERBOSE;
    /**
     * 设置当前是否为调试模式
     */
    private static boolean isDebug = false;

    private static final String TAG_START = "tcjy";
    private static final String TAG_START2 = TAG_START + "-->";
    private static final String NULL_STRING = "NULL";

    /**
     * 获取堆栈信息
     * @return
     */
    private static StackTraceElement[] getStackTrace() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        int flag = 0;
        for (int i = 0; i < sts.length; i++) {
            if (sts[i].isNativeMethod()) {
                continue;
            }

            if (sts[i].getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (sts[i].getClassName().equals(CommonLog.class.getName())) {
                continue;
            }

            flag = i;
            break;
        }

        if (flag == sts.length - 1)
            return null;

        StackTraceElement[] newElements = new StackTraceElement[sts.length
                - flag];
        for (int i = flag; i < sts.length; i++) {
            newElements[i - flag] = sts[i];
        }

        return newElements;
    }

    /**
     * 获取Tag,目前的Tag为调用打印Log的语句所在位置
     * @return
     */
    private static String getTag() {
        StackTraceElement[] sts = getStackTrace();

        if (sts == null) {
            return TAG_START;
        }

        return TAG_START2 + Thread.currentThread().getId() + ":"
                + sts[0].getFileName() + "->" + sts[0].getMethodName() + ":"
                + sts[0].getLineNumber();
    }

    /**
     * 判断当前是否要打印Log
     *
     * @return <li>true 打印Log<li>false 不打印Log
     */
    private static boolean isShow(int priority) {
        return isDebug || priority >= logLevel;
    }

    public static void v(Object str) {
        if (isShow(Log.VERBOSE)) {
            Log.v(getTag(), str == null ? NULL_STRING : str.toString());
        }
    }

    public static void d(Object str) {
        if (isShow(Log.DEBUG)) {
            Log.d(getTag(), str == null ? NULL_STRING : str.toString());
        }
    }

    public static void i(Object str) {
        if (isShow(Log.INFO)) {
            Log.i(getTag(), str == null ? NULL_STRING : str.toString());
        }
    }

    public static void w(Object str) {
        if (isShow(Log.WARN)) {
            Log.w(getTag(), str == null ? NULL_STRING : str.toString());
        }
    }

    public static void w(Throwable thr) {
        if (isShow(Log.WARN)) {
            if (thr == null)
                Log.w(getTag(), NULL_STRING);
            else
                Log.w(getTag(), thr);
        }
    }

    public static void e(Object str) {
        if (isShow(Log.ERROR)) {
            Log.e(getTag(), str == null ? NULL_STRING : str.toString());
        }
    }

    public static void e(Throwable thr) {
        if (isShow(Log.ERROR)) {
            if (thr == null)
                Log.e(getTag(), NULL_STRING);
            else
                Log.e(getTag(), "", thr);
        }
    }

    /**
     * 打印Log当前调用栈信息
     *
     * @param priority Log等级
     */
    public static void printStackTrace(int priority) {
        if (isShow(priority)) {
            Exception exception = new Exception();
            StackTraceElement[] sts = getStackTrace();
            if (sts == null) {
                switch (priority) {
                    case Log.INFO:
                        Log.i(getTag(), NULL_STRING);
                        break;
                    case Log.DEBUG:
                        Log.d(getTag(), NULL_STRING);
                        break;
                    case Log.WARN:
                        Log.w(getTag(), NULL_STRING);
                        break;
                    case Log.ERROR:
                        Log.e(getTag(), NULL_STRING);
                        break;
                    default:
                        Log.v(getTag(), NULL_STRING);
                        break;
                }
            } else {
                exception.setStackTrace(sts);

                switch (priority) {
                    case Log.INFO:
                        Log.i(getTag(), "", exception);
                        break;
                    case Log.DEBUG:
                        Log.d(getTag(), "", exception);
                        break;
                    case Log.WARN:
                        Log.w(getTag(), exception);
                        break;
                    case Log.ERROR:
                        Log.e(getTag(), "", exception);
                        break;
                    default:
                        Log.v(getTag(), "", exception);
                        break;
                }
            }
        }
    }
}
