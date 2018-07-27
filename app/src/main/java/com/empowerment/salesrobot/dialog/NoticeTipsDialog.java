package com.empowerment.salesrobot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.uitls.GlobalMethod;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/27.
 * Description:
 */
public class NoticeTipsDialog extends Dialog implements View.OnClickListener {
    private TextView mTips,mContent;
    private Button mButton;
    private View.OnClickListener mSureListener;

    public NoticeTipsDialog(Context context, String tips,String content,View.OnClickListener listener) {
        super(context, R.style.warn_window_dialog);
        mSureListener = listener;
        init(tips,content);
    }


    private void init(String tips, String content) {
        setContentView(R.layout.notice_tips_dialog);
        mTips = findViewById(R.id.d_tips_tv);
        mContent = findViewById(R.id.d_content_tv);
        mButton = findViewById(R.id.d_error_btn);
        mButton.setOnClickListener(this);
        mTips.setText(tips);
        mContent.setText(content);
    }

    public void setBtnTextView(String btnTextView) {
        if (!TextUtils.isEmpty(btnTextView))
            mButton.setText(btnTextView);
    }

    @Override
    public void show() {
        windowDeploy(0, 0);
        super.show();
    }

    public void windowDeploy(int x, int y) {
        Window window = getWindow(); // 得到对话框
        window.setBackgroundDrawableResource(android.R.color.transparent); // 设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = (int) (GlobalMethod.getWindowSize(getContext()).x * 0.75);
        // 根据x，y坐标设置窗口需要显示的位置
        wl.x = x; // x小于0左移，大于0右移
        wl.y = y; // y小于0上移，大于0下移
        wl.gravity = Gravity.CENTER;// 设置重力
        window.setAttributes(wl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.d_error_btn:
                if (mSureListener != null)
                    mSureListener.onClick(v);
                dismiss();
                break;
        }
    }
}
