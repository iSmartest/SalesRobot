package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.example.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/4.
 * Description:
 */
public class FieldRecordActivity extends BaseActivity {

    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.record_recycler)
    XRecyclerView xRecyclerView;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_field_record;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        title.setText("现场记录");
        titleBack.setVisibility(View.VISIBLE);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//        mAdapter = new LifeAdapter(context, mList);
//        xRecyclerView.setAdapter(mAdapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                nowPage = 1;
//                mList.clear();
//                getLifeData();
//                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }
            @Override
            public void onLoadMore() {
//                nowPage++;
//                getLifeData();
//                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }
        });

        xRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(xRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
//                int position = vh.getAdapterPosition() - 1;
//                if (position < 0 | position >= mList.size()){
//                    return;
//                }
//                Bundle bundle = new Bundle();
//                bundle.putString(KnowLedgeWebActivity.TITLE,mList.get(position).getLifeTitle());
//                bundle.putString(KnowLedgeWebActivity.URL,mList.get(position).getLifeUrl());
//                MyApplication.openActivity(context,KnowLedgeWebActivity.class,bundle);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.title_Img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
        }
    }
}
