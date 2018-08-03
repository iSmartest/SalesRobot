package com.empowerment.salesrobot.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.adapter.ReceptionAdapter;
import com.empowerment.salesrobot.ui.base.BaseFragment;
import com.empowerment.salesrobot.uitls.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/10.
 * Description:
 */
public class ReceptionFragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.gv_reception)
    GridView gv_reception;
    Unbinder unbinder;
    private View view;
    private ReceptionAdapter mAdapter;
    private List<String> mList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_reception, container,false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        loadData();
        return view;
    }

    private void loadData() {
        for (int i = 0; i <9 ; i++) {
            String string = i+"";
            mList.add(string);
        }
        mAdapter = new ReceptionAdapter(context,mList);
        gv_reception.setAdapter(mAdapter);
    }

    protected void initView() {
        title.setText("机器人编号");
        gv_reception.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.makeText(context,"你接待的是" + mList.get(position)+"号展屏");
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
