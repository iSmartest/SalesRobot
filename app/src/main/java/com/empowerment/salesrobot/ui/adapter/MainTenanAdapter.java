package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.widget.TextView;


import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.MpbListEntity;
import com.empowerment.salesrobot.uitls.TimeUtils;

import java.util.List;

public class MainTenanAdapter extends AbsAdapter<MpbListEntity.DataBean.MaintianListBean> {
    public MainTenanAdapter(Context context, int layoutId, List<MpbListEntity.DataBean.MaintianListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void bindResponse(ViewHolder holder, MpbListEntity.DataBean.MaintianListBean data) {

        ((TextView) holder.getView(R.id.main_item_Time)).setText("维修时间：\t\t" + TimeUtils.transferLongToDate(Long.parseLong(data.getDate()+"")));
        ((TextView) holder.getView(R.id.main_item_Project)).setText("维修项目：\t\t" + data.getItem());
        ((TextView) holder.getView(R.id.main_item_pir)).setText("维修价格：\t\t" + (String) data.getPrice()+ "￥");


    }
}
