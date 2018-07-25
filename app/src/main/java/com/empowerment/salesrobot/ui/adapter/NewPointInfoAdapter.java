package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.NewPointInfoBean;
import com.empowerment.salesrobot.uitls.GlideUtils;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/25.
 * Description:
 */
public class NewPointInfoAdapter extends RecyclerView.Adapter<NewPointInfoAdapter.NewPointInfoViewHolder>{

    private Context context;
    private List<NewPointInfoBean.DataBean.BuyPointDetail> mList;
    public NewPointInfoAdapter(Context context, List<NewPointInfoBean.DataBean.BuyPointDetail> mList) {
        this.context = context;
        this.mList = mList;

    }

    @NonNull
    @Override
    public NewPointInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new_point_info,parent,false);
        NewPointInfoViewHolder viewHolder = new NewPointInfoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewPointInfoViewHolder holder, int position) {
        NewPointInfoBean.DataBean.BuyPointDetail pointDetail = mList.get(position);
        holder.mDec.setText(pointDetail.getSellPoint());
        if (pointDetail.getType() == 1){//图文+视频
            String img = pointDetail.getSellPointdsc().get(0).getImg();
            GlideUtils.imageLoader(context,img,holder.mPic);
        }else if (pointDetail.getType() == 2){//图文
            String img = pointDetail.getSellPointdsc().get(0).getImg();
            GlideUtils.imageLoader(context,img,holder.mPic);
        }else {//视频
            holder.mPic.setImageResource(R.drawable.video_cover_icon);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class NewPointInfoViewHolder extends RecyclerView.ViewHolder{
        ImageView mPic;
        TextView mDec;
        public NewPointInfoViewHolder(View itemView) {
            super(itemView);
            mPic = itemView.findViewById(R.id.iv_new_point_pic);
            mDec = itemView.findViewById(R.id.tv_new_point_dec);
        }
    }
}
