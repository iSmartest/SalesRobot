package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.MineBean;
import com.empowerment.salesrobot.uitls.TimeUtils;
import com.empowerment.salesrobot.view.RoundedImageView;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/5.
 * Description:
 */
public class UnderStandAdapter extends RecyclerView.Adapter<UnderStandAdapter.UnderstandViewHolder>{
    private Context context;
    private List<MineBean.DataBean.ContentListBean> mList;
    public UnderStandAdapter(Context context, List<MineBean.DataBean.ContentListBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public UnderstandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_understand,null);
        UnderstandViewHolder viewHolder = new UnderstandViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UnderstandViewHolder holder, int position) {
        MineBean.DataBean.ContentListBean contentListBean = mList.get(position);
        holder.mUnderstand.setText(contentListBean.getContent());
        holder.mTime.setText(TimeUtils.transferLongToDate(contentListBean.getData()));
        Glide.with(context).load(contentListBean.getImage()).error(R.drawable.my_head_portrait).into(holder.mIcon);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class UnderstandViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView mIcon;
        TextView mUnderstand;
        TextView mTime;
        public UnderstandViewHolder(View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.iv_sale_icon);
            mUnderstand = itemView.findViewById(R.id.tv_sale_understand);
            mTime = itemView.findViewById(R.id.tv_sale_time);

        }
    }
}
