package com.empowerment.salesrobot.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.adapter.ImageAndTextAdapter;
import com.empowerment.salesrobot.ui.model.ImageBean;
import com.empowerment.salesrobot.uitls.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/13.
 * Description:
 */
public class ImageAndTextDialog extends Dialog implements View.OnClickListener, ImageAndTextAdapter.OnRecyclerViewItemClickListener, ImageAndTextAdapter.OnRemoveImageClickListener {

    private Context mContext;
    private Button sureButton, cancelButton;
    private OnSureBtnClickListener mSureListener;
    private RecyclerView recyclerView;
    private EditText mContent;
    private List<ImageBean> mBinnerList = new ArrayList<>();
    private int maxImgCount = 1;
    private ImageAndTextAdapter mImageAndTextAdapter;
    public static final int IMAGE_BINER_ITEM_ADD = -1;
    public ImageAndTextDialog(Context context, OnSureBtnClickListener sureListener) {
        super(context);
        this.mContext = context;
        this.mSureListener = sureListener;
        setContentView(R.layout.dialog_image_text);
        recyclerView = findViewById(R.id.recyclerView);
        mContent = findViewById(R.id.edit_content);
        cancelButton = findViewById(R.id.log_out_tips_btn_cancel);
        cancelButton.setOnClickListener(this);
        sureButton = findViewById(R.id.log_out_tips_btn_sure);
        sureButton.setOnClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setHasFixedSize(true);
        mImageAndTextAdapter = new ImageAndTextAdapter(context, mBinnerList, maxImgCount);
        mImageAndTextAdapter.setOnItemClickListener(this);
        mImageAndTextAdapter.setOnRemoveClickListener(this);
        recyclerView.setAdapter(mImageAndTextAdapter);

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
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_BINER_ITEM_ADD:
                PhotoPicker.builder()
                        .setPhotoCount(maxImgCount - mBinnerList.size())
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start((Activity) mContext, PhotoPicker.REQUEST_CODE);
                break;
        }
    }


    public void setImageList(List<ImageBean> binnerList){
        mBinnerList.addAll(binnerList);
        mImageAndTextAdapter.setImages(mBinnerList);
        mImageAndTextAdapter.notifyDataSetChanged();
    }

    public void setSureButtonText(int resourceId) {
        sureButton.setText(resourceId);
    }

    public void setCancelButtonText(int resourceId) {
        cancelButton.setText(resourceId);
    }

    @Override
    public void onRemoveBinnerClick(int position) {
        mBinnerList.remove(position);
        mImageAndTextAdapter.setImages(mBinnerList);
        mImageAndTextAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_out_tips_btn_cancel:
                dismiss();
                break;
            case R.id.log_out_tips_btn_sure:
                if (mSureListener != null) {
                    if (mContent.getText().toString().trim().isEmpty()){
                        ToastUtils.makeText(mContext,"图文回答内容不能为空");
                        return;
                    }
                    if (mBinnerList != null && !mBinnerList.isEmpty() && mBinnerList.size() > 0) {
                        mSureListener.sure(mContent.getText().toString().trim());
                        dismiss();
                    }else {
                        ToastUtils.makeText(mContext, "图文回答图片不能为空");
                    }
                }
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
        void sure(String content);
    }
}
