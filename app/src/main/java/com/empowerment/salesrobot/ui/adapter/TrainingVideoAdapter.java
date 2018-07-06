package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.TrainingVideoBean;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/4.
 * Description:
 */
public class TrainingVideoAdapter extends RecyclerView.Adapter<TrainingVideoAdapter.TrainingVideoViewHolder>{
    private Context context;
    private List<TrainingVideoBean.DataBean.VideoList> mList;
    public TrainingVideoAdapter(Context context, List<TrainingVideoBean.DataBean.VideoList> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public TrainingVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_training_video,parent,false);
        TrainingVideoViewHolder trainingVideoViewHolder = new TrainingVideoViewHolder(view);
        return trainingVideoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingVideoViewHolder holder, int position) {
        TrainingVideoBean.DataBean.VideoList videoList = mList.get(position);
        holder.mTitle.setText(videoList.getName());
        Glide.with(context).load(videoList.getCoverAddress()).error(R.drawable.image_fail_empty).into(holder.mCover);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class TrainingVideoViewHolder extends RecyclerView.ViewHolder{
        ImageView mCover;
        TextView mTitle;
        public TrainingVideoViewHolder(View itemView) {
            super(itemView);
            mCover = itemView.findViewById(R.id.iv_video_cover);
            mTitle = itemView.findViewById(R.id.tv_video_title);
        }
    }
}
