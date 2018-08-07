package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.dialog.SeePictureDialog;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.NewPointInfoAdapter;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.ui.model.NewPointInfoBean;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.Url.HTTP;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class NewPointInfoActivity extends BaseActivity {

    private String id;
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_OK)
    TextView title_OK;
    @BindView(R.id.new_point_list)
    RecyclerView recyclerView;
    private NewPointInfoAdapter mAdapter;
    private List<NewPointInfoBean.DataBean.BuyPointDetail> mList = new ArrayList<>();
    private String policy;
    private SeePictureDialog seePictureDialog;
    @Override
    protected int getLauoutId() {
        return R.layout.activity_new_point_info;
    }

    @Override
    protected void loadData() {
        mList.clear();
        mAdapter.notifyDataSetChanged();
        Map<String, String> params = new HashMap<>();
        params.put("carId", id);
        MyOkhttp.Okhttp(context, Url.BUYPOINTDETAIL, "加载中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            NewPointInfoBean newPointInfoBean = gson.fromJson(response, NewPointInfoBean.class);
            if (result.equals("1")) {
                ToastUtils.makeText(context, resultNote);
                return;
            }
            List<NewPointInfoBean.DataBean.BuyPointDetail> buyPointDetails = newPointInfoBean.getData().getBuyPointDetail();
            if (buyPointDetails != null && !buyPointDetails.isEmpty() && buyPointDetails.size() > 0){
                mList.addAll(buyPointDetails);
                mAdapter.notifyDataSetChanged();
            }
            policy = newPointInfoBean.getData().getPolicy();
        });
    }

    @Override
    protected void initView() {
        title.setText("新品卖点");
        titleBack.setVisibility(View.VISIBLE);
        title_OK.setVisibility(View.VISIBLE);
        title_OK.setText("政策");
        id = getIntent().getStringExtra("carId");
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new NewPointInfoAdapter(context,mList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition();
                if (position < 0 | position >= mList.size()){
                    return;
                }
                Bundle bundle = new Bundle();
                if (mList.get(position).getType() == 1){//图文+视频
                    seePictureDialog = new SeePictureDialog(context,mList.get(position).getSellPointdsc(),mList.get(position).getSellPointvid(),mList.get(position).getSellPoint());
                    seePictureDialog.show();
                }else if (mList.get(position).getType() == 2){//图文
                    seePictureDialog = new SeePictureDialog(context,mList.get(position).getSellPointdsc());
                    seePictureDialog.show();
                }else {//视频
                    bundle.putString("mName","");
                    bundle.putString("url",HTTP+mList.get(position).getSellPointvid());
                    bundle.putString("uri","");
                    MyApplication.openActivity(context,PlayVideoActivity.class,bundle);
                }


            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.title_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.title_OK:
                Bundle bundle = new Bundle();
                bundle.putString("policy",policy);
                MyApplication.openActivity(context,NoticeActivity.class,bundle);
                break;
        }

    }
}
