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
 * Created by 2018/7/10.
 * Description:
 */
public class LabelAdapter extends BaseAdapter{
    private Context context;
    private List<String> label;
    public LabelAdapter(Context context, List<String> label) {
        this.context = context;
        this.label = label;
    }

    @Override
    public int getCount() {
        return label == null ? 0 : label.size();
    }

    @Override
    public Object getItem(int position) {
        return label.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LabelViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_label,null);
            viewHolder = new LabelViewHolder();
            viewHolder.mTabel = convertView.findViewById(R.id.tv_label);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (LabelViewHolder) convertView.getTag();
        }
        viewHolder.mTabel.setText(label.get(position));
        return convertView;
    }

    class LabelViewHolder {
        TextView mTabel;
    }
}
