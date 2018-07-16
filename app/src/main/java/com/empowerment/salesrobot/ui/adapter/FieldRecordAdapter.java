package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.FieldRecordBean;
import com.empowerment.salesrobot.uitls.ImageManagerUtils;
import com.empowerment.salesrobot.view.MyGridView;
import com.empowerment.salesrobot.view.RoundedImageView;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/10.
 * Description:
 */
public class FieldRecordAdapter extends RecyclerView.Adapter<FieldRecordAdapter.FieldRecordViewHolder>{
    private Context context;
    private List<FieldRecordBean.DataBean.ConsultList> mList;
    public FieldRecordAdapter(Context context, List<FieldRecordBean.DataBean.ConsultList> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public FieldRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_field_record,parent,false);
        FieldRecordViewHolder viewHolder = new FieldRecordViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FieldRecordViewHolder holder, int position) {
        FieldRecordBean.DataBean.ConsultList consultList = mList.get(position);
        if (consultList.getType() == 1){
            holder.mName.setText("姓名：" + consultList.getName());
        }
        Glide.with(context).load(consultList.getImage()).into(holder.mIcon);
        LabelAdapter labelAdapter = new LabelAdapter(context,consultList.getItems());
        holder.mGVLabel.setAdapter(labelAdapter);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class FieldRecordViewHolder extends RecyclerView.ViewHolder{
        RoundedImageView mIcon;
        TextView mName,mTime;
        MyGridView mGVLabel;
        public FieldRecordViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.record_icon);
            mName = itemView.findViewById(R.id.tv_record_name);
            mTime = itemView.findViewById(R.id.tv_record_time);
            mGVLabel = itemView.findViewById(R.id.gv_record_label);
        }
    }
}
