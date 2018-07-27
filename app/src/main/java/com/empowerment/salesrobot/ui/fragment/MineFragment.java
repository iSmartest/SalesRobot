package com.empowerment.salesrobot.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.activity.MyUnderstandActivity;
import com.empowerment.salesrobot.ui.activity.PersonalMainActivity;
import com.empowerment.salesrobot.ui.activity.UnderstandInfoActivity;
import com.empowerment.salesrobot.ui.adapter.UnderStandAdapter;
import com.empowerment.salesrobot.ui.base.BaseFragment;
import com.empowerment.salesrobot.ui.model.MineBean;
import com.empowerment.salesrobot.uitls.GlideUtils;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.TimeUtils;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.view.RoundedImageView;
import com.example.xrecyclerview.XRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.empowerment.salesrobot.config.BaseUrl.AGE;
import static com.empowerment.salesrobot.config.BaseUrl.IMAGE;
import static com.empowerment.salesrobot.config.BaseUrl.NAME;
import static com.empowerment.salesrobot.config.BaseUrl.PAGE;
import static com.empowerment.salesrobot.config.BaseUrl.PHONE_NUMBER;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.WORK;
import static com.empowerment.salesrobot.config.Url.HTTP;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/5.
 * Description:
 */
public class MineFragment extends BaseFragment {

    private static final String TAG = "MyFragment";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.my_icon_img)
    RoundedImageView myIconImgview;
    @BindView(R.id.title_Layout)
    RelativeLayout titleLayout;
    @BindView(R.id.mName)
    TextView mName;
    @BindView(R.id.my_doubel_no)
    TextView myDoubelNo;
    @BindView(R.id.my_doubel_nos)
    TextView myDoubelNos;
    @BindView(R.id.understand_recycler)
    XRecyclerView xRecyclerView;
    @BindView(R.id.mXd)
    TextView mXd;
    Unbinder unbinder;
    private int nowPage = 1;
    private int rows = 10;
    private UnderStandAdapter mAdapter;
    private List<MineBean.DataBean.ContentListBean> mList = new ArrayList<>();
    private View view;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.empowerment.salesrobot.mine");
        Objects.requireNonNull(getActivity()).registerReceiver(mAllBroad, intentFilter);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(R.layout.fragment_mine, container,false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        loadData();
        return view;
    }

    protected void initView() {
        title.setText(R.string.app_mine);
        titleLayout.setBackgroundColor(getContext().getResources().getColor(R.color.colorTransParent));
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new UnderStandAdapter(context,mList);
        xRecyclerView.setAdapter(mAdapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                nowPage = 1;
                mList.clear();
                loadData();
                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                nowPage++;
                loadData();
                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }
        });

        xRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(xRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition() - 1;
                if (position < 0 | position >= mList.size()){
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("mName",mList.get(position).getName());
                bundle.putString("mImage",mList.get(position).getImage());
                bundle.putString("mPhone",mList.get(position).getPhone());
                bundle.putString("mContent",mList.get(position).getContent());
                bundle.putString("mTime",String.valueOf(TimeUtils.transferLongToDate(mList.get(position).getData())));
                bundle.putString("type","0");
                MyApplication.openActivity(context,UnderstandInfoActivity.class,bundle);
            }
        });

    }

    protected void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put(SALE_ID, SPUtil.getString(context,SALE_ID));
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put("rows", rows+"");
        params.put(PAGE,String.valueOf(nowPage));
        MyOkhttp.Okhttp(context, Url.EXPERIENCELIST, "加载中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            MineBean mineBean = gson.fromJson(response,MineBean.class);
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
                return;
            }
            List<MineBean.DataBean.ContentListBean> experienceLists = mineBean.getData().getContentList();
            if (experienceLists != null && !experienceLists.isEmpty() && experienceLists.size() > 0){
                mList.addAll(experienceLists);
                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }
            SPUtil.putString(context,IMAGE,mineBean.getData().getSale().getImage());
            SPUtil.putString(context,AGE,mineBean.getData().getSale().getAge()+"");
            SPUtil.putString(context,NAME,mineBean.getData().getSale().getName());
            SPUtil.putString(context,WORK,mineBean.getData().getSale().getWork());
            SPUtil.putString(context,PHONE_NUMBER,mineBean.getData().getSale().getPhone());
            GlideUtils.imageLoader(context,mineBean.getData().getSale().getImage(),myIconImgview);
            mName.setText(mineBean.getData().getSale().getName());
            myDoubelNo.setText(mineBean.getData().getSale().getSaleIndex());
            myDoubelNos.setText(mineBean.getData().getSale().getSuccessIndex());
            if (experienceLists.size() < rows){
                ToastUtils.makeText(context,"没有更多了");
                xRecyclerView.noMoreLoading();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_icon_img, R.id.mXd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mXd:
                MyApplication.openActivity(context,MyUnderstandActivity.class);
                break;
            case R.id.my_icon_img:
                MyApplication.openActivity(context,PersonalMainActivity.class);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this).load(HTTP + SPUtil.getString(context,IMAGE)).into(myIconImgview);
    }
    private BroadcastReceiver mAllBroad = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            //接到广播通知后刷新数据源
            nowPage = 1;
            mList.clear();
            mAdapter.notifyDataSetChanged();
            loadData();
        }
    };
}
