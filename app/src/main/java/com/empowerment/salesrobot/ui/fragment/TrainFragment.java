package com.empowerment.salesrobot.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.ui.activity.FieldRecordActivity;
import com.empowerment.salesrobot.ui.activity.RoBotIMActivity;
import com.empowerment.salesrobot.ui.activity.SeetingsActivity;
import com.empowerment.salesrobot.ui.activity.TrainKnowledgeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/4.
 * Description:
 */
public class TrainFragment extends BaseFragment{
    private static final String TAG = "TrainFragment";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_Img)
    ImageView titleImg;
    @BindView(R.id.fileid_Record)
    LinearLayout fileidRecord;
    @BindView(R.id.traiNing)
    LinearLayout traiNing;
    @BindView(R.id.mainTenance)
    LinearLayout mainTenance;
    @BindView(R.id.insuyance_Claims)
    LinearLayout insuyanceClaims;
    Unbinder unbinder;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_train, container,false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }
    protected void initView() {
        title.setText("训练室");
//        titleImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.title_Img, R.id.fileid_Record, R.id.traiNing, R.id.mainTenance, R.id.insuyance_Claims})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.title_Img:
                MyApplication.openActivity(context,SeetingsActivity.class);
                break;
            case R.id.fileid_Record:
                bundle.putString("im_title", "现场记录");
                MyApplication.openActivity(context,FieldRecordActivity.class);
                break;
            case R.id.traiNing:
                MyApplication.openActivity(context,RoBotIMActivity.class);
                break;
            case R.id.mainTenance:
                bundle.putString("type", "0");
                MyApplication.openActivity(context,TrainKnowledgeActivity.class,bundle);
                break;
            case R.id.insuyance_Claims:
                bundle.putString("type", "1");
                MyApplication.openActivity(context,TrainKnowledgeActivity.class,bundle);
                break;
        }
    }
}
