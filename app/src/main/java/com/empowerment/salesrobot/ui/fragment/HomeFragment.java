package com.empowerment.salesrobot.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.activity.AgencyAffairsActivity;
import com.empowerment.salesrobot.ui.activity.CustomerInfoActivity;
import com.empowerment.salesrobot.ui.activity.NewPointActivity;
import com.empowerment.salesrobot.ui.activity.ProductSalesActivity;
import com.empowerment.salesrobot.ui.adapter.AbsAdapter;
import com.empowerment.salesrobot.ui.adapter.GridViewAdapter;
import com.empowerment.salesrobot.ui.adapter.HomeBannerAdapter;
import com.empowerment.salesrobot.ui.model.GridViewBean;
import com.empowerment.salesrobot.ui.model.HomeEntity;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.empowerment.salesrobot.config.BaseUrl.NAMES;
import static com.empowerment.salesrobot.config.BaseUrl.IMGS;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.Url.HTTP;


/**
 * Name 赋睿智能
 * Date 2018/5/2
 * Time 16:36
 * home页面
 */

public class HomeFragment extends BaseFragment implements OnBannerClickListener {

    private static final String TAG = "HomeFragment";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_Layout)
    RelativeLayout titleLayout;
    @BindView(R.id.homeListView)
    ListView homeListView;
    @BindView(R.id.mBanner)
    Banner mBanner;
    @BindView(R.id.homeGridView)
    GridView homeGridView;
    private View view;
    Unbinder unbinder;

    private Map<String, String> homeMap = new HashMap<>();
    private List<String> titleList = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();
    private List<HomeEntity.DataBean.ImageListBean> homeList = new ArrayList<>();
    private List<HomeEntity.DataBean.NoticeListBean> mmList = new ArrayList<>();
    private List<GridViewBean> gridViewBeanList = new ArrayList<>();
    private GridViewAdapter gridViewAdapter;
    private CallBackListener callBackListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBackListener = (HomeFragment.CallBackListener) getActivity();
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
        title.setText(R.string.app_home);
        titleLayout.setBackgroundColor(context.getResources().getColor(R.color.colorTransParent));
        mBanner.setOnBannerClickListener(this);
        for (int i = 0; i < IMGS.length; i++) {
            GridViewBean bean = new GridViewBean();
            bean.setImg(IMGS[i]);
            bean.setName(NAMES[i]);
            gridViewBeanList.add(bean);
        }
        gridViewAdapter = new GridViewAdapter(context, R.layout.home_gridview_item_list, gridViewBeanList);
        homeGridView.setAdapter(gridViewAdapter);
        homeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //待办事宜
                        MyApplication.openActivity(context, AgencyAffairsActivity.class);
                        break;
                    case 1://客户资料
                        MyApplication.openActivity(context, CustomerInfoActivity.class);
                        break;
                    case 2: //我的机器人
                        callBackListener.onClickListener(1);
                        break;
                    case 3://新品买点
                        MyApplication.openActivity(context, NewPointActivity.class);
                        break;
                    case 4://产品销售
                        MyApplication.openActivity(context, ProductSalesActivity.class);
                        break;
                    case 5: //接待
                        callBackListener.onClickListener(2);
                        break;
                }
            }
        });

    }

    protected void loadData() {
//        homeMap.put(SALE_ID, SPUtil.getString(context, SALE_ID));
        homeMap.put(SALE_ID, "1");
        MyOkhttp.Okhttp(context, Url.INDEX, dialog, homeMap, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Gson gson = new Gson();
                final HomeEntity bean = gson.fromJson(response, HomeEntity.class);
                switch (result) {
                    case "0":
                        if (bean.getData().getImageList() != null && !bean.getData().getImageList().isEmpty() && bean.getData().getImageList().size() > 0) {
                            homeList.addAll(bean.getData().getImageList());
                            for (int i = 0; i < bean.getData().getImageList().size(); i++) {
                                titleList.add(bean.getData().getImageList().get(i).getName());
                                imgList.add(HTTP + bean.getData().getImageList().get(i).getAddress());
                            }
                            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                                    .setImageLoader(new HomeBannerAdapter())
                                    .setImages(imgList)
                                    .setBannerAnimation(Transformer.Default)
                                    .setBannerTitles(titleList)
                                    .setDelayTime(3000)
                                    .isAutoPlay(true)
                                    .setIndicatorGravity(BannerConfig.CENTER)
                                    .start();
                        }

                        if (bean.getData().getNoticeList() != null && !bean.getData().getNoticeList().isEmpty() && bean.getData().getNoticeList().size() > 0) {
                            mmList.addAll(bean.getData().getNoticeList());
                            homeListView.setAdapter(new AbsAdapter<HomeEntity.DataBean.NoticeListBean>(context, R.layout.home_list_item, mmList) {
                                @Override
                                public void bindResponse(ViewHolder holder, HomeEntity.DataBean.NoticeListBean data) {
                                    ((TextView) holder.getView(R.id.home_item_tv)).setText(data.getContent());
                                    Glide.with(getActivity()).load(HTTP + bean.getData().getImage()).into((ImageView) holder.getView(R.id.home_ImgView));
                                    ((TextView) holder.getView(R.id.home_tvName)).setText(bean.getData().getName());
                                }
                            });
                        }
                        break;
                    case "1":
                        ToastUtils.makeText(context, bean.getMsg());
                        break;
                }
            }

        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void OnBannerClick(int position) {
        ToastUtils.makeText(context, "这是第" + homeList.get(position) + "条....");
    }


    public interface CallBackListener {
        void onClickListener(int flag);
    }
}
