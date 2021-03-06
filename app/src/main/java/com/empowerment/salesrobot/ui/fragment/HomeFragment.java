package com.empowerment.salesrobot.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.activity.AgencyAffairsActivity;
import com.empowerment.salesrobot.ui.activity.CustomerInfoActivity;
import com.empowerment.salesrobot.ui.activity.NewPointActivity;
import com.empowerment.salesrobot.ui.activity.ProductSalesActivity;
import com.empowerment.salesrobot.ui.adapter.HomeBannerAdapter;
import com.empowerment.salesrobot.ui.base.BaseFragment;
import com.empowerment.salesrobot.ui.model.HomeModel;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.uitls.Utils;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;
import static com.empowerment.salesrobot.config.Url.HTTP;


/**
 * Name 赋睿智能
 * Date 2018/5/2
 * Time 16:36
 * home页面
 */

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    @BindView(R.id.title_Layout)
    RelativeLayout titleLayout;
    @BindView(R.id.tv_home_notice)
    TextView mNotice;
    @BindView(R.id.mBanner)
    Banner mBanner;
    @BindView(R.id.ll_home_item0)
    LinearLayout mLayout0;
    @BindView(R.id.ll_home_item1)
    LinearLayout mLayout1;
    @BindView(R.id.ll_home_item2)
    LinearLayout mLayout2;
    @BindView(R.id.ll_home_item3)
    LinearLayout mLayout3;
    @BindView(R.id.ll_home_item4)
    LinearLayout mLayout4;
    @BindView(R.id.ll_home_item5)
    LinearLayout mLayout5;
    @BindView(R.id.tv_red_count)
    TextView mRedCount;
    private View view;
    Unbinder unbinder;
    private String notice;
    private int companyNoticeCount;
    private List<String> imgList = new ArrayList<>();
    private List<HomeModel.DataBean.ImageList> homeList = new ArrayList<>();
    private CallBackListener callBackListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBackListener = (HomeFragment.CallBackListener) getActivity();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.empowerment.salesrobot.home");
        Objects.requireNonNull(getActivity()).registerReceiver(mAllBroad, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        loadData();
        initView();
        return view;
    }

    protected void initView() {
        titleLayout.setBackgroundColor(context.getResources().getColor(R.color.colorTransParent));
    }

    @OnClick({R.id.ll_home_item0, R.id.ll_home_item1, R.id.ll_home_item2, R.id.ll_home_item3, R.id.ll_home_item4, R.id.ll_home_item5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home_item0://待办事宜
                MyApplication.openActivity(context, AgencyAffairsActivity.class);
                break;
            case R.id.ll_home_item1://客户资料
                MyApplication.openActivity(context, CustomerInfoActivity.class);
                break;
            case R.id.ll_home_item2: //我的机器人
                callBackListener.onClickListener(1);
                break;
            case R.id.ll_home_item3://新品卖点
                MyApplication.openActivity(context, NewPointActivity.class);
                break;
            case R.id.ll_home_item4://产品销售
                MyApplication.openActivity(context, ProductSalesActivity.class);
                break;
            case R.id.ll_home_item5://接待
                callBackListener.onClickListener(2);
                break;
        }
    }

    protected void loadData() {
        Map<String, String> homeMap = new HashMap<>();
        homeMap.put(SALE_ID,SPUtil.getString(context, SALE_ID));
        homeMap.put(STORE_ID,SPUtil.getString(context, STORE_ID));
        MyOkhttp.Okhttp(context, Url.INDEX, "加载中...", homeMap, (response, result, resultNote) -> {
            Gson gson = new Gson();
            final HomeModel bean = gson.fromJson(response, HomeModel.class);
            switch (result) {
                case "0":
                    if (bean.getData().getImageList() != null && !bean.getData().getImageList().isEmpty() && bean.getData().getImageList().size() > 0) {
                        homeList.addAll(bean.getData().getImageList());
                        for (int i = 0; i <homeList.size(); i++) {
                            imgList.add(HTTP + homeList.get(i).getAddress());
                        }
                        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                                .setImageLoader(new HomeBannerAdapter())
                                .setImages(imgList)
                                .setBannerAnimation(Transformer.Default)
                                .setDelayTime(3000)
                                .isAutoPlay(true)
                                .start();
                    }
                    notice = bean.getData().getNotice();
                    companyNoticeCount = bean.getData().getCompanyNoticeCount();
                    if (companyNoticeCount == 0){
                        mRedCount.setVisibility(View.GONE);
                    }else {
                        mRedCount.setVisibility(View.VISIBLE);
                        mRedCount.setText(companyNoticeCount+"");
                    }
                    mNotice.setText(notice);
                    break;
                case "1":
                    ToastUtils.makeText(context, bean.getMsg());
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public interface CallBackListener {
        void onClickListener(int flag);
    }

    private BroadcastReceiver mAllBroad = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            //接到广播通知后刷新数据源
            homeList.clear();
            imgList.clear();
            loadData();
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            if (imgList.isEmpty()){
                if (Utils.isNetworkAvailable(Objects.requireNonNull(getActivity()))){
                    loadData();
                }else {
                    ToastUtils.makeText(getActivity(),"呀！网络跑丢了！");
                }
            }
        }
    }
}
