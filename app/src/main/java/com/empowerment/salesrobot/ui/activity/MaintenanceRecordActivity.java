package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.MainTenanAdapter;
import com.empowerment.salesrobot.ui.model.MpbListEntity;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

import static com.empowerment.salesrobot.config.BaseUrl.C_ID;
import static com.empowerment.salesrobot.config.BaseUrl.PAGE;
import static com.empowerment.salesrobot.config.BaseUrl.PAGE_SIZI;
import static com.empowerment.salesrobot.config.BaseUrl.TYPE;

public class MaintenanceRecordActivity extends BaseActivity{
    private static final String TAG = "MaintenanceRecordActivi";
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mListView)
    ListView mListView;
    @BindView(R.id.mPtrLayout)
    PtrClassicFrameLayout mPtrLayout;
    private List<MpbListEntity.DataBean.MaintianListBean> mpList;
    private Map<String, String> mainMap;
    private MainTenanAdapter mainTenanAdapter;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_maintenance_record;
    }

    @Override
    protected void loadData() {



    }

    @Override
    protected void initView() {
        titleBack.setVisibility(View.VISIBLE);
        title.setText(getIntent().getStringExtra("title"));
        mpList = new ArrayList<>();
        mainMap = new HashMap<>();
        Bundle extras = getIntent().getExtras();
        String cid = extras.getString("cid");
        mainMap.put(C_ID, cid);
        mainMap.put(PAGE, PAGE_SIZI + "");

        switch (getIntent().getStringExtra("title")) {
            case "汽车保养":
                mainMap.put(TYPE, "1");
                break;
            case "维修记录":
                mainMap.put(TYPE, "2");
                break;
            case "购车情况":
                mainMap.put(TYPE, "3");
                break;
        }
        getdata(mainMap);
        mainTenanAdapter = new MainTenanAdapter(this, R.layout.maintenan_list_item,mpList);
        mListView.setAdapter(mainTenanAdapter);


    }

    private void getdata(Map<String, String> mainMap) {
        MyOkhttp.Okhttp(context, Url.MPBLIST, dialog, mainMap, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Gson gson = new Gson();
                MpbListEntity entity = gson.fromJson(response,MpbListEntity.class);
                switch (entity.getResultCode()){
                    case 0:
                        if (entity.getMsg().equals("查询成功")){
                            mpList.addAll(entity.getData().getMaintianList());
                            mainTenanAdapter.notifyDataSetChanged();
                        }else {
                            ToastUtils.makeText(context,entity.getMsg());
                        }

                        break;
                    case 1:
                        ToastUtils.makeText(context,entity.getMsg());
                        break;
                }
            }
        });
    }

    @OnClick(R.id.title_Back)
    public void onViewClicked() {
        finish();
    }

}
