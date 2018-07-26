package com.empowerment.salesrobot.ui.activity;

import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.adapter.CustomerToStoreTimesAdapter;
import com.empowerment.salesrobot.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/24.
 * Description:
 */
public class CustomerToStoreTimesActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.rc_times_list)
    RecyclerView recyclerView;
    private String mName;
    private ArrayList<Parcelable> datas;
    private CustomerToStoreTimesAdapter mAdapter;
    @Override
    protected int getLauoutId() {
        return R.layout.activity_customer_to_store_times;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        mName = getIntent().getStringExtra("mName");
        datas = getIntent().getParcelableArrayListExtra("datas");
        mTitle.setText(mName);
        mBack.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new CustomerToStoreTimesAdapter(context,datas);
        recyclerView.setAdapter(mAdapter);

    }

    @OnClick({R.id.title_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
        }
    }
}
