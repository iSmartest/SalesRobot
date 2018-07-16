package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.GridViewBean;


import java.util.List;

public class GridViewAdapter extends AbsAdapter<GridViewBean> {

    Context context;
    public GridViewAdapter(Context context, int layoutId, List<GridViewBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    public void bindResponse(ViewHolder holder, GridViewBean data) {
        ImageView view = (ImageView) holder.getView(R.id.home_item_img);

        Glide.with(context).load(data.getImg()).into(view);
        ((TextView)holder.getView(R.id.home_item_name)).setText(data.getName());
    }
}
