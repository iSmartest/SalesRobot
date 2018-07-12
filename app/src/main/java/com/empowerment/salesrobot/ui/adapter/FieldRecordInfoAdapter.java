package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.FieldRecordInfoBean;
import com.empowerment.salesrobot.view.MyListView;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/11.
 * Description:
 */
public class FieldRecordInfoAdapter extends RecyclerView.Adapter<FieldRecordInfoAdapter.FieldRecordInfoViewHolder>{
    private Context context;
    private List<FieldRecordInfoBean.DataBean.ConList> mList;
    public FieldRecordInfoAdapter(Context context, List<FieldRecordInfoBean.DataBean.ConList> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public FieldRecordInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_record_info,parent,false);
        FieldRecordInfoViewHolder viewHolder = new FieldRecordInfoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FieldRecordInfoViewHolder holder, int position) {
        FieldRecordInfoBean.DataBean.ConList conList = mList.get(position);
        holder.mTime.setText(conList.getDate());
        FieldRecordContentAdapter mAdapter = new FieldRecordContentAdapter(context,conList.getConsultList());
        holder.myListView.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class FieldRecordInfoViewHolder extends RecyclerView.ViewHolder{
        TextView mTime;
        MyListView myListView;
        public FieldRecordInfoViewHolder(View itemView) {
            super(itemView);
            mTime = itemView.findViewById(R.id.tv_record_info_time);
            myListView = itemView.findViewById(R.id.content_list);
        }
    }
}
