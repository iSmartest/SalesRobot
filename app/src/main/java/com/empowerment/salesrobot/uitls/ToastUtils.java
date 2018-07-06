package com.empowerment.salesrobot.uitls;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.empowerment.salesrobot.R;


/**
 * Created by 小火
 * Create time on  2017/9/27
 * My mailbox is 1403241630@qq.com
 */

public class ToastUtils {
    /**
     * 使用Toast显示信息，时间较短
     *
     * @param context
     * @param strId
     */
    public static void showMessageShort(Context context, int strId) {
        Toast.makeText(context, strId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 使用Toast显示信息，时间较短
     *
     * @param context
     * @param text
     */
    public static void showMessageShort(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 使用Toast显示信息，时间较长
     *
     * @param context
     * @param strId
     */
    public static void showMessageLong(Context context, int strId) {
        Toast.makeText(context, strId, Toast.LENGTH_LONG).show();
    }

    /**
     * 使用Toast显示信息，时间较长
     *
     * @param context
     * @param text
     */
    public static void showMessageLong(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


    private Toast mToast;
    private TextView textView;

    private ToastUtils(Context context, CharSequence text) {
        mToast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.eplay_toast, null);
        textView = view.findViewById(R.id.textView1);
        textView.setText(text);//设置文本
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setView(view);
        mToast.show();
    }

    public static ToastUtils makeText(Context context, CharSequence text) {
        return new ToastUtils(context, text);
    }

    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}

