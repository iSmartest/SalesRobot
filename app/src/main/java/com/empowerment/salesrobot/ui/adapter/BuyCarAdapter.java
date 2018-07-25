package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.BuyCarRecordBean;
import com.empowerment.salesrobot.uitls.TimeUtils;

import java.util.List;

public class BuyCarAdapter extends RecyclerView.Adapter<BuyCarAdapter.BuyCarViewHolder> {
    private Context context;
    private List<BuyCarRecordBean.DataBean.CarBuyList> mpList;

    public BuyCarAdapter(Context context, List<BuyCarRecordBean.DataBean.CarBuyList> mpList) {
        this.context = context;
        this.mpList = mpList;
    }

    @NonNull
    @Override
    public BuyCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.maintenan_list_item,parent,false);
        BuyCarViewHolder viewHolder = new BuyCarViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BuyCarViewHolder holder, int position) {
        BuyCarRecordBean.DataBean.CarBuyList carBuyList = mpList.get(position);
        holder.mTime.setText("购车日期：\t\t" + TimeUtils.transferLongToDate(Long.parseLong(carBuyList.getDate()+"")));
        holder.mProject.setText("车\t\t\t\t\t\t\t\t型：\t\t" + carBuyList.getCarType());
        holder.mPrice.setText("二次购车：\t\t" + carBuyList.getReBuy());
    }

    @Override
    public int getItemCount() {
        return mpList == null ? 0 : mpList.size();
    }


    public static class BuyCarViewHolder extends RecyclerView.ViewHolder{
        TextView mTime,mProject,mPrice;
        public BuyCarViewHolder(View itemView) {
            super(itemView);
            mTime = itemView.findViewById(R.id.main_item_Time);
            mProject = itemView.findViewById(R.id.main_item_Project);
            mPrice = itemView.findViewById(R.id.main_item_pir);
        }
    }
}
