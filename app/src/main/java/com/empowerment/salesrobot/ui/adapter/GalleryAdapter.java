package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.VipOrYxBean;
import com.empowerment.salesrobot.uitls.GlideUtils;
import com.empowerment.salesrobot.view.RoundedImageView;


import java.util.List;

import static com.empowerment.salesrobot.config.Url.HTTP;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private LayoutInflater mInflater;
    private List<VipOrYxBean.DataBean.CustListBean> mList;
    private Context context;

    public GalleryAdapter(Context context, List<VipOrYxBean.DataBean.CustListBean> mList) {
        mInflater = LayoutInflater.from(context);
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.galler_item, viewGroup, false);
        GalleryViewHolder viewHolder = new GalleryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GalleryViewHolder viewHolder, final int position) {
        VipOrYxBean.DataBean.CustListBean custListBean = mList.get(position);
        GlideUtils.imageLoader(context,HTTP+custListBean.getPic(),viewHolder.mImg);
        viewHolder.mTxt.setText(custListBean.getName());
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mImg;
        TextView mTxt;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.id_index_gallery_item_image);
            mTxt = itemView.findViewById(R.id.id_index_gallery_item_text);
        }
    }
}
