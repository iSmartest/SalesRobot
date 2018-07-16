package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.dialog.ImageAndTextDialog;
import com.empowerment.salesrobot.ui.model.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/13.
 * Description:
 */
public class ImageAndTextAdapter extends RecyclerView.Adapter<ImageAndTextAdapter.ImageAndTextViewHolder>{
    private int maxImgCount;
    private Context mContext;
    private List<ImageBean> mData;
    private OnRecyclerViewItemClickListener listener;
    public OnRemoveImageClickListener removeListener;
    private boolean isAdded;   //是否额外添加了最后一个图片

    public ImageAndTextAdapter(Context mContext, List<ImageBean> data, int maxImgCount) {
        this.mContext = mContext;
        this.maxImgCount = maxImgCount;
        setImages(data);
    }

    public interface OnRemoveImageClickListener {
        void onRemoveBinnerClick(int position);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnRemoveClickListener(OnRemoveImageClickListener removeListener) {
        this.removeListener = removeListener;
    }

    public void setImages(List<ImageBean> data) {
        mData = new ArrayList<>(data);
        if (getItemCount() < maxImgCount) {
            mData.add(new ImageBean());
            isAdded = true;
        } else {
            isAdded = false;
        }
        notifyDataSetChanged();
    }

    @Override
    public ImageAndTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_image, parent, false);
        ImageAndTextViewHolder viewHolder = new ImageAndTextViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    @Override
    public void onBindViewHolder(ImageAndTextViewHolder holder, int position) {
        ImageBean item = mData.get(position);
        final int clickPosition;
        if (isAdded && position == getItemCount() - 1) {
            holder.iv_img.setImageResource(R.drawable.selector_image_add);
            clickPosition = ImageAndTextDialog.IMAGE_BINER_ITEM_ADD;
            holder.iv_del.setVisibility(View.GONE);
        } else {
            clickPosition = position;
            Glide.with(mContext).load(item.getImage()).into(holder.iv_img);
            holder.iv_del.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(v, clickPosition);
            }
        });
        holder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeListener.onRemoveBinnerClick(clickPosition);
            }
        });
    }

    public class ImageAndTextViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_img,iv_del;
        public ImageAndTextViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            iv_del = itemView.findViewById(R.id.iv_delete);
        }
    }
}
