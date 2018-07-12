package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.model.FieldRecordInfoBean;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/11.
 * Description:
 */
public class FieldRecordContentAdapter extends BaseAdapter {
    private Context context;
    private List<FieldRecordInfoBean.DataBean.ConList.ConsultList> mList;
    public FieldRecordContentAdapter(Context context, List<FieldRecordInfoBean.DataBean.ConList.ConsultList> mList) {
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
        FieldRecordContentViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_record_content,null);
            viewHolder = new FieldRecordContentViewHolder();
            viewHolder.mQuestion = convertView.findViewById(R.id.tv_record_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (FieldRecordContentViewHolder) convertView.getTag();
        }
        FieldRecordInfoBean.DataBean.ConList.ConsultList consultList = mList.get(position);
        viewHolder.mQuestion.setText(consultList.getQuestion());
        return convertView;
    }
    class FieldRecordContentViewHolder {
        TextView mQuestion;
    }

}
