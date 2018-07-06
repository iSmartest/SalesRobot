package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.TextView;


import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.InfromationEntity;
import com.empowerment.salesrobot.uitls.TimeUtils;

import java.util.List;

public class InfromationAdapter extends AbsAdapter<InfromationEntity.DataBean.CustomerListBean> {

    private static final String TAG = "InfromationAdapter";

    public InfromationAdapter(Context context, int layoutId, List<InfromationEntity.DataBean.CustomerListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void bindResponse(ViewHolder holder, InfromationEntity.DataBean.CustomerListBean data) {

        ((TextView) holder.getView(R.id.infrom_item_Name)).setText(data.getName());
        ((TextView) holder.getView(R.id.infrom_item_Time)).setText(TimeUtils.transferLongToDate(data.getDate()));
        ((TextView) holder.getView(R.id.infrom_item_Context)).setText(data.getContent());
    }
}
