package com.empowerment.salesrobot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.view.wheelview.AbstractWheelTextAdapter1;
import com.empowerment.salesrobot.view.wheelview.OnWheelChangedListener;
import com.empowerment.salesrobot.view.wheelview.WheelView;

import java.util.ArrayList;

/**
 * Created by 小火
 * Create time on  2018/07/19
 * My mailbox is 1403241630@qq.com
 */

public class SelectedDialog extends Dialog implements
        View.OnClickListener {
    private OnSureBtnClickListener mSureListener;
    private Context mContext;
    private Button sureButton, cancelButton;
    private TextView mTitle;
    private WheelView mWVData;
    private LinearLayout lyChangeDataChild;
    private ArrayList<String> list;
    private int maxsize = 14;
    private int minsize = 12;
    private String strData;
    private SelectedTextAdapter selectedTextAdapter;
    public SelectedDialog(Context context, int title, String strData, ArrayList<String> list, OnSureBtnClickListener sureListener) {
        super(context);
        this.mContext = context;
        this.mSureListener = sureListener;
        this.list = list;
        this.strData = strData;
        setContentView(R.layout.selected_data);
        initView(title);
    }

    private void initView(int title) {
        lyChangeDataChild = (LinearLayout) findViewById(R.id.lyChangeDataChild);
        mTitle = (TextView) findViewById(R.id.text_select_title);
        mWVData = (WheelView) findViewById(R.id.wv_data);
        cancelButton = (Button) findViewById(R.id.select_btn_cancel);
        cancelButton.setOnClickListener(this);
        sureButton = (Button) findViewById(R.id.select_btn_sure);
        sureButton.setOnClickListener(this);
        mTitle.setText(title);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.textsize));
        selectedTextAdapter = new SelectedTextAdapter(mContext, list,getWvItem(strData), maxsize, minsize);
        mWVData.setVisibleItems(5);
        mWVData.setViewAdapter(selectedTextAdapter);
        mWVData.setCurrentItem(getWvItem(strData));
        mWVData.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) selectedTextAdapter.getItemText(wheel.getCurrentItem());
                strData = currentText;
                setTextviewSize(currentText, selectedTextAdapter);
            }
        });


        try{
            int dividerID = mContext.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider=findViewById(dividerID);
            divider.setBackgroundColor(Color.TRANSPARENT);
        }catch(Exception e){
            //上面的代码，是用来去除Holo主题的蓝色线条
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        if (v == sureButton) {
            if ( mSureListener != null) {
                mSureListener.sure(strData);
            }
        } else if (v == cancelButton) {

        } else if (v == lyChangeDataChild) {
            return;
        } else {

        }
        dismiss();
    }
    public interface OnSureBtnClickListener {
        public void sure(String s);
    }

    public int getWvItem(String province) {
        int size = list.size();
        int provinceIndex = 0;
        boolean noprovince = true;
        for (int i = 0; i < size; i++) {
            if (province.equals(list.get(i))) {
                noprovince = false;
                return provinceIndex;
            } else {
                provinceIndex++;
            }
        }
        if (noprovince) {

            return 7;
        }
        return provinceIndex;
    }

    private class SelectedTextAdapter extends AbstractWheelTextAdapter1 {
        ArrayList<String> list;

        protected SelectedTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_add_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, SelectedTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(14);
            } else {
                textvew.setTextSize(12);
            }
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
}
