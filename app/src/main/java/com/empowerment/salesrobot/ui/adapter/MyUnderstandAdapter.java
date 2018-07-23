package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.activity.UnderstandInfoActivity;
import com.empowerment.salesrobot.ui.model.MyUnderstandBean;
import com.empowerment.salesrobot.uitls.TimeUtils;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.view.swipeLayout.SwipeLayout;
import com.empowerment.salesrobot.view.swipeLayout.SwipeLayoutManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/5.
 * Description:
 */
public class MyUnderstandAdapter extends RecyclerView.Adapter<MyUnderstandAdapter.MyUnderstandViewHolder>{
    private Context context;
    private ModifyCountInterface modifyCountInterface;
    private List<MyUnderstandBean.DataBean.ExperienceListBean> mList;
    public MyUnderstandAdapter(Context context, List<MyUnderstandBean.DataBean.ExperienceListBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    @NonNull
    @Override
    public MyUnderstandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listview,null);
        MyUnderstandViewHolder viewHolder = new MyUnderstandViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyUnderstandViewHolder holder, final int position) {
        final MyUnderstandBean.DataBean.ExperienceListBean experienceListBean = mList.get(position);
        holder.tv_text.setText(experienceListBean.getContent());
        holder.tv_time.setText(TimeUtils.transferLongToDate(experienceListBean.getTime()));
        holder.id_item_btn.setOnClickListener(v -> {
            SwipeLayoutManager.getInstance().closeOpenInstance();
            modifyCountInterface.childDelete(holder.getAdapterPosition(),experienceListBean.getId());
        });
        holder.swipeLayout.setOnSwipeLayoutClickListener(() -> {
            Bundle bundle = new Bundle();
            bundle.putString("mName",mList.get(position).getName());
            bundle.putString("mContent",mList.get(position).getContent());
            bundle.putString("mTime",String.valueOf(TimeUtils.transferLongToDate(mList.get(position).getTime())));
            MyApplication.openActivity(context,UnderstandInfoActivity.class,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class MyUnderstandViewHolder extends RecyclerView.ViewHolder{
        TextView tv_text;
        TextView tv_time;
        SwipeLayout swipeLayout;
        Button id_item_btn;
        public MyUnderstandViewHolder(View itemView) {
            super(itemView);
            tv_text = itemView.findViewById(R.id.tv_text);
            tv_time = itemView.findViewById(R.id.tv_time);
            swipeLayout = itemView.findViewById(R.id.swipelayout);
            id_item_btn = itemView.findViewById(R.id.id_item_btn);
        }
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 删除子item
         *
         * @param position
         */
        void childDelete(int position,String itemId);
    }
}
