package com.empowerment.salesrobot.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.model.TrainKnowledgeBean;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.Url.ROBOT_INSURANCE;
import static com.empowerment.salesrobot.config.Url.ROBOT_MAINTAIN;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/20.
 * Description:
 */
public class TrainKnowledgeActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_train_knowledge)
    TextView mKnowledge;
    private String type;
    private String url;
    @Override
    protected int getLauoutId() {
        return R.layout.activity_train_knowledge;
    }

    @Override
    protected void loadData() {
        Map<String,String> params = new HashMap<>();
        MyOkhttp.Okhttp(context, url, "加载中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            TrainKnowledgeBean trainKnowledgeBean = gson.fromJson(response,TrainKnowledgeBean.class);
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
                return;
            }
            mKnowledge.setText(trainKnowledgeBean.getData().getMaintain());
        });
    }

    @Override
    protected void initView() {
        type = getIntent().getStringExtra("type");
        mBack.setVisibility(View.VISIBLE);
        if (type.equals("0")){
            title.setText("维修保养室");
            url = ROBOT_MAINTAIN;
        }else {
            title.setText("保险理赔室");
            url = ROBOT_INSURANCE;
        }
    }

    @OnClick({R.id.title_Back})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.title_Back:
                finish();
                break;
        }
    }
}
