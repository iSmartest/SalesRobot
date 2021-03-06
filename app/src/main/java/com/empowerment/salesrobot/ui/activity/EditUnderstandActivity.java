package com.empowerment.salesrobot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.EXPERIENCE;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/5.
 * Description:
 */
public class EditUnderstandActivity extends BaseActivity {

    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_OK)
    TextView titleOK;
    @BindView(R.id.edit_content)
    EditText mContent;
    @BindView(R.id.tv_edit_time)
    TextView mTime;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_understand;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        title.setText("编辑心得");
        titleBack.setVisibility(View.VISIBLE);
        titleOK.setVisibility(View.VISIBLE);
        titleOK.setText("保存");
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        date = calendar.getTime();
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy" + "." + "MM" + "." + "dd", Locale.CHINA);
        String dateString = formatter2.format(date);
        mTime.setText(dateString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    
    @OnClick({R.id.title_Back, R.id.title_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.title_OK:
                String content = mContent.getText().toString().trim();
                if (content.isEmpty()){
                    ToastUtils.makeText(context,"编辑心得内容不能为空");
                    return;
                }
                save(content);
                break;
        }
    }

    private void save(String content) {
        Map<String,String> params = new HashMap<>();
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put(SALE_ID, SPUtil.getString(context,SALE_ID));
        params.put(EXPERIENCE, content);
        MyOkhttp.Okhttp(context, Url.ADD_EXPERLIST, "保存中...", params, (response, result, resultNote) -> {
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
                return;
            }

            Intent intent = new Intent();
            intent.setAction("com.empowerment.salesrobot.mine");
            getApplicationContext().sendBroadcast(intent);
            ToastUtils.makeText(context,resultNote);
            finish();
        });
    }
}
