package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.ui.model.InfromationEntity;
import com.empowerment.salesrobot.ui.model.NewPointBean;
import com.empowerment.salesrobot.uitls.ImageManagerUtils;
import com.empowerment.salesrobot.uitls.TimeUtils;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class NewPointAdapter extends RecyclerView.Adapter<NewPointAdapter.NewPointViewHolder> {
    private Context context;
    private List<NewPointBean.DataBean.CList> mList;

    public NewPointAdapter(Context context, List<NewPointBean.DataBean.CList> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public NewPointViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_point_item, parent, false);
        NewPointViewHolder viewHolder = new NewPointViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewPointViewHolder viewHolder, int position) {
        NewPointBean.DataBean.CList mCList = mList.get(position);
        String img = Url.HTTP + mCList.getPic();
        if (TextUtils.isEmpty(img)) {
            viewHolder.mPicture.setImageResource(R.drawable.image_fail_empty);
        } else {
            ImageManagerUtils.imageLoader.displayImage(img, viewHolder.mPicture, ImageManagerUtils.options3);
        }
        viewHolder.mIdea.setText(mCList.getIdea());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class NewPointViewHolder extends RecyclerView.ViewHolder {
        TextView mIdea;
        ImageView mPicture;

        public NewPointViewHolder(View itemView) {
            super(itemView);
            mIdea = itemView.findViewById(R.id.tv_point_idea);
            mPicture = itemView.findViewById(R.id.iv_point_pic);
        }
    }
}