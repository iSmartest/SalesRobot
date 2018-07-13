package com.empowerment.salesrobot.ui.activity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.listener.SoftKeyBoardListener;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.view.voicebutton.AudioRecorder;
import com.empowerment.salesrobot.view.voicebutton.RecordButton;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/4.
 * Description:
 */
public class RoBotIMActivity extends BaseActivity implements RecordButton.RecordListener {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rl_item)
    RelativeLayout relativeLayout;
    @BindView(R.id.ll_item)
    LinearLayout linearLayout;
    @BindView(R.id.iv_voice)
    ImageView mVoice;
    @BindView(R.id.iv_keyboard)
    ImageView mKeyBoard;
    @BindView(R.id.edi_chat_reply_content)
    EditText mEditContent;
    @BindView(R.id.tv_long_chat)
    RecordButton mLongChat;
    @BindView(R.id.tv_add)
    ImageView mAdd;
    @BindView(R.id.text_chat_reply)
    TextView mSend;
    private String type;
    private String content;
    @Override
    protected int getLauoutId() {
        return R.layout.activity_robot_im;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        type = getIntent().getStringExtra("type");
        if (type.equals("0")) {
            title.setText("销售训练室");
        } else if (type.equals("1")) {
            title.setText("维修保养室");
        } else if (type.equals("2")) {
            title.setText("保险理赔室");
        }
        titleBack.setVisibility(View.VISIBLE);

        SoftKeyBoardListener.setListener(RoBotIMActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int rootHight,int keyBroadheight) {
                Log.i("TAG", "键盘显示 跟布局" + rootHight + "，高度" + keyBroadheight + "，ll高度" +linearLayout.getMeasuredHeight());
                ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
                params.height = rootHight - keyBroadheight - linearLayout.getMeasuredHeight()+35;
                relativeLayout.setLayoutParams(params);
            }

            @Override

            public void keyBoardHide(int rootHight,int keyBroadheight) {
                Log.i("TAG", "键盘隐藏 跟布局" + rootHight + "，高度" + keyBroadheight + "，ll高度" +linearLayout.getMeasuredHeight());
                ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
                params.height = rootHight + keyBroadheight - linearLayout.getMeasuredHeight()+35;
                relativeLayout.setLayoutParams(params);
            }
        });

        mLongChat.setListener(this);
        mLongChat.setRecordStrategy(new AudioRecorder());
    }

    @OnClick({R.id.title_Back,R.id.iv_voice,R.id.iv_keyboard,R.id.tv_add,R.id.text_chat_reply})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.title_Back:
                finish();
                break;
            case R.id.iv_voice:
                mVoice.setVisibility(View.GONE);
                mKeyBoard.setVisibility(View.VISIBLE);
                mEditContent.setVisibility(View.GONE);
                mLongChat.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_keyboard:
                mVoice.setVisibility(View.VISIBLE);
                mKeyBoard.setVisibility(View.GONE);
                mEditContent.setVisibility(View.VISIBLE);
                mLongChat.setVisibility(View.GONE);
                break;
            case R.id.tv_add:

                break;
            case R.id.text_chat_reply:

                break;
        }

    }

    @Override
    public void RecordEnd(File path) {

    }
}