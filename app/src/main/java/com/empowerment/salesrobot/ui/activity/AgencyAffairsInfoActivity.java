package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;

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
    private int open;
    @Override
    protected int getLayoutId() {
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
        open = getIntent().getIntExtra("open",1);
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
                if (open == 2){
                    MyApplication.openActivity(context,MainActivity.class);
                    finish();
                }else {
                    finish();
                }
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
        params.put("sId", SPUtil.getString(context,SALE_ID));
        params.put("aType",String.valueOf(type));//待办类型
        params.put(STORE_ID,SPUtil.getString(context,STORE_ID));
        params.put("type", "2");//1为阅读，2为完结
        MyOkhttp.Okhttp(context, Url.READ_OR_FINISH, "加载中...", params, (response, result, resultNote) -> {
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
            }else {
                ToastUtils.makeText(context,"已完结");
                mFinish.setBackgroundResource(R.color.gray);
            }
        });
    }
}
