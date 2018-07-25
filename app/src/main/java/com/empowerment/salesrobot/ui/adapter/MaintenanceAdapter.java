package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.MpbListEntity;
import com.empowerment.salesrobot.uitls.TimeUtils;
import java.util.List;

public class MaintenanceAdapter extends RecyclerView.Adapter<MaintenanceAdapter.MaintenanceViewHolder> {
    private Context context;
    private List<MpbListEntity.DataBean.MaintianListBean> mpList;

    public MaintenanceAdapter(Context context, List<MpbListEntity.DataBean.MaintianListBean> mpList) {
        this.context = context;
        this.mpList = mpList;
    }

    @NonNull
    @Override
    public MaintenanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.maintenan_list_item,parent,false);
        MaintenanceViewHolder viewHolder = new MaintenanceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MaintenanceViewHolder holder, int position) {
        MpbListEntity.DataBean.MaintianListBean maintianListBean = mpList.get(position);
        holder.mTime.setText("保养日期：\t\t" + TimeUtils.transferLongToDate(Long.parseLong(maintianListBean.getDate()+"")));
        holder.mProject.setText("保养项目：\t\t" + maintianListBean.getItem());
        holder.mPrice.setText("保养价格：\t\t" + maintianListBean.getPrice()+ "￥");
    }

    @Override
    public int getItemCount() {
        return mpList == null ? 0 : mpList.size();
    }


    public static class MaintenanceViewHolder extends RecyclerView.ViewHolder{
        TextView mTime,mProject,mPrice;
        public MaintenanceViewHolder(View itemView) {
            super(itemView);
            mTime = itemView.findViewById(R.id.main_item_Time);
            mProject = itemView.findViewById(R.id.main_item_Project);
            mPrice = itemView.findViewById(R.id.main_item_pir);
        }
    }
}
