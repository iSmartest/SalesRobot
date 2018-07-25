package com.empowerment.salesrobot.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.GalleryAdapter;
import com.empowerment.salesrobot.ui.model.VipOrYxEntity;
import com.empowerment.salesrobot.uitls.GlideUtils;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.view.RoundedImageView;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.PAGE;
import static com.empowerment.salesrobot.config.BaseUrl.PAGE_SIZI;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.TYPE;

/**
 * Vip // 预成交 页面
 */
public class VIPActivity extends BaseActivity {
    private static final String TAG = "VIPActivity";
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_Layout)
    RelativeLayout titleLayout;
    @BindView(R.id.vip_icon)
    RoundedImageView vipIcon;
    @BindView(R.id.vip_Name)
    TextView vipName;
    @BindView(R.id.vip_Write)
    ImageView vipWrite;
    @BindView(R.id.vipNames)
    TextView vipNames;
    @BindView(R.id.vipSex)
    TextView vipSex;
    @BindView(R.id.vipAge)
    TextView vipAge;
    @BindView(R.id.vipAddsees)
    TextView vipAddsees;
    @BindView(R.id.vipPhone)
    TextView vipPhone;
    @BindView(R.id.vipWork)
    TextView vipWork;
    @BindView(R.id.vipId)
    TextView vipId;
    @BindView(R.id.vip_Add)
    TextView vipAdd;
    @BindView(R.id.tv_car_maintenance)
    TextView mMaintenance;
    @BindView(R.id.tv_car_service)
    TextView mService;
    @BindView(R.id.tv_car_recommendation)
    TextView mRecommendation;
    @BindView(R.id.tv_birthday_reminding)
    TextView mBirthdayReminding;
    @BindView(R.id.tv_car_purchase)
    TextView mPurchase;
    @BindView(R.id.tv_car_insurance)
    TextView mInsurance;
    @BindView(R.id.mCopyRecyclerView)
    RecyclerView recyclerView;
    private List<VipOrYxEntity.DataBean.CustListBean> mList = new ArrayList<>();
    private GalleryAdapter mAdapter;
    private int position = 0;
    private String mStyle;
    @Override
    protected int getLauoutId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        mStyle = getIntent().getStringExtra("mStyle");
        if (mStyle.equals("1")){
            title.setText("VIP客户");
        }else {
            title.setText("预成交客户");
        }
        titleBack.setVisibility(View.VISIBLE);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.colorTransParent));
        recyclerView.setLayoutManager(new LinearLayoutManager(context, OrientationHelper.HORIZONTAL,false));
        mAdapter = new GalleryAdapter(context, mList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                position = vh.getAdapterPosition();
                if (position < 0 | position >= mList.size()) {
                    return;
                }
                vipName.setText(mList.get(position).getName());
                vipNames.setText("姓名：" + mList.get(position).getName());
                vipAddsees.setText("地址：" + mList.get(position).getAddress());
                vipAge.setText("年龄：" + mList.get(position).getAge());
                vipPhone.setText("手机：" + mList.get(position).getPhone());
                vipWork.setText("职业：" + mList.get(position).getWork());
                vipId.setText("身份证：" + mList.get(position).getIdCard());
                GlideUtils.imageLoader(context,mList.get(position).getPic(),vipIcon);
                switch (mList.get(position).getSex()) {
                    case 0:
                        vipSex.setText("性别：男");
                        break;
                    case 1:
                        vipSex.setText("性别：女");
                        break;
                }
            }
        });

        getdata();
    }

    private void getdata() {
        Map<String, String> params = new HashMap<>();
        params.put(SALE_ID, SPUtil.getString(context,SALE_ID));
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put(PAGE, PAGE_SIZI + "");
        params.put(TYPE, mStyle);
        MyOkhttp.Okhttp(context, Url.VIP_YCJ, "加载中...", params, (response, result, resultNote) -> {
            Log.i("TAG", "loadData: " + response);
            Gson gson = new Gson();
            VipOrYxEntity vipOrYxEntity = gson.fromJson(response,VipOrYxEntity.class);
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
                return;
            }
            List<VipOrYxEntity.DataBean.CustListBean> listBeans = vipOrYxEntity.getData().getCustList();
            if (listBeans != null && !listBeans.isEmpty() && listBeans.size() > 0){
                mList.addAll(listBeans);
                mAdapter.notifyDataSetChanged();
                vipName.setText(mList.get(0).getName());
                vipNames.setText("姓名：" + mList.get(0).getName());
                vipAddsees.setText("地址：" + mList.get(0).getAddress());
                vipAge.setText("年龄：" + mList.get(0).getAge());
                vipPhone.setText("手机：" + mList.get(0).getPhone());
                vipWork.setText("职业：" + mList.get(0).getWork());
                vipId.setText("身份证：" + mList.get(0).getIdCard());
                GlideUtils.imageLoader(context,mList.get(0).getPic(),vipIcon);
                switch (mList.get(0).getSex()) {
                    case 1:
                        vipSex.setText("性别：男");
                        break;
                    case 2:
                        vipSex.setText("性别：女");
                        break;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.vip_Write, R.id.vip_Add,R.id.tv_car_maintenance,R.id.tv_car_service
    ,R.id.tv_car_recommendation,R.id.tv_birthday_reminding,R.id.tv_car_purchase,R.id.tv_car_insurance})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.vip_Write:
                //编辑页面
                if (mList != null&& !mList.isEmpty()&&mList.size()>0){
                    bundle.putString("mAddress",mList.get(position).getAddress());
                    bundle.putString("mAge",mList.get(position).getAge()+"");
                    bundle.putString("mId",mList.get(position).getId()+"");
                    bundle.putString("mIdCard",mList.get(position).getIdCard());
                    bundle.putString("mName",mList.get(position).getName());
                    bundle.putString("mPhone",mList.get(position).getPhone());
                    bundle.putString("mPic",mList.get(position).getPic());
                    bundle.putString("mSex",mList.get(position).getSex()+"");
                    bundle.putString("mWork",mList.get(position).getWork());
                    bundle.putString("mCount",mList.get(position).getDates().size()+"");
                    bundle.putString("mContent",mList.get(position).getContent());
                    bundle.putString("mData",mList.get(position).getDate());
                    bundle.putSerializable("datas", (Serializable) mList.get(position).getDates());
                    bundle.putString("type",mStyle);
                    MyApplication.openActivity(context,EditActivity.class,bundle);
                }else {
                    ToastUtils.makeText(context,"当前数据为空");
                }
                break;
            case R.id.vip_Add:
                bundle.putString("mStyle",mStyle);
                MyApplication.openActivity(context,AddCustomerActivity.class,bundle);
                break;
            //保养记录
            case R.id.tv_car_maintenance:
                if (mList != null && !mList.isEmpty() && mList.size() > 0){
                    bundle.putString("cid",mList.get(position).getId() + "");
                    MyApplication.openActivity(context,MaintenanceRecordActivity.class,bundle);
                }else {
                    ToastUtils.makeText(context,"暂无数据");
                }
                break;
            //维修记录
            case R.id.tv_car_service:
                if (mList != null && !mList.isEmpty() && mList.size() > 0){
                    bundle.putString("cid",mList.get(position).getId() + "");
                    MyApplication.openActivity(context,RepairRecordActivity.class,bundle);
                }else {
                    ToastUtils.makeText(context,"暂无数据");
                }
                break;
            //汽车推荐
            case R.id.tv_car_recommendation:
                ToastUtils.makeText(context,"暂未开通，敬请期待...");
                break;
            //生日提醒
            case R.id.tv_birthday_reminding:
                ToastUtils.makeText(context,"暂未开通，敬请期待...");
                break;
            //购车情况
            case R.id.tv_car_purchase:
                if (mList != null && !mList.isEmpty() && mList.size() > 0){
                    bundle.putString("cid", mList.get(position).getId() + "");
                    MyApplication.openActivity(context,BuyCarRecordActivity.class,bundle);
                }else {
                    ToastUtils.makeText(context,"暂无数据");
                }
                break;
            //汽车保险
            case R.id.tv_car_insurance:
                ToastUtils.makeText(context,"暂未开通，敬请期待...");
                break;
        }
    }
}
