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
import com.empowerment.salesrobot.ui.model.InfromationEntity;
import com.empowerment.salesrobot.uitls.GlideUtils;
import com.empowerment.salesrobot.uitls.TimeUtils;
import com.empowerment.salesrobot.view.RoundedImageView;

import java.util.List;

import static com.empowerment.salesrobot.config.Url.HTTP;


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
        holder.mTime.setText(customerListBean.getDate());
        holder.mContext.setText(customerListBean.getContent());
        holder.mCount.setText(customerListBean.getCount()+"");
        GlideUtils.imageLoader(context, HTTP + customerListBean.getPic(), holder.mCustomerIcon);
        switch (customerListBean.getType()) {
            case 1:
                holder.mCustomerType.setImageResource(R.drawable.vip_image);
                break;
            case 2:
                holder.mCustomerType.setImageResource(R.drawable.per_icon);
                break;
            case 3:
                holder.mCustomerType.setImageResource(R.drawable.common_icon);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return infoList == null ? 0 : infoList.size();
    }

    class InformationViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mTime, mContext, mCount;
        RoundedImageView mCustomerIcon;
        ImageView mCustomerType;

        public InformationViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.infrom_item_Name);
            mTime = itemView.findViewById(R.id.infrom_item_Time);
            mContext = itemView.findViewById(R.id.infrom_item_Context);
            mCount = itemView.findViewById(R.id.tv_customer_red_count);
            mCustomerType = itemView.findViewById(R.id.iv_customer_type);
            mCustomerIcon = itemView.findViewById(R.id.ri_customer_icon);

        }
    }
}
