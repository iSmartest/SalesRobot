package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.uitls.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/12.
 * Description:
 */
public class AgencyAffairsInfoActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.tv_affairs_info_time)
    TextView mTime;
    @BindView(R.id.tv_affairs_info_content)
    TextView mContent;
    @BindView(R.id.tv_affairs_info_finish)
    TextView mFinish;
    private String type, id;

    @Override
    protected int getLauoutId() {
        return R.layout.activity_agency_affairs_info;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        title.setText("待办事宜");
        titleBack.setVisibility(View.VISIBLE);
        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        if (getIntent().getStringExtra("isFinish").equals("1")){
            mFinish.setVisibility(View.GONE);
        }else {
            mFinish.setVisibility(View.VISIBLE);
        }
        mTime.setText(getIntent().getStringExtra("time"));
        mContent.setText(getIntent().getStringExtra("content"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.tv_affairs_info_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.tv_affairs_info_finish:
                submitFinish();
                break;
        }
    }

    private void submitFinish() {
        Map<String,String> params = new HashMap<>();
        params.put("aId",id+"");
//        params.put("sId", SPUtil.getString(context,SALE_ID));
        params.put("sId","1");
        params.put("aType",String.valueOf(type));//待办类型
        params.put("type", "2");//1为阅读，2为完结

        MyOkhttp.Okhttp(context, Url.READ_OR_FINISH, dialog, params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                if (result.equals("1")){
                    ToastUtils.makeText(context,resultNote);
                }else {
                    ToastUtils.makeText(context,"已完结");
                }
            }
        });
    }
}
