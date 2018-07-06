package com.empowerment.salesrobot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.empowerment.salesrobot.R;


/**
 * Created by 小火
 * Create time on  2017/10/13
 * My mailbox is 1403241630@qq.com
 */

public class LogOutDialog extends Dialog implements
        View.OnClickListener {
    private TextView tv_tips;
    private Context mContext;
    private Button sureButton, cancelButton;

    OnSureBtnClickListener mSureListener;

    public LogOutDialog(Context context, String tips, String cancel, String sure,
                        OnSureBtnClickListener sureListener) {
        super(context);
        this.mContext = context;
        this.mSureListener = sureListener;
        init(tips,cancel,sure);
    }
    public LogOutDialog(Context context, int tips,
                        OnSureBtnClickListener sureListener) {
        super(context);
        this.mContext = context;
        this.mSureListener = sureListener;

        setContentView(R.layout.log_out_tips);

        tv_tips = findViewById(R.id.log_out_tips_tv);
        cancelButton = findViewById(R.id.log_out_tips_btn_cancel);
        cancelButton.setOnClickListener(this);
        sureButton = findViewById(R.id.log_out_tips_btn_sure);
        sureButton.setOnClickListener(this);
        tv_tips.setText(tips);
        tv_tips.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.default_text_size));
        try{
            int dividerID = mContext.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider=findViewById(dividerID);
            divider.setBackgroundColor(Color.TRANSPARENT);
        }catch(Exception e){
            //上面的代码，是用来去除Holo主题的蓝色线条
            e.printStackTrace();
        }
    }

    private void init(String tips, String cancel, String sure) {
        setContentView(R.layout.log_out_tips);
        tv_tips = findViewById(R.id.log_out_tips_tv);
        cancelButton = findViewById(R.id.log_out_tips_btn_cancel);
        cancelButton.setOnClickListener(this);
        cancelButton.setText(cancel);
        sureButton = findViewById(R.id.log_out_tips_btn_sure);
        sureButton.setOnClickListener(this);
        sureButton.setText(sure);
        tv_tips.setText(tips);
        tv_tips.setTextSize(20);
        try{
            int dividerID = mContext.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider=findViewById(dividerID);
            divider.setBackgroundColor(Color.TRANSPARENT);
        }catch(Exception e){
            //上面的代码，是用来去除Holo主题的蓝色线条
            e.printStackTrace();
        }
    }


    public void setTips(int resourceId) {
        tv_tips.setText(resourceId);
    }

    public void setTips(String formatStr) {
        tv_tips.setText(String.format(
                mContext.getResources().getString(R.string.sure_to_do_sth),
                formatStr));
    }

    public void setSureButtonText(int resourceId) {
        sureButton.setText(resourceId);
    }

    public void setCancelButtonText(int resourceId) {
        cancelButton.setText(resourceId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_out_tips_btn_cancel:
                dismiss();
                break;
            case R.id.log_out_tips_btn_sure:
                if (mSureListener != null) {
                    mSureListener.sure();
                }
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        windowDeploy(0, 0);
        super.show();
    }

    public void windowDeploy(int x, int y) {
        Window window = getWindow(); // 得到对话框
        window.setBackgroundDrawableResource(R.drawable.transpant_bg); // 设置对话框背景为透明

        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = (int) (mContext.getApplicationContext().getResources()
                .getDisplayMetrics().widthPixels * 0.75);
        wl.x = x;
        wl.y = y;
        wl.gravity = Gravity.CENTER;// 设置重力
        window.setAttributes(wl);
    }

    public interface OnSureBtnClickListener {
        void sure();
    }
}
