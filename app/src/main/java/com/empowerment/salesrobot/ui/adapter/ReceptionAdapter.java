package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.empowerment.salesrobot.R;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/12.
 * Description:
 */
public class ReceptionAdapter extends BaseAdapter {
    private Context context;
    private List<String> mList;
    public ReceptionAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReceptionViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_reception,null);
            viewHolder = new ReceptionViewHolder();
            viewHolder.mNumber = convertView.findViewById(R.id.tv_reception_number);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ReceptionViewHolder) convertView.getTag();
        }

        viewHolder.mNumber.setText(mList.get(position));
        return convertView;
    }

    class ReceptionViewHolder {
        TextView mNumber;
    }
}
