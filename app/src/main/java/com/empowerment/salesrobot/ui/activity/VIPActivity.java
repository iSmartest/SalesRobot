package com.empowerment.salesrobot.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.GalleryAdapter;
import com.empowerment.salesrobot.ui.model.VipOrYxEntity;
import com.empowerment.salesrobot.uitls.ImageManagerUtils;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;

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
import static com.empowerment.salesrobot.config.BaseUrl.TYPE;
import static com.empowerment.salesrobot.config.BaseUrl.TYPE_VALUE_ONE;
import static com.empowerment.salesrobot.config.BaseUrl.TYPE_VALUE_TWO;

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
    ImageView vipIcon;
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
    RecyclerView mCopyRecyclerView;
    private List<VipOrYxEntity.DataBean.CustListBean> vipList = new ArrayList<>();
    private GalleryAdapter galleryAdapter;
    private Map<String, String> vipMap = new HashMap<>();
    private int pos = 0;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void loadData() {
//        vipMap.put(SALE_ID, SPUtil.getString(context,"SALE_ID"));
        vipMap.put(SALE_ID, "1");
        vipMap.put(PAGE, PAGE_SIZI + "");
        switch (title.getText().toString()) {
            case "VIP客户":
                vipMap.put(TYPE, TYPE_VALUE_ONE);
                getdata(vipMap);
                Log.e(TAG, "loadData: ");
                break;
            case "预成交客户":
                vipMap.put(TYPE, TYPE_VALUE_TWO);
                getdata(vipMap);
                Log.e(TAG, "loadData: 1");
                break;
        }


        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mCopyRecyclerView.setLayoutManager(linearLayoutManager);
        galleryAdapter = new GalleryAdapter(context, vipList);

        mCopyRecyclerView.setAdapter(galleryAdapter);
        galleryAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                pos = position;
                vipName.setText(vipList.get(position).getName());
                vipNames.setText("姓名：" + vipList.get(position).getName());
                vipAddsees.setText("地址：" + vipList.get(position).getAddress());
                vipAge.setText("年龄：" + vipList.get(position).getAge());
                vipPhone.setText("手机：" + vipList.get(position).getPhone());
                vipWork.setText("职业：" + vipList.get(position).getWork());
                vipId.setText("身份证：" + vipList.get(position).getIdCard());
                ImageManagerUtils.imageLoader.displayImage(vipList.get(position).getPic(),vipIcon,ImageManagerUtils.options3);
                switch (vipList.get(position).getSex()) {
                    case 1:
                        vipSex.setText("性别：男");
                        break;
                    case 2:
                        vipSex.setText("性别：女");
                        break;
                }
            }
        });
//        mCopyRecyclerView.setOnItemScrollChangeListener(new CopyOfMyRecyclerView.OnItemScrollChangeListener() {
//            @Override
//            public void onChange(View view, int position) {
//
//                position= position+1;
//                Log.e(TAG, "onChange: "+position);
//
//            }
//        });
    }

    @Override
    protected void initView() {
        title.setText(getIntent().getStringExtra("titles"));
        titleBack.setVisibility(View.VISIBLE);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.colorTransParent));
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
                VipOrYxEntity.DataBean.CustListBean custListBean = new VipOrYxEntity.DataBean.CustListBean();
                if (vipList != null&& !vipList.isEmpty()&&vipList.size()>0){
                    custListBean.setAddress(vipList.get(pos).getAddress());
                    custListBean.setAge(vipList.get(pos).getAge());
                    custListBean.setId(vipList.get(pos).getId());
                    custListBean.setIdCard(vipList.get(pos).getIdCard());
                    custListBean.setName(vipList.get(pos).getName());
                    custListBean.setPhone(vipList.get(pos).getPhone());
                    custListBean.setPic(vipList.get(pos).getPic());
                    custListBean.setSex(vipList.get(pos).getSex());
                    custListBean.setWork(vipList.get(pos).getWork());
                    bundle.putString("title", title.getText().toString());
                    bundle.putString("json", new Gson().toJson(custListBean));
                    MyApplication.openActivity(context,EditActivity.class,bundle);
                }else {
                    ToastUtils.makeText(context,"当前数据为空");
                }
                break;
            case R.id.vip_Add:
                MyApplication.openActivity(context,AddVIPActivity.class);
                break;
            //汽车保养
            case R.id.tv_car_maintenance:
                if (vipList != null && !vipList.isEmpty() && vipList.size() > 0){
                    bundle.putString("title", "汽车保养");
                    bundle.putString("cid", vipList.get(pos).getId() + "");
                    MyApplication.openActivity(context,MaintenanceRecordActivity.class,bundle);
                }else {
                    ToastUtils.makeText(context,"暂无数据");
                }
                break;
            //汽车服务
            case R.id.tv_car_service:
                ToastUtils.makeText(context,"暂无权限");
                break;
            //汽车推荐
            case R.id.tv_car_recommendation:
                if (vipList != null && !vipList.isEmpty() && vipList.size() > 0){
                    bundle.putString("title", "汽车推荐");
                    bundle.putString("cid", vipList.get(pos).getId() + "");
                    MyApplication.openActivity(context,MaintenanceRecordActivity.class,bundle);
                }else {
                    ToastUtils.makeText(context,"暂无数据");
                }
                break;
            //生日提醒
            case R.id.tv_birthday_reminding:
                ToastUtils.makeText(context,"暂无权限");
                break;
            //购车情况
            case R.id.tv_car_purchase:
                if (vipList != null && !vipList.isEmpty() && vipList.size() > 0){
                    bundle.putString("title", "购车情况");
                    bundle.putString("cid", vipList.get(pos).getId() + "");
                    MyApplication.openActivity(context,MaintenanceRecordActivity.class,bundle);
                }else {
                    ToastUtils.makeText(context,"暂无数据");
                }
                break;
            //汽车保险
            case R.id.tv_car_insurance:
                ToastUtils.makeText(context,"暂无权限");
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getdata(vipMap);
    }

    public void getdata(Map<String,String> params){
        MyOkhttp.Okhttp(context, Url.VIP_YCJ, "加载中...", params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Gson gson = new Gson();
                VipOrYxEntity vipOrYxEntity = gson.fromJson(response,VipOrYxEntity.class);
                switch (vipOrYxEntity.getResultCode()) {
                    case 0:
                        List<VipOrYxEntity.DataBean.CustListBean> listBeans = vipOrYxEntity.getData().getCustList();
                        Log.e(TAG, "setRequest: " + listBeans.size());
                        vipList.addAll(listBeans);
                        galleryAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        ToastUtils.makeText(context,vipOrYxEntity.getMsg());
                        break;
                }
            }
        });
    }
}
