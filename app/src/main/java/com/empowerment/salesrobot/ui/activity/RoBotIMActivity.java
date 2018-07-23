package com.empowerment.salesrobot.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.dialog.ImageAndTextDialog;
import com.empowerment.salesrobot.dialog.StopTipsDialog;
import com.empowerment.salesrobot.listener.SoftKeyBoardListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.okhttp.OkHttpUtils;
import com.empowerment.salesrobot.okhttp.budiler.StringCallback;
import com.empowerment.salesrobot.ui.adapter.RoBotIMAdapter;
import com.empowerment.salesrobot.ui.model.ImageBean;
import com.empowerment.salesrobot.ui.model.RobotResultBean;
import com.empowerment.salesrobot.ui.model.TrainRecordBean;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.uitls.UriUtils;
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
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/4.
 * Description:
 */
public class RoBotIMActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rl_item)
    RelativeLayout relativeLayout;
    @BindView(R.id.ll_item)
    LinearLayout linearLayout;
    @BindView(R.id.iv_keyboard)
    ImageView mKeyBoard;
    @BindView(R.id.edi_chat_reply_content)
    EditText mEditContent;
    @BindView(R.id.tv_album)
    ImageView mAlbum;
    @BindView(R.id.tv_video)
    ImageView mVideo;
    @BindView(R.id.text_chat_reply)
    TextView mSend;
    @BindView(R.id.ll_function_point)
    LinearLayout mFunction;
    @BindView(R.id.chat_list)
    ListView mChatList;
    private String mContent = "";
    private List<ImageBean> mBinnerList;
    private ImageAndTextDialog imageAndTextDialog;
    private List<TrainRecordBean.ContentRecord> mList = new ArrayList<>();
    List<RobotResultBean.DataBean.Answers.Pics> picLists;
    private RoBotIMAdapter mAdapter;
    public final static int CHECK_VIDEO_REQUEST = 24;
    private String path;
    private File tempFile;
    private String url;//视频播放地址
    @Override
    protected int getLauoutId() {
        return R.layout.activity_robot_im;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        title.setText("销售训练室");
        titleBack.setVisibility(View.VISIBLE);
        SoftKeyBoardListener.setListener(RoBotIMActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int rootHight, int keyBroadheight) {
                Log.i("TAG", "键盘显示 跟布局" + rootHight + "，高度" + keyBroadheight + "，ll高度" + linearLayout.getMeasuredHeight());
                ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
                params.height = rootHight - keyBroadheight - linearLayout.getMeasuredHeight() + 35;
                relativeLayout.setLayoutParams(params);
            }

            @Override

            public void keyBoardHide(int rootHight, int keyBroadheight) {
                Log.i("TAG", "键盘隐藏 跟布局" + rootHight + "，高度" + keyBroadheight + "，ll高度" + linearLayout.getMeasuredHeight());
                ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
                params.height = rootHight + keyBroadheight - linearLayout.getMeasuredHeight() + 35;
                relativeLayout.setLayoutParams(params);
            }
        });

        mEditContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    mFunction.setVisibility(View.GONE);
                    mSend.setVisibility(View.VISIBLE);
                } else {
                    mFunction.setVisibility(View.VISIBLE);
                    mSend.setVisibility(View.GONE);
                }
            }
        });
        mAdapter = new RoBotIMAdapter(context, mList);
        mChatList.setAdapter(mAdapter);
    }


    @OnClick({R.id.title_Back,R.id.iv_keyboard, R.id.tv_album, R.id.tv_video, R.id.text_chat_reply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.iv_keyboard:
                ToastUtils.makeText(context,"你好666啊！");
                break;
            case R.id.tv_album:
                mBinnerList = new ArrayList<>();
                picLists = new ArrayList<>();
                imageAndTextDialog = new ImageAndTextDialog(context, this::submit);
                imageAndTextDialog.show();
                break;
            case R.id.tv_video:
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, CHECK_VIDEO_REQUEST);
                break;
            case R.id.text_chat_reply:
                mContent = mEditContent.getText().toString().trim();
                if (mContent.isEmpty()) {
                    ToastUtils.makeText(context, "请输入您要询问的问题");
                    return;
                }
                TrainRecordBean.ContentRecord comm = new TrainRecordBean.ContentRecord(1, 2, mContent);
                mList.add(comm);
                mAdapter.notifyDataSetChanged();
                SubmitQuestion();
                break;
        }
    }

    /**
     * 咨询
     */
    private void SubmitQuestion() {
        mEditContent.setText("");
        Map<String, String> params = new HashMap<>();
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put("sId",SPUtil.getString(context,SALE_ID));
        params.put("keyWord", mContent);
        MyOkhttp.Okhttp(context, Url.TRAIN_ROBOT, "", params, (response, result, resultNote) -> {
            Log.i("TAG", "SubmitQuestion: " + response);
            Gson gson = new Gson();
            RobotResultBean robotResultBean = gson.fromJson(response, RobotResultBean.class);
            if (result.equals("1")) {
                TrainRecordBean.ContentRecord comm = new TrainRecordBean.ContentRecord(0, 2, resultNote);
                mList.add(comm);
                mAdapter.notifyDataSetChanged();
            } else {
                mContent = robotResultBean.getData().getQuestion();
                switch (robotResultBean.getData().getType()) {
                    case 1:// 你问的方式有误,该怎么问请询问io大神
                        TrainRecordBean.ContentRecord comm = new TrainRecordBean.ContentRecord(0, 2, resultNote);
                        mList.add(comm);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 2: //对不起,没有找到你想要的答案。你是要告诉我吗？
                        TrainRecordBean.ContentRecord comm2 = new TrainRecordBean.ContentRecord(0, 2, resultNote);
                        mList.add(comm2);
                        mAdapter.notifyDataSetChanged();
                        StopTipsDialog dialog = new StopTipsDialog(context, resultNote, "否", "是", new StopTipsDialog.OnSureBtnClickListener() {
                            @Override
                            public void sure() {
                                finishTrain("1");
                            }

                            @Override
                            public void cancle() {
                                finishTrain("0");
                            }
                        });
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        break;
                    case 3://只有视频
                        TrainRecordBean.ContentRecord comm3 = new TrainRecordBean.ContentRecord(0, 1, robotResultBean.getData().getAnswers().get(0).getPics().get(0).getPics(), robotResultBean.getData().getAnswers().get(0).getVideoAddress(),robotResultBean.getData().getQuestion());
                        mList.add(comm3);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 4: //只有图文
                        TrainRecordBean.ContentRecord comm4 = new TrainRecordBean.ContentRecord(0, 0, robotResultBean.getData().getAnswers().get(0).getPics().get(0).getPics(), robotResultBean.getData().getAnswers().get(0).getPics(),robotResultBean.getData().getQuestion(),robotResultBean.getData().getId());
                        mList.add(comm4);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 5:// 视频图文都有
                        TrainRecordBean.ContentRecord comm5 = new TrainRecordBean.ContentRecord(0, 3, robotResultBean.getData().getAnswers().get(0).getPics().get(0).getPics(), robotResultBean.getData().getAnswers().get(0).getVideoAddress(), robotResultBean.getData().getAnswers().get(0).getPics(),robotResultBean.getData().getQuestion(),robotResultBean.getData().getId());
                        mList.add(comm5);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 99://其他
                        TrainRecordBean.ContentRecord comm9 = new TrainRecordBean.ContentRecord(0, 2, resultNote);
                        mList.add(comm9);
                        mAdapter.notifyDataSetChanged();
                        break;

                }
            }
        });

    }

    private void finishTrain(String isFinish) {
        Map<String, String> params = new HashMap<>();
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put("sId",SPUtil.getString(context,SALE_ID));
        params.put("keyWord", mContent);
        params.put("isFinish", isFinish);
        MyOkhttp.Okhttp(context, Url.FINISH_TRAIN, "", params, (response, result, resultNote) -> Log.i("TAG", "onRequestComplete: " + response));

    }

    /**
     * 提交图文答案
     *
     * @param content
     */
    private void submit(String content) {
        Map<String, String> params = new HashMap<>();
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put("sId",SPUtil.getString(context,SALE_ID));
        params.put("keyWord", mContent);
        params.put("describe", content);
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
                if (robotResultBean.getResultCode() == 1) {
                    TrainRecordBean.ContentRecord comm99 = new TrainRecordBean.ContentRecord(0, 2, robotResultBean.getMsg());
                    mList.add(comm99);
                    mAdapter.notifyDataSetChanged();
                } else {
                    RobotResultBean.DataBean.Answers.Pics pics = new RobotResultBean.DataBean.Answers.Pics(mBinnerList.get(0).getImage(),content);
                    picLists.add(pics);
                    TrainRecordBean.ContentRecord comm = new TrainRecordBean.ContentRecord(1, 0, mBinnerList.get(0).getImage(), content,picLists,robotResultBean.getData().getId(),robotResultBean.getData().getPosition());
                    mList.add(comm);
                    mAdapter.notifyDataSetChanged();
                    switch (robotResultBean.getData().getType()) {
                        case 6:// 您已上传过,请等待后台审核....
                            TrainRecordBean.ContentRecord comm6 = new TrainRecordBean.ContentRecord(0, 2, robotResultBean.getMsg());
                            mList.add(comm6);
                            mAdapter.notifyDataSetChanged();
                            break;
                        case 7://亲,您还要上传更多吗？
                            TrainRecordBean.ContentRecord comm7 = new TrainRecordBean.ContentRecord(0, 2, robotResultBean.getMsg());
                            mList.add(comm7);
                            mAdapter.notifyDataSetChanged();
                            StopTipsDialog dialog = new StopTipsDialog(context, robotResultBean.getMsg(), "否", "是", new StopTipsDialog.OnSureBtnClickListener() {
                                @Override
                                public void sure() {
                                    finishTrain("1");
                                }
                                @Override
                                public void cancle() {
                                    finishTrain("0");
                                }
                            });
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                            break;
                        case 99://
                            TrainRecordBean.ContentRecord comm99 = new TrainRecordBean.ContentRecord(0, 2, robotResultBean.getMsg());
                            mList.add(comm99);
                            mAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            }
        });
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
                case CHECK_VIDEO_REQUEST:
                    Uri uri = data.getData();
                    try {
                        path = UriUtils.getPath(this, uri);
                        tempFile = new File(path);
                        url = tempFile.getAbsolutePath();
                    } catch (Exception e) {

                    }
                    if(!tempFile.exists()){
                        tempFile.mkdirs();
                    }
                    submitVideo(tempFile,uri);//上传提交
                    break;
            }
        }
    }

    private void submitVideo(File tempFile, Uri uri) {
        Map<String, String> params = new HashMap<>();
        params.put("storeId","1");
        params.put("sId","1");
        params.put("keyWord",mContent);

        dialog.show();
        OkHttpUtils.post().url(Url.ADD_TRAIN_RECORD_ABOUT_VIDEO).params(params)
                .addFile("uploadFile", tempFile.getName(), tempFile)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.makeText(context, e.getMessage() + tempFile.getName() + tempFile);
                Log.i("TAG", "onError: " + e.getMessage() + tempFile.getName() + tempFile);
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("TAG", "onResponse: " + response);
                Gson gson = new Gson();
                dialog.dismiss();
                RobotResultBean robotResultBean = gson.fromJson(response, RobotResultBean.class);
                if (robotResultBean.getResultCode() == 1) {
                    TrainRecordBean.ContentRecord comm99 = new TrainRecordBean.ContentRecord(0, 2, robotResultBean.getMsg());
                    mList.add(comm99);
                    mAdapter.notifyDataSetChanged();
                } else {
                    TrainRecordBean.ContentRecord comm = new TrainRecordBean.ContentRecord(1, 1, uri, url,mContent);
                    mList.add(comm);
                    mAdapter.notifyDataSetChanged();
                    switch (robotResultBean.getData().getType()) {
                        case 6:// 您已上传过,请等待后台审核....
                            TrainRecordBean.ContentRecord comm6 = new TrainRecordBean.ContentRecord(0, 2, robotResultBean.getMsg());
                            mList.add(comm6);
                            mAdapter.notifyDataSetChanged();
                            break;
                        case 7://亲,您还要上传更多吗？
                            TrainRecordBean.ContentRecord comm7 = new TrainRecordBean.ContentRecord(0, 2, robotResultBean.getMsg());
                            mList.add(comm7);
                            mAdapter.notifyDataSetChanged();
                            StopTipsDialog dialog = new StopTipsDialog(context, robotResultBean.getMsg(), "否", "是", new StopTipsDialog.OnSureBtnClickListener() {
                                @Override
                                public void sure() {
                                    finishTrain("1");
                                }

                                @Override
                                public void cancle() {
                                    finishTrain("0");
                                }
                            });
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                            break;
                        case 8: //再次上传会覆盖原先的视频,您确定上传吗？
                            TrainRecordBean.ContentRecord comm8 = new TrainRecordBean.ContentRecord(0, 2, robotResultBean.getMsg());
                            mList.add(comm8);
                            mAdapter.notifyDataSetChanged();
                            StopTipsDialog stopTipsDialog = new StopTipsDialog(context, robotResultBean.getMsg(), "否", "是", new StopTipsDialog.OnSureBtnClickListener() {
                                @Override
                                public void sure() {
                                    finishTrain("1");
                                }

                                @Override
                                public void cancle() {
                                    finishTrain("0");
                                }
                            });
                            stopTipsDialog.setCanceledOnTouchOutside(false);
                            stopTipsDialog.show();
                            break;
                    }
                }
            }
        });
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
}