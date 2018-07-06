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
import com.empowerment.salesrobot.ui.adapter.TrainingDocumentsAdapter;
import com.empowerment.salesrobot.ui.model.TrainingDocBean;
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
public class TrainingDocumentsActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycler_doc)
    XRecyclerView xRecyclerView;
    private int nowPage = 1;
    private TrainingDocumentsAdapter mAdapter;
    private List<TrainingDocBean.DataBean.TdoctList> mList = new ArrayList<>();

    @Override
    protected int getLauoutId() {
        return R.layout.activity_training_doc;
    }

    @Override
    protected void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put(SALE_ID, "1");
        params.put("page", nowPage + "");
        MyOkhttp.Okhttp(context, Url.TRAINVIDEOLIST, dialog, params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Gson gson = new Gson();
                TrainingDocBean trainingDocBean = gson.fromJson(response, TrainingDocBean.class);
                if (result.equals("1")) {
                    ToastUtils.makeText(context, resultNote);
                    return;
                }
                List<TrainingDocBean.DataBean.TdoctList> tdoctLists = trainingDocBean.getData().getTdoctList();
                if (tdoctLists != null && !tdoctLists.isEmpty() && tdoctLists.size() > 0) {
                    mList.addAll(tdoctLists);
                    mAdapter.notifyDataSetChanged();
                    xRecyclerView.refreshComplete();
                    if (3 < nowPage) {
                        ToastUtils.makeText(context, "没有更多了");
                        xRecyclerView.noMoreLoading();
                    }
                }
            }
        });
    }

    @Override
    protected void initView() {
        title.setText("培训文档");
        titleBack.setVisibility(View.VISIBLE);
        xRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        mAdapter = new TrainingDocumentsAdapter(context, mList);
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
                nowPage++;
                loadData();
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
                bundle.putString("mAddress", mList.get(position).getAddress());
//                MyApplication.openActivity(context, ShopDecActivity.class, bundle);
            }
        });
    }

    @OnClick({R.id.title_Back})
    public void onViewClicked() {
        finish();
    }
}
