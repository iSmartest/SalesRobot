package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.InfromationEntity;
import com.empowerment.salesrobot.uitls.TimeUtils;

import java.util.List;


public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.InformationViewHolder> {

    private static final String TAG = "InfromationAdapter";
    private Context context;
    private List<InfromationEntity.DataBean.CustomerListBean> infoList;

    public InformationAdapter(Context context, List<InfromationEntity.DataBean.CustomerListBean> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @NonNull
    @Override
    public InformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.informatin_listview_item_layout, parent, false);
        InformationViewHolder viewHolder = new InformationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InformationViewHolder holder, int position) {
        InfromationEntity.DataBean.CustomerListBean customerListBean = infoList.get(position);
        holder.mName.setText(customerListBean.getName());
        holder.mTime.setText(TimeUtils.transferLongToDate(customerListBean.getDate()));
        holder.mContext.setText(customerListBean.getContent());
    }

    @Override
    public int getItemCount() {
        return infoList == null ? 0 : infoList.size();
    }

    class InformationViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mTime, mContext;

        public InformationViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.infrom_item_Name);
            mTime = itemView.findViewById(R.id.infrom_item_Time);
            mContext = itemView.findViewById(R.id.infrom_item_Context);
        }
    }
}
