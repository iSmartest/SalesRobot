package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.AgencyAffairsBean;
import com.empowerment.salesrobot.uitls.TimeUtils;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/11.
 * Description:
 */
public class AgencyAffairsAdapter extends RecyclerView.Adapter<AgencyAffairsAdapter.AgencyAffairsViewHolder>{

    private Context context;
    private List<AgencyAffairsBean.DataBean.AgentList> mList;

    public AgencyAffairsAdapter(Context context, List<AgencyAffairsBean.DataBean.AgentList> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public AgencyAffairsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_agency_affairs,parent,false);
        AgencyAffairsViewHolder viewHolder = new AgencyAffairsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AgencyAffairsViewHolder holder, int position) {
        AgencyAffairsBean.DataBean.AgentList agentList = mList.get(position);
        Resources resource = context.getResources();
        ColorStateList csl1 = resource.getColorStateList(R.color.label_color);
        ColorStateList csl2 = resource.getColorStateList(R.color.unread_color);
        if (agentList.getType() == 1){
            holder.mTime.setText("完成时间：" + TimeUtils.transferLongToDate(agentList.getEndTime()));
        }else {
            holder.mTime.setText("待办时间：" + TimeUtils.transferLongToDate(agentList.getEndTime()));
        }
        holder.mContent.setText(agentList.getContent());
        if (agentList.getIsFinish() == 1){
            holder.mTime.setTextColor(csl1);
            holder.mContent.setTextColor(csl1);
        }else {
            holder.mTime.setTextColor(csl2);
            holder.mContent.setTextColor(csl2);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class AgencyAffairsViewHolder extends RecyclerView.ViewHolder{
        TextView mTime;
        TextView mContent;
        public AgencyAffairsViewHolder(View itemView) {
            super(itemView);

            mTime = itemView.findViewById(R.id.tv_finish_time);
            mContent = itemView.findViewById(R.id.tv_affairs_content);
        }
    }
}
