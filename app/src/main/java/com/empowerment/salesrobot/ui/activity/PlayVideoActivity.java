package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.empowerment.salesrobot.R;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/4.
 * Description:
 */
public class PlayVideoActivity extends AppCompatActivity{
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.nice_video_player)
    NiceVideoPlayer mNiceVideoPlayer;
    private String name;
    private String videoUrl ;
    private String videoUri ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        ButterKnife.bind(this);
        initView();//实例化
        loadData();//加载数据
    }

    protected void loadData() {
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        mNiceVideoPlayer.setUp(videoUrl, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle(name);
        controller.setLenght(98000);
        Glide.with(this)
                .load(videoUri)
                .into(controller.imageView());
        mNiceVideoPlayer.setController(controller);
    }

    protected void initView() {
        name = getIntent().getStringExtra("mName");
        videoUrl = getIntent().getStringExtra("url");
        videoUri = getIntent().getStringExtra("uri");
        title.setText("");
        titleBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.title_Back})
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
}
