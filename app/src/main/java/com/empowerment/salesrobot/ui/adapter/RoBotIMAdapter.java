package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.ui.activity.SeePictureActivity;
import com.empowerment.salesrobot.ui.activity.PlayVideoActivity;
import com.empowerment.salesrobot.ui.model.TrainRecordBean;
import com.empowerment.salesrobot.uitls.GlideUtils;
import com.empowerment.salesrobot.view.RCRelativeLayout;

import java.io.Serializable;
import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/15.
 * Description:
 */
public class RoBotIMAdapter extends BaseAdapter {
    private Context context;
    private List<TrainRecordBean.ContentRecord> mList;

    public RoBotIMAdapter(Context context, List<TrainRecordBean.ContentRecord> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RoBotIMHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_robot_im, null);
            viewHolder = new RoBotIMHolder();
            viewHolder.mLeft = convertView.findViewById(R.id.left);
            viewHolder.mRight = convertView.findViewById(R.id.right);
            viewHolder.mMachineIcon = convertView.findViewById(R.id.iv_machine_icon);
            viewHolder.mUserIcon = convertView.findViewById(R.id.iv_user_icon);
            viewHolder.mLeftIamge = convertView.findViewById(R.id.iv_left_iamge);
            viewHolder.mRightIamge = convertView.findViewById(R.id.iv_right_iamge);
            viewHolder.mLeftContent = convertView.findViewById(R.id.text_left_content);
            viewHolder.mRightContent = convertView.findViewById(R.id.text_right_content);
            viewHolder.mRCLeftImage = convertView.findViewById(R.id.rc_left_image);
            viewHolder.mRCRightImage = convertView.findViewById(R.id.rc_right_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RoBotIMHolder) convertView.getTag();
        }
        TrainRecordBean.ContentRecord contentRecord = mList.get(position);
        if (contentRecord.getLeftOrRight() == 0) {//左边
            viewHolder.mLeft.setVisibility(View.VISIBLE);
            viewHolder.mRight.setVisibility(View.GONE);
            switch (contentRecord.getContentType()) {
                case 0: //图文
                    viewHolder.mRCLeftImage.setVisibility(View.VISIBLE);
                    viewHolder.mLeftContent.setVisibility(View.GONE);
                    viewHolder.mLeftIamge.setImageResource(R.drawable.mine_backround);
                    break;
                case 1: //视频
                    viewHolder.mRCLeftImage.setVisibility(View.VISIBLE);
                    viewHolder.mLeftContent.setVisibility(View.GONE);
                    viewHolder.mLeftIamge.setImageResource(R.drawable.sale_icon_img);
                    break;
                case 3: //图文+视频
                    viewHolder.mRCLeftImage.setVisibility(View.VISIBLE);
                    viewHolder.mLeftContent.setVisibility(View.GONE);
                    viewHolder.mLeftIamge.setImageResource(R.drawable.sale_icon_img);
                    GlideUtils.imageLoader(context, Url.HTTP + contentRecord.getPic(), viewHolder.mLeftIamge);
                    break;
                default: //文本
                    viewHolder.mRCLeftImage.setVisibility(View.GONE);
                    viewHolder.mLeftContent.setVisibility(View.VISIBLE);
                    viewHolder.mLeftContent.setText(contentRecord.getContent());
                    break;
            }
        } else {//右边
            viewHolder.mLeft.setVisibility(View.GONE);
            viewHolder.mRight.setVisibility(View.VISIBLE);
            switch (contentRecord.getContentType()) {
                case 0: //图文
                    viewHolder.mRCRightImage.setVisibility(View.VISIBLE);
                    viewHolder.mRightContent.setVisibility(View.GONE);
                    Glide.with(context).load(contentRecord.getPic()).into(viewHolder.mRightIamge);
                    break;
                case 1: //视频
                    viewHolder.mRCRightImage.setVisibility(View.VISIBLE);
                    viewHolder.mRightContent.setVisibility(View.GONE);
                    Glide.with(convertView).load(contentRecord.getUri()).into(viewHolder.mRightIamge);
                    break;
                default: //文本
                    viewHolder.mRCRightImage.setVisibility(View.GONE);
                    viewHolder.mRightContent.setVisibility(View.VISIBLE);
                    viewHolder.mRightContent.setText(contentRecord.getContent());
                    break;
            }
        }
        return convertView;
    }


    class RoBotIMHolder {
        LinearLayout mLeft, mRight;
        ImageView mMachineIcon, mLeftIamge, mUserIcon, mRightIamge;
        TextView mLeftContent, mRightContent;
        RCRelativeLayout mRCLeftImage, mRCRightImage;
    }
}
