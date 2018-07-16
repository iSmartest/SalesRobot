package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.GridViewBean;
import com.empowerment.salesrobot.ui.model.ProductSalesBean;

import java.util.List;

public class BrandAdapter extends AbsAdapter<ProductSalesBean.DataBean.BList> {

    private Context context;

    public BrandAdapter(Context context, int layoutId, List<ProductSalesBean.DataBean.BList> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    public void bindResponse(ViewHolder holder, ProductSalesBean.DataBean.BList data) {
        ImageView view = (ImageView) holder.getView(R.id.iv_brand_pic);
        Glide.with(context).load(data.getPic()).into(view);
        ((TextView)holder.getView(R.id.tv_brand_name)).setText(data.getName());
    }
}

