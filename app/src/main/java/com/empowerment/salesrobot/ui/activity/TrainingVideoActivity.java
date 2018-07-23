package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.TrainingVideoAdapter;
import com.empowerment.salesrobot.ui.model.TrainingVideoBean;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.example.xrecyclerview.XRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class TrainingVideoActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.video_recycle)
    XRecyclerView xRecyclerView;
    private int nowPage = 1;
    private TrainingVideoAdapter mAdapter;
    private List<TrainingVideoBean.DataBean.VideoList> mList = new ArrayList<>();
    @Override
    protected int getLauoutId() {
        return R.layout.activity_train_video;
    }

    @Override
    protected void loadData() {
        mList.clear();
        mAdapter.notifyDataSetChanged();
        Map<String, String> params = new HashMap<>();
        params.put(SALE_ID, SPUtil.getString(context,SALE_ID));
        params.put("page", nowPage + "");
        MyOkhttp.Okhttp(context, Url.TRAINVIDEOLIST, "加载中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            TrainingVideoBean trainingVideoBean = gson.fromJson(response, TrainingVideoBean.class);
            if (result.equals("1")) {
                ToastUtils.makeText(context, resultNote);
                return;
            }
            List<TrainingVideoBean.DataBean.VideoList> videoList = trainingVideoBean.getData().getVideoList();
            if (videoList != null && !videoList.isEmpty() && videoList.size() > 0) {
                mList.addAll(videoList);
                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
                if (3 < nowPage) {
                    ToastUtils.makeText(context, "没有更多了");
                    xRecyclerView.noMoreLoading();
                }
            }
        });
    }

    @Override
    protected void initView() {
        title.setText("培训视频");
        titleBack.setVisibility(View.VISIBLE);
        xRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        mAdapter = new TrainingVideoAdapter(context, mList);
        xRecyclerView.setAdapter(mAdapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                nowPage = 1;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                loadData();
            }

            @Override
            public void onLoadMore() {
//                nowPage++;
//                loadData();
                xRecyclerView.noMoreLoading();
            }
        });
        xRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(xRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition() - 1;
                if (position < 0 | position >= mList.size()) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("url", mList.get(position).getAddress());
                bundle.putString("uri", mList.get(position).getCoverAddress());
                bundle.putString("mName", mList.get(position).getName());
                MyApplication.openActivity(context, PlayVideoActivity.class, bundle);
            }
        });
    }
    @OnClick({R.id.title_Back})
    public void onViewClicked() {
        finish();
    }
}
