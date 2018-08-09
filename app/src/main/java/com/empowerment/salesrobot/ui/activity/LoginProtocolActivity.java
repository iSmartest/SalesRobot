package com.empowerment.salesrobot.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.ui.base.BaseActivity;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/8/7.
 * Description:
 */
public class LoginProtocolActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_text)
    TextView text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_text;
    }

    @Override
    protected void loadData() {
        text.setText(readStream(getResources().openRawResource(R.raw.login)));
    }

    @Override
    protected void initView() {
        title.setText("登录协议");
        titleBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.title_Back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
        }
    }

    private String readStream(InputStream is) {
        // 资源流(GBK汉字码）变为串
        String res;
        try {
            byte[] buf = new byte[is.available()];
            is.read(buf);
            res = new String(buf, "GBK");
            //  必须将GBK码制转成Unicode
            is.close();
        } catch (Exception e) {
            res = "";
        }
        return (res);

    }
}
