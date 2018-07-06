package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.CarBrandBean;
import com.empowerment.salesrobot.uitls.ImageManagerUtils;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class CarBrandAdapter extends RecyclerView.Adapter<CarBrandAdapter.CarBrandViewHolder>{

    private Context context;
    private List<CarBrandBean.DataBean.CarList> mList;
    public CarBrandAdapter(Context context, List<CarBrandBean.DataBean.CarList> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public CarBrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_brand, parent, false);
        CarBrandViewHolder viewHolder = new CarBrandViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarBrandViewHolder viewHolder, int position) {
        CarBrandBean.DataBean.CarList carList = mList.get(position);
        viewHolder.mName.setText(carList.getName());
        float score = Float.valueOf(carList.getScore());
        viewHolder.mRatingBar.setRating(score);
        viewHolder.mLevel.setText("级   别： "+carList.getLevel());
        viewHolder.mStructure.setText("车身结构："+carList.getStructure());
        viewHolder.mMotor.setText("发 动 机："+carList.getMotor());
        viewHolder.mGearBox.setText("变 速 箱：" + carList.getGearBox());
        viewHolder.mPrePrice.setText(carList.getPrePrice());
        viewHolder.mGuidePrice.setText("外观指导价："+carList.getGuidePrice());
        String img = carList.getPic();
        if (img.isEmpty()){
            viewHolder.mPic.setImageResource(R.drawable.image_fail_empty);
        }else {
            ImageManagerUtils.imageLoader.displayImage(img,viewHolder.mPic,ImageManagerUtils.options3);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class CarBrandViewHolder extends RecyclerView.ViewHolder{
        TextView mName,mLevel,mStructure,mMotor,mGearBox,mPrePrice,mGuidePrice;
        RatingBar mRatingBar;
        ImageView mPic;

        public CarBrandViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.a_list_item_Name);
            mRatingBar = itemView.findViewById(R.id.rab_store_opinion_star);
            mPic = itemView.findViewById(R.id.a_List_item_Img);
            mLevel = itemView.findViewById(R.id.a_list_item_level);
            mStructure = itemView.findViewById(R.id.a_list_item_structure);
            mMotor = itemView.findViewById(R.id.a_list_item_motor);
            mGearBox = itemView.findViewById(R.id.a_list_item_gearBox);
            mPrePrice = itemView.findViewById(R.id.a_list_item_prePrice);
            mGuidePrice = itemView.findViewById(R.id.a_list_item_guidePrice);
        }
    }
}
