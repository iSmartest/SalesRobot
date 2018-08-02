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
import com.empowerment.salesrobot.ui.model.ProductSalesBean;
import com.empowerment.salesrobot.uitls.GlideUtils;

import java.util.List;

import static com.empowerment.salesrobot.config.Url.HTTP;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder>{

    private Context context;
    private List<ProductSalesBean.DataBean.BList> datas;

    public BrandAdapter(Context context, List<ProductSalesBean.DataBean.BList> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_brand,parent,false);
        BrandViewHolder viewHolder = new BrandViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        ProductSalesBean.DataBean.BList bList = datas.get(position);
        holder.mName.setText(bList.getName());
        GlideUtils.imageLoader(context,HTTP+bList.getPic(),holder.mPic);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    class BrandViewHolder extends RecyclerView.ViewHolder{
        ImageView mPic;
        TextView mName;
        public BrandViewHolder(View itemView) {
            super(itemView);
            mPic = itemView.findViewById(R.id.iv_brand_pic);
            mName = itemView.findViewById(R.id.tv_brand_name);

        }
    }
}

