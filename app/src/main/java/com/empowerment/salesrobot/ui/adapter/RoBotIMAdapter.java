package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
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
import com.empowerment.salesrobot.dialog.SeePictureDialog;
import com.empowerment.salesrobot.ui.activity.PlayVideoActivity;
import com.empowerment.salesrobot.ui.model.TrainRecordBean;
import com.empowerment.salesrobot.uitls.ImageManagerUtils;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/15.
 * Description:
 */
public class RoBotIMAdapter extends BaseAdapter {
    private Context context;
    private SeePictureDialog mSeePictureDialog;
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
        if (convertView == null ){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_robot_im,null);
            viewHolder = new RoBotIMHolder();
            viewHolder.mLeft = convertView.findViewById(R.id.left);
            viewHolder.mRight = convertView.findViewById(R.id.right);
            viewHolder.mMachineIcon = convertView.findViewById(R.id.iv_machine_icon);
            viewHolder.mUserIcon = convertView.findViewById(R.id.iv_user_icon);
            viewHolder.mLeftIamge = convertView.findViewById(R.id.iv_left_iamge);
            viewHolder.mRightIamge = convertView.findViewById(R.id.iv_right_iamge);
            viewHolder.mLeftContent = convertView.findViewById(R.id.text_left_content);
            viewHolder.mRightContent = convertView.findViewById(R.id.text_right_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (RoBotIMHolder) convertView.getTag();
        }
        TrainRecordBean.ContentRecord contentRecord = mList.get(position);
        if (contentRecord.getLeftOrRight() == 0){//左边
            viewHolder.mLeft.setVisibility(View.VISIBLE);
            viewHolder.mRight.setVisibility(View.GONE);
            if (contentRecord.getContentType() == 0){//图文
                viewHolder.mLeftIamge.setVisibility(View.VISIBLE);
                viewHolder.mLeftContent.setVisibility(View.GONE);
                viewHolder.mLeftIamge.setImageResource(R.drawable.mine_backround);
                viewHolder.mLeftIamge.setOnClickListener(v -> {
                    if (contentRecord.getPicLists() != null && !contentRecord.getPicLists().isEmpty()) {
                        mSeePictureDialog = new SeePictureDialog(context, contentRecord.getPicLists());
                        mSeePictureDialog.show();
                    }
                });
            }else if (contentRecord.getContentType() == 1){//视频
                viewHolder.mLeftIamge.setVisibility(View.VISIBLE);
                viewHolder.mLeftContent.setVisibility(View.GONE);
                viewHolder.mLeftIamge.setImageResource(R.drawable.sale_icon_img);
//                Glide.with(context).load("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg").into(viewHolder.mLeftIamge);
                viewHolder.mLeftIamge.setOnClickListener(v -> MyApplication.openActivity(context, PlayVideoActivity.class));
            }else if (contentRecord.getContentType() == 3){//图文+视频
                viewHolder.mLeftIamge.setVisibility(View.VISIBLE);
                viewHolder.mLeftContent.setVisibility(View.GONE);
                viewHolder.mLeftIamge.setImageResource(R.drawable.sale_icon_img);
                Glide.with(context).load(Url.HTTP+contentRecord.getPic()).into(viewHolder.mLeftIamge);
//                viewHolder.mLeftIamge.setOnClickListener(v -> MyApplication.openActivity(context, PlayVideoOrPicsActivity.class));
            }else {//文本
                viewHolder.mLeftIamge.setVisibility(View.GONE);
                viewHolder.mLeftContent.setVisibility(View.VISIBLE);
                viewHolder.mLeftContent.setText(contentRecord.getContent());
            }
        }else {//右边
            viewHolder.mLeft.setVisibility(View.GONE);
            viewHolder.mRight.setVisibility(View.VISIBLE);
            if (contentRecord.getContentType() == 0){//图文
                viewHolder.mRightIamge.setVisibility(View.VISIBLE);
                viewHolder.mRightContent.setVisibility(View.GONE);
//                viewHolder.mRightIamge.setImageResource(R.drawable.mine_backround);
                Glide.with(context).load(contentRecord.getPic()).into(viewHolder.mRightIamge);
            }else if (contentRecord.getContentType() == 1){//视频
                viewHolder.mRightIamge.setVisibility(View.VISIBLE);
                viewHolder.mRightContent.setVisibility(View.GONE);
                Glide.with(convertView).load(contentRecord.getUri()).into(viewHolder.mRightIamge);
                viewHolder.mRightIamge.setOnClickListener(v -> MyApplication.openActivity(context, PlayVideoActivity.class));
            }else {//文本
                viewHolder.mRightIamge.setVisibility(View.GONE);
                viewHolder.mRightContent.setVisibility(View.VISIBLE);
                viewHolder.mRightContent.setText(contentRecord.getContent());
            }
        }
        return convertView;
    }

    class RoBotIMHolder {
        LinearLayout mLeft,mRight;
        ImageView mMachineIcon,mLeftIamge,mUserIcon,mRightIamge;
        TextView mLeftContent,mRightContent;
    }
}
