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
import com.empowerment.salesrobot.config.BaseUrl;
import com.empowerment.salesrobot.view.wheelview.AbstractWheelTextAdapter1;
import com.empowerment.salesrobot.view.wheelview.OnWheelChangedListener;
import com.empowerment.salesrobot.view.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/12.
 * Description:
 */
public class SelectedTimeDialog extends Dialog implements
        View.OnClickListener {
    private OnSureBtnClickListener mSureListener;
    private Context mContext;
    private Button sureButton, cancelButton;
    private TextView mTitle;
    private WheelView mWVHour;
    private WheelView mWVMinute;
    private LinearLayout lyChangeDataChild;
    private int maxsize = 14;
    private int minsize = 12;
    private String strHourData = "08";
    private String strMinuteData = "30";
    private SelectedTextAdapter selectedHourAdapter, selectedMinuteAdapter;

    public SelectedTimeDialog(Context context, int title, OnSureBtnClickListener sureListener) {
        super(context);
        setContentView(R.layout.selected_time);
        this.mContext = context;
        this.mSureListener = sureListener;
        initView(title);
    }

    private void initView(int title) {
        lyChangeDataChild = findViewById(R.id.lyChangeDataChild);
        mTitle = findViewById(R.id.text_select_time_title);
        mWVHour = findViewById(R.id.wv_hour);
        mWVMinute = findViewById(R.id.wv_minute);
        cancelButton = findViewById(R.id.select_time_btn_cancel);
        cancelButton.setOnClickListener(this);
        sureButton = findViewById(R.id.select_time_btn_sure);
        sureButton.setOnClickListener(this);
        mTitle.setText(title);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.textsize));
        selectedMinuteAdapter = new SelectedTextAdapter(mContext, BaseUrl.getMinuteList(), getWvMinuteItem(strMinuteData), maxsize, minsize);

        selectedHourAdapter = new SelectedTextAdapter(mContext, BaseUrl.getHourList(), getWvHourItem(strHourData), maxsize, minsize);
        mWVHour.setVisibleItems(5);
        mWVHour.setViewAdapter(selectedHourAdapter);
        mWVHour.setCurrentItem(getWvHourItem(strHourData));
        mWVHour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) selectedHourAdapter.getItemText(wheel.getCurrentItem());
                strHourData = currentText;
                setTextviewSize(currentText, selectedHourAdapter);
            }
        });

        mWVMinute.setVisibleItems(5);
        mWVMinute.setViewAdapter(selectedMinuteAdapter);
        mWVMinute.setCurrentItem(getWvMinuteItem(strMinuteData));
        mWVMinute.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) selectedMinuteAdapter.getItemText(wheel.getCurrentItem());
                strMinuteData = currentText;
                setTextviewSize(currentText, selectedMinuteAdapter);
            }
        });
        try {
            int dividerID = mContext.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = findViewById(dividerID);
            divider.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            //上面的代码，是用来去除Holo主题的蓝色线条
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == sureButton) {
            if (mSureListener != null) {
                mSureListener.sure(strHourData + ":" + strMinuteData);
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

    //
    public int getWvHourItem(String province) {
        int size = BaseUrl.getHourList().size();
        int provinceIndex = 0;
        boolean noprovince = true;
        for (int i = 0; i < size; i++) {
            if (province.equals(BaseUrl.getHourList().get(i))) {
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

    public int getWvMinuteItem(String province) {
        int size = BaseUrl.getMinuteList().size();
        int provinceIndex = 0;
        boolean noprovince = true;
        for (int i = 0; i < size; i++) {
            if (province.equals(BaseUrl.getMinuteList().get(i))) {
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
        List<String> list;

        protected SelectedTextAdapter(Context context, List<String> list, int currentItem, int maxsize, int minsize) {
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
