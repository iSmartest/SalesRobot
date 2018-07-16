package com.empowerment.salesrobot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.RobotResultBean;
import com.empowerment.salesrobot.uitls.ImageManagerUtils;
import com.empowerment.salesrobot.view.PhotoView;

import java.util.List;


/**
 * Created by 小火
 * Create time on  2017/12/15
 * My mailbox is 1403241630@qq.com
 */

public class SeePictureDialog extends Dialog {
    private Context mContext;
    private List<RobotResultBean.DataBean.Answers.Pics> mShopImgList;
    private ViewPager mPager;
    private TextView mTotalItem,mPicInfo;
    private ImagAdapter mImagAdapter;
    public SeePictureDialog(Context context, List<RobotResultBean.DataBean.Answers.Pics> mShopImgList) {
        super(context);
        this.mContext = context;
        this.mShopImgList = mShopImgList;
        setContentView(R.layout.see_picture_tips);
        initView();
        try {
            int dividerID = mContext.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = findViewById(dividerID);
            divider.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {
            //上面的代码，是用来去除Holo主题的蓝色线条
            e.printStackTrace();
        }
    }

    private void initView() {
        mPager = findViewById(R.id.see_pager);
        mTotalItem = findViewById(R.id.text_total_item);
        mPicInfo = findViewById(R.id.text_pic_info);
        mTotalItem.setText(1 + "/" + mShopImgList.size());
        mPager.setPageMargin((int) (mContext.getResources().getDisplayMetrics().density * 15));
        mImagAdapter = new ImagAdapter(mContext,mShopImgList);
        mPager.setAdapter(mImagAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTotalItem.setText((position + 1)+"/" + mShopImgList.size());
                mPicInfo.setText(mShopImgList.get(position).getDes());
            }
        });
    }

    public class ImagAdapter extends PagerAdapter {
        private PhotoView photo_view;
        private Context mContext;
        private List<RobotResultBean.DataBean.Answers.Pics> mShopImgList;

        public ImagAdapter(Context mContext, List<RobotResultBean.DataBean.Answers.Pics> mShopImgList) {
            this.mContext = mContext;
            this.mShopImgList = mShopImgList;
        }

        @Override
        public int getCount() {
            return mShopImgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_see_picture, null);
            photo_view = view.findViewById(R.id.iv_see_picture);
            photo_view.enable();
            photo_view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            photo_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            ImageManagerUtils.imageLoader.displayImage(mShopImgList.get(position).getPics(),photo_view, ImageManagerUtils.options3);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
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
        wl.width = (mContext.getApplicationContext().getResources().getDisplayMetrics().widthPixels);
        wl.x = x;
        wl.y = y;
        wl.gravity = Gravity.CENTER;// 设置重力
        window.setAttributes(wl);
    }
}

