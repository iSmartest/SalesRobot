package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.empowerment.salesrobot.R;

import java.util.ArrayList;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/24.
 * Description:
 */
public class CustomerToStoreTimesAdapter extends RecyclerView.Adapter<CustomerToStoreTimesAdapter.CustomerToStoreTimesViewHolder>{

    private Context context;
    private ArrayList<Parcelable> datas;

    public CustomerToStoreTimesAdapter(Context context, ArrayList<Parcelable> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public CustomerToStoreTimesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer_times,parent,false);
        CustomerToStoreTimesViewHolder viewHolder = new CustomerToStoreTimesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerToStoreTimesViewHolder holder, int position) {
        holder.mTime.setText((CharSequence) datas.get(position) + "来店");
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class CustomerToStoreTimesViewHolder extends RecyclerView.ViewHolder{
        TextView mTime;
        public CustomerToStoreTimesViewHolder(View itemView) {
            super(itemView);

            mTime = itemView.findViewById(R.id.tv_times_data);
        }
    }
}
