package com.empowerment.salesrobot.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有列表适配器都可继承的抽象适配器
 *
 * @param <T>
 */
public abstract class AbsAdapter<T> extends BaseAdapter {
    private List<T> datas;
    private Context context;
    private int layoutId;

    public AbsAdapter(Context context, int layoutId, List<T> datas) {
        super();
        this.datas = datas;
        this.context = context;
        this.layoutId = layoutId;
    }


    public void setData(List<T> list) {
        this.datas = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datas.size();
    }


    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(layoutId, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindResponse(holder, datas.get(position));
        return convertView;
    }

    public abstract void bindResponse(ViewHolder holder, T data);

    public class ViewHolder {
        private View view;
        private Map<Integer, View> cache;

        public ViewHolder(View view) {
            super();
            this.view = view;
            cache = new HashMap<Integer, View>();
        }

        public View getView(int id) {
            View itemView = cache.get(id);
            if (itemView == null) {
                itemView = view.findViewById(id);
                if (itemView != null) {
                    cache.put(id, itemView);
                }
            }
            return itemView;
        }
    }
}