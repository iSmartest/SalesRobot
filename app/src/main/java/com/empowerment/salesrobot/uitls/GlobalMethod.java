package com.empowerment.salesrobot.uitls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.empowerment.salesrobot.app.CommonLog;
import com.empowerment.salesrobot.app.Constant;
import com.empowerment.salesrobot.app.MyApplication;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


/**
 * 全局方法工具类
 */
public class GlobalMethod {
    /**
     * The method to get is network available or not
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = Objects.requireNonNull(manager).getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        }
        return false;
    }

    public static Point getWindowSize(Context context) {
        Point p = new Point();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager mWm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Objects.requireNonNull(mWm).getDefaultDisplay().getMetrics(dm);
        p.x = dm.widthPixels;
        p.y = dm.heightPixels;
        return p;
    }

    public static String getAbsoluteImagePath(Activity a, Uri uri) {
        // can post image
        String[] proj = {MediaColumns.DATA};
        @SuppressLint("Recycle") Cursor cursor = a.getContentResolver().query(uri, proj, // Which columns
                // to return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)

        int column_index = Objects.requireNonNull(cursor).getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static String getRealPathFromURI(Activity a, Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = a.getContentResolver().query(contentUri, proj, null, null, null);
        if (Objects.requireNonNull(cursor).moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /**
     * 获取屏幕的宽高，以px为单位
     *
     * @param context
     * @return display[0] 屏幕宽，display[1]屏幕高 单位dip
     */
    public static int[] getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources()
                .getDisplayMetrics(); // 屏幕对象

        int screenWidth = dm.widthPixels; // 屏幕宽度
        int screenHeight = dm.heightPixels; // 屏幕高度

        int[] display = new int[]{screenWidth, screenHeight};
        return display;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取顶部广告位高度  宽：高=5：3
     *
     * @param context
     * @return
     */
    public static int getADHeight(Context context) {
        return (getDisplayMetrics(context)[0] /2);
    }

    /**
     * 获取中部广告位高度  宽：高=4：1
     *
     * @param context
     * @return
     */
    public static int getMiddleADHeight(Context context) {
        return (getDisplayMetrics(context)[0] / 4);
    }

    /**
     * 获取当前网络类型
     *
     * @param context
     * @return
     */
    public static int getNetState(Context context) {
        int state = Constant.NetState.NOWAY;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = Objects.requireNonNull(manager).getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            int type = info.getType();
            if (type == ConnectivityManager.TYPE_MOBILE)
                state = Constant.NetState.Mobile;
            else if (type == ConnectivityManager.TYPE_WIFI)
                state = Constant.NetState.WIFI;
        }
        return state;

    }

    /**
     * 获取当前版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        try {
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            CommonLog.e(e);
        }
        return versionCode;
    }

    /**
     * 获取安装渠道
     *
     * @return
     */
    public static String getInstallChannel() {
        String data = "";
        try {
            data = MyApplication.getApplication().getPackageManager().getApplicationInfo(
                    MyApplication.getApplication().getPackageName(), PackageManager.GET_META_DATA)
                    .metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            CommonLog.e(e);
        }
        return data;
    }

    /**
     * 获取版本号名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionCode = "";
        try {
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            CommonLog.e(e);
        }
        return versionCode;
    }

    /**
     * 将文件大小转化为具体单位
     *
     * @param size
     * @return
     */
    public static String FormatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSize;
        if (size == 0)
            fileSize = "0B";
        else if (size < 1024) {
            fileSize = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSize = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSize = df.format((double) size / 1048576) + "M";
        } else
            fileSize = df.format((double) size / 1073741824) + "G";
        return fileSize;
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static long getFileLength(File file) {
        if (file.isFile()) {
            return file.length();
        }
        if (file.list().length == 0) {
            return 0;
        }
        long length = 0;
        File[] files = file.listFiles();
        for (File f : files) {
            length += f.length();
        }
        return length;
    }

    /**
     * 判断应用是否已经启动
     *
     * @param context 一个context
     * @return boolean
     */
    public static boolean isAppAlive(Context context) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = Objects.requireNonNull(activityManager).getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前app是否处于前台运行
     *
     * @param context
     * @return
     */
    public static boolean isAppForeGround(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = Objects.requireNonNull(activityManager)
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            CommonLog.i("processName=" + appProcess.processName + "    importance=" + appProcess.importance);
            if (appProcess.processName.equals(context.getPackageName()) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置EditText的提示内容，并将焦点设置在mEditText上，并且弹出软键盘。
     *
     * @param hint     EditText的提示内容
     * @param editText
     * @param activity
     */
    public static void showSoftInput(String hint, EditText editText, Activity activity) {
        editText.setHint(hint);
        editText.requestFocus();
        editText.setText(null);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 关闭软键盘
     */
    public static void dismissSoftInput(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(inputmanger).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 获取一个新的URI，用以指向新拍摄的照片存放的位置
     *
     * @return
     */
    public static Uri getNewPhotoUri() {
        File dir = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DCIM);
        return Uri.fromFile(new File(dir, System.currentTimeMillis() + ".jpg"));
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return codePoint == 0x0 || codePoint == 0x9 || codePoint == 0xA || codePoint == 0xD || codePoint >= 0x20 && codePoint <= 0xD7FF || codePoint >= 0xE000 && codePoint <= 0xFFFD;
    }

    /**
     * 获取当前APK使用的签明文件SHA1
     * @param context
     * @return
     */
    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (byte aPublicKey : publicKey) {
                String appendString = Integer.toHexString(0xFF & aPublicKey)
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * dp转px
     */

    public static int dip2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }


    /**
     * sp转px
     */

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }


    /**
     * px转dp
     */

    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     */

    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);

    }
}