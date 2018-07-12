package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.empowerment.salesrobot.ui.model.AgencyAffairsBean;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AgencyAffairsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class AgencyAffairsViewHolder extends RecyclerView.ViewHolder{

        public AgencyAffairsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
