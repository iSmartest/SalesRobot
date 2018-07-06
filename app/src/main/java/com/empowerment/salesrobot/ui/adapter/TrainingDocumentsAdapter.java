package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.TrainingDocBean;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class TrainingDocumentsAdapter extends RecyclerView.Adapter<TrainingDocumentsAdapter.TrainingDocumentsAdapterViewHolder>{
    private Context context;
    private List<TrainingDocBean.DataBean.TdoctList> mList;
    public TrainingDocumentsAdapter(Context context, List<TrainingDocBean.DataBean.TdoctList> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public TrainingDocumentsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_training_doc, parent, false);
        TrainingDocumentsAdapterViewHolder viewHolder = new TrainingDocumentsAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingDocumentsAdapterViewHolder viewHolder, int position) {
        TrainingDocBean.DataBean.TdoctList tdoctList = mList.get(position);
        viewHolder.tv_train_word.setText(tdoctList.getName());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class TrainingDocumentsAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tv_train_word;
        public TrainingDocumentsAdapterViewHolder(View itemView) {
            super(itemView);
            tv_train_word = itemView.findViewById(R.id.tv_train_word);
        }
    }
}
