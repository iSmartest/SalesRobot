package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.RepairBean;
import com.empowerment.salesrobot.uitls.TimeUtils;

import java.util.List;

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.RepairViewHolder> {
    private Context context;
    private List<RepairBean.DataBean.RepairList> mpList;

    public RepairAdapter(Context context, List<RepairBean.DataBean.RepairList> mpList) {
        this.context = context;
        this.mpList = mpList;
    }

    @NonNull
    @Override
    public RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.maintenan_list_item,parent,false);
        RepairViewHolder viewHolder = new RepairViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepairViewHolder holder, int position) {
        RepairBean.DataBean.RepairList repairList = mpList.get(position);
        holder.mTime.setText("维修日期：\t\t" + TimeUtils.transferLongToDate(Long.parseLong(repairList.getDate()+"")));
        holder.mProject.setText("维修项目：\t\t" + repairList.getItem());
        holder.mPrice.setText("维修价格：\t\t" + repairList.getPrice()+ "￥");
    }

    @Override
    public int getItemCount() {
        return mpList == null ? 0 : mpList.size();
    }


    public static class RepairViewHolder extends RecyclerView.ViewHolder{
        TextView mTime,mProject,mPrice;
        public RepairViewHolder(View itemView) {
            super(itemView);
            mTime = itemView.findViewById(R.id.main_item_Time);
            mProject = itemView.findViewById(R.id.main_item_Project);
            mPrice = itemView.findViewById(R.id.main_item_pir);
        }
    }
}
