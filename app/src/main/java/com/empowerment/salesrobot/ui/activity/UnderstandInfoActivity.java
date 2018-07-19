package com.empowerment.salesrobot.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.uitls.GlideUtils;
import com.empowerment.salesrobot.view.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/6.
 * Description:
 */
public class UnderstandInfoActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.iv_other_sale_icon)
    RoundedImageView mIcon;
    @BindView(R.id.tv_other_sale_name)
    TextView tvName;
    @BindView(R.id.tv_other_sale_phone)
    TextView tvPhone;
    private String mName,mImage,mPhone;
    private String mContent;
    private String mTime;
    @Override
    protected int getLauoutId() {
        return R.layout.activity_understand_info;
    }

    @Override
    protected void loadData() {
        tvContent.setText(mContent);
        tvData.setText(mTime);
        GlideUtils.imageLoader(context, Url.HTTP + mImage,mIcon);
        tvName.setText(mName);
        tvPhone.setText(mPhone);
    }

    @Override
    protected void initView() {
        mName = getIntent().getStringExtra("mName");
        mImage = getIntent().getStringExtra("mImage");
        mPhone = getIntent().getStringExtra("mPhone");
        mContent = getIntent().getStringExtra("mContent");
        mTime = getIntent().getStringExtra("mTime");
        title.setText("销售心得");
        titleBack.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
        }
    }
}
