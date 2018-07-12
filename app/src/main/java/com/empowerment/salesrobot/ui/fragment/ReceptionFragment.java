package com.empowerment.salesrobot.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.empowerment.salesrobot.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/10.
 * Description:
 */
public class ReceptionFragment extends BaseFragment{
    @BindView(R.id.title)
    TextView title;
    Unbinder unbinder;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_reception, container,false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }
    protected void initView() {
        title.setText("展屏编号");
//        titleImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
