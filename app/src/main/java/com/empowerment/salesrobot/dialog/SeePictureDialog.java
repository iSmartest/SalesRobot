package com.empowerment.salesrobot.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
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
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.ui.activity.PlayVideoActivity;
import com.empowerment.salesrobot.ui.model.NewPointInfoBean;
import com.empowerment.salesrobot.uitls.GlideUtils;
import com.empowerment.salesrobot.view.PhotoView;

import java.util.List;

import static com.empowerment.salesrobot.config.Url.HTTP;


/**
 * Created by 小火
 * Create time on  2018/7/25
 * My mailbox is 1403241630@qq.com
 */

public class SeePictureDialog extends Dialog {
    private Context mContext;
    private List<NewPointInfoBean.DataBean.BuyPointDetail.SellPointdsc> mList;
    private ViewPager mPager;
    private LinearLayout mLlPicture;
    private TextView mTotalItem,mPicInfo;
    private ImageView mPlay;
    private ImageAdapter mImageAdapter;
    private String videoUrl = "";
    private String name = "";
    private boolean isVISIBLE = true;
    public SeePictureDialog(Context context, List<NewPointInfoBean.DataBean.BuyPointDetail.SellPointdsc> mList) {
        super(context);
        this.mContext = context;
        this.mList = mList;
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

    public SeePictureDialog(Context context, List<NewPointInfoBean.DataBean.BuyPointDetail.SellPointdsc> mList, String videoUrl, String name) {
        super(context);
        this.mContext = context;
        this.mList = mList;
        this.videoUrl = videoUrl;
        this.name = name;
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
        findViewById(R.id.title_Layout).setVisibility(View.GONE);
        mPager = findViewById(R.id.see_pager);
        mLlPicture = findViewById(R.id.ll_see_picture);
        mTotalItem = findViewById(R.id.text_total_item);
        mPicInfo = findViewById(R.id.text_pic_info);
        mPlay = findViewById(R.id.iv_play_video);
        if (videoUrl.isEmpty()){
            mPlay.setVisibility(View.GONE);
        }else {
            mPlay.setVisibility(View.VISIBLE);
        }
        mTotalItem.setText(1 + "/" + mList.size());
        mPicInfo.setText(mList.get(0).getDsc());
        mPicInfo.setMaxHeight(600);
        mPicInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
        mPager.setPageMargin((int) (mContext.getResources().getDisplayMetrics().density * 15));
        mImageAdapter = new ImageAdapter(mContext,mList);
        mPager.setAdapter(mImageAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTotalItem.setText((position + 1)+"/" + mList.size());
                mPicInfo.setText(mList.get(position).getDsc());
            }
        });

        mPlay.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("uri","");
            bundle.putString("url",HTTP+videoUrl);
            bundle.putString("mName",name);
            MyApplication.openActivity(mContext, PlayVideoActivity.class,bundle);
        });
    }

    public class ImageAdapter extends PagerAdapter {
        private PhotoView photo_view;
        private Context mContext;
        private List<NewPointInfoBean.DataBean.BuyPointDetail.SellPointdsc> sellPointdscs;

        public ImageAdapter(Context mContext, List<NewPointInfoBean.DataBean.BuyPointDetail.SellPointdsc> sellPointdscs) {
            this.mContext = mContext;
            this.sellPointdscs = sellPointdscs;
        }

        @Override
        public int getCount() {
            return sellPointdscs.size();
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
            photo_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isVISIBLE){
                        mLlPicture.setVisibility(View.GONE);
                        isVISIBLE = false;
                    }else {
                        mLlPicture.setVisibility(View.VISIBLE);
                        isVISIBLE = true;
                    }

                }
            });
            GlideUtils.imageLoader(mContext,HTTP+sellPointdscs.get(position).getImg(),photo_view);
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

