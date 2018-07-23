package com.empowerment.salesrobot.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.dialog.ImageAndTextDialog;
import com.empowerment.salesrobot.dialog.StopTipsDialog;
import com.empowerment.salesrobot.okhttp.OkHttpUtils;
import com.empowerment.salesrobot.okhttp.budiler.StringCallback;
import com.empowerment.salesrobot.ui.activity.PlayVideoActivity;
import com.empowerment.salesrobot.ui.model.ImageBean;
import com.empowerment.salesrobot.ui.model.RobotResultBean;
import com.empowerment.salesrobot.ui.model.TrainRecordBean;
import com.empowerment.salesrobot.uitls.ImageManagerUtils;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.uitls.UriUtils;
import com.empowerment.salesrobot.view.PhotoView;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.iwf.photopicker.PhotoPicker;
import okhttp3.Call;
import top.zibin.luban.Luban;

import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;


/**
 * Created by 小火
 * Create time on  2017/12/15
 * My mailbox is 1403241630@qq.com
 */

public class SeePictureActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title_OK)
    TextView titleOk;
    @BindView(R.id.title_Layout)
    RelativeLayout titleLayout;
    @BindView(R.id.see_pager)
    ViewPager mPager;
    @BindView(R.id.text_total_item)
    TextView mTotalItem;
    @BindView(R.id.text_pic_info)
    TextView mPicInfo;
    @BindView(R.id.iv_play_video)
    ImageView mPlay;
    private ImagAdapter mImagAdapter;
    private String mVideoUrl;
    private String name;
    private String mVideoPic;
    private String isPicOrVideo;
    private String isLiftOrRight = "1";
    private int mQuestionId;
    private int mPosition;
    private ImageAndTextDialog imageAndTextDialog;
    private List<ImageBean> mBinnerList;
    private ArrayList<? extends RobotResultBean.DataBean.Answers.Pics> mPicList;
    public SeePictureActivity() {
    }

    @Override
    protected int getLauoutId() {
        return R.layout.see_picture_tips;
    }

    @Override
    protected void initView() {
        titleLayout.setBackgroundColor(context.getResources().getColor(R.color.black_50_transparent));
        titleOk.setVisibility(View.VISIBLE);
        titleBack.setVisibility(View.VISIBLE);
        titleOk.setText("替换");
        mPicList = getIntent().getParcelableArrayListExtra("mPicList");
        mVideoUrl = getIntent().getStringExtra("mVideoUrl");
        mVideoPic = getIntent().getStringExtra("mVideoPic");
        isPicOrVideo = getIntent().getStringExtra("isPicOrVideo");
        isLiftOrRight = getIntent().getStringExtra("isLiftOrRight");
        mQuestionId = getIntent().getIntExtra("mQuestionId",0);
        if (isLiftOrRight.equals("0")) {
            mPosition = getIntent().getIntExtra("position",0);
        }else {
            mPosition = 0;
        }
        if (isPicOrVideo.equals("0")){
            mPlay.setVisibility(View.GONE);
        }else {
            mPlay.setVisibility(View.VISIBLE);
        }
        mTotalItem.setText(1 + "/" + mPicList.size());
        mPicInfo.setText(mPicList.get(0).getDes());
        mPager.setPageMargin((int) (context.getResources().getDisplayMetrics().density * 15));
        mImagAdapter = new ImagAdapter(context,mPicList);
        mPager.setAdapter(mImagAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTotalItem.setText((position + 1)+"/" + mPicList.size());
                mPicInfo.setText(mPicList.get(position).getDes());
                mPosition = position;
            }
        });
        mPlay.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("uri",mVideoPic);
            bundle.putString("url",mVideoUrl);
            bundle.putString("mName",name);
            MyApplication.openActivity(context, PlayVideoActivity.class,bundle);
        });
    }

    @Override
    protected void loadData() {

    }
    @OnClick({R.id.title_Back,R.id.title_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.title_OK:
                mBinnerList = new ArrayList<>();
                imageAndTextDialog = new ImageAndTextDialog(context, this::submit);
                break;
        }
    }

    public class ImagAdapter extends PagerAdapter {
        private PhotoView photo_view;
        private Context mContext;
        private ArrayList<? extends RobotResultBean.DataBean.Answers.Pics> mShopImgList;

        public ImagAdapter(Context mContext, ArrayList<? extends RobotResultBean.DataBean.Answers.Pics> mShopImgList) {
            this.mContext = mContext;
            this.mShopImgList = mShopImgList;
        }

        @Override
        public int getCount() {
            return mShopImgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_see_picture, null);
            photo_view = view.findViewById(R.id.iv_see_picture);
            photo_view.enable();
            photo_view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if (isLiftOrRight.equals("0")){
                Glide.with(mContext).load(mShopImgList.get(position).getPics()).into(photo_view);
            }else {
                Glide.with(mContext).load(Url.HTTP + mShopImgList.get(position).getPics()).into(photo_view);

            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PhotoPicker.REQUEST_CODE:
                    dialog.show();
                    if (data != null) {
                        final ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                        new Thread(() -> compressWithRx(photos)).start();
                    }
                    break;
            }
        }
    }

    @SuppressLint("CheckResult")
    private void compressWithRx(final List<String> photos) {
        Flowable.just(photos)
                .observeOn(Schedulers.io())
                .map(list -> Luban.with(context).load(list).get()).observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
            mHandler.sendEmptyMessage(1);
            for (int i = 0; i < list.size(); i++) {
                ImageBean imageBean = new ImageBean();
                imageBean.setImage(list.get(i).getAbsolutePath());
                mBinnerList.add(imageBean);
            }
            imageAndTextDialog.setImageList(mBinnerList);
        });
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 提交图文答案
     *
     * @param content
     */
    private void submit(String content) {
        Map<String, String> params = new HashMap<>();
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put("sId",SPUtil.getString(context,SALE_ID));
        File fileDec = new File(mBinnerList.get(0).getImage());
        dialog.show();
        OkHttpUtils.post().url(Url.ADD_TRAIN_RECORD_ABOUT_PIC).params(params)
                .addFile("uploadFile", fileDec.getName(), fileDec)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.makeText(context, e.getMessage());
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("TAG", "onResponse: " + response);
                Gson gson = new Gson();
                dialog.dismiss();
                RobotResultBean robotResultBean = gson.fromJson(response, RobotResultBean.class);

            }
        });
    }


}

