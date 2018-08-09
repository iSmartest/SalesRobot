package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.NewPointAdapter;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.ui.model.NewPointBean;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.example.xrecyclerview.XRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.PAGE;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;

/**
 * 新品买点
 */
public class NewPointActivity extends BaseActivity {

    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rc_new_point)
    XRecyclerView mRecyclerView;
    private NewPointAdapter mAdapter;
    private List<NewPointBean.DataBean.CList> mList = new ArrayList<>();
    private int nowPage = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_point;
    }

    @Override
    protected void loadData() {
        mAdapter.notifyDataSetChanged();
        Map<String,String> params = new HashMap<>();
        params.put("groupid", SPUtil.getString(context,SALE_ID));
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put(PAGE, nowPage +"");
        MyOkhttp.Okhttp(context, Url.BUYPOINT, "加载中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            NewPointBean newPointBean = gson.fromJson(response,NewPointBean.class);
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
                return;
            }
            List<NewPointBean.DataBean.CList> cLists = newPointBean.getData().getcList();
            if (cLists != null && !cLists.isEmpty() && cLists.size() > 0){
                mList.addAll(cLists);
                mAdapter.notifyDataSetChanged();
            }
            if (cLists.size() < 10){
                ToastUtils.makeText(context,"没有更多了");
                mRecyclerView.noMoreLoading();
            }
            mRecyclerView.refreshComplete();
        });
    }

    @Override
    protected void initView() {
        title.setText("新品卖点");
        titleBack.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new NewPointAdapter(context,mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                nowPage = 1;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                loadData();
            }

            @Override
            public void onLoadMore() {
                nowPage ++;
                loadData();
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition()-1;
                if (position < 0 | position >= mList.size()){
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("carId",mList.get(position).getId());
                MyApplication.openActivity(context,NewPointInfoActivity.class,bundle);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.title_Back)
    public void onViewClicked() {
        finish();
    }
}
