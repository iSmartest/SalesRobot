package com.empowerment.salesrobot.view.voicebutton;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.empowerment.salesrobot.R;

import java.io.File;

/**
 * 描述：添加类的描述
 *
 * @author Created by wxl
 * @e-mail mmwxl666@163.com
 * @time Created on 2018/6/4
 */
public class RecordButton extends android.support.v7.widget.AppCompatButton {
    private Context ctx;
    private static final int MIN_RECORD_TIME = 1;//最短录音时间 秒
    private static final int MAX_RECORD_TIME = 60;//最长录音时间 秒
    private static final int OFF_RECORD = 0;//没有录音
    private static final int ON_RECORD = 1;//正在录音
    private Dialog recordDialog;//菊花轮
    private RecordStrategy recordStrategy;//用于录音相关的接口 如：开始 结束
    private RecordListener listener;
    private Thread thread;
    private int recordStatus = 0;//录音状态
    private float recordTime = 0.0f;//录音时长，如果录音时间太短则录音失败
    private double recordVoiceValue = 0.0;//录音音量值
    private boolean isCanceled = false;//是否消除录音
    private float downY;
    private float downX;
    private TextView dialog_tv;
    private ImageView dialog_img;


    public RecordButton(Context context) {
        super(context);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setListener(RecordListener listener) {
        this.listener = listener;
    }

    public void setRecordStrategy(RecordStrategy recordStrategy) {
        this.recordStrategy = recordStrategy;
    }

    private void init(Context context) {
        this.ctx = context;
        this.setText("按住 说话");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                //1.判断状态
                if (recordStatus == OFF_RECORD) {
                    showDialog(recordStatus);
                    downY = event.getY();
                    downX = event.getX();
                    if (recordStrategy != null) {
                        recordStrategy.ready();//准备
                        recordStatus = ON_RECORD;//改变状态 正在录音
                        recordStrategy.start();
                        callRecordTimeThread();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE://滑动
                //当按下滑动时
                float moveY = event.getY();
                float moveX = event.getX();
                if (downY - moveY > 50 && downX - moveX > 50) {
                    isCanceled = true;
                    showDialog(1);
                }
                if (downY - moveY < 20 && downX - moveX < 20) {
                    isCanceled = false;
                    showDialog(0);
                }
                break;
            case MotionEvent.ACTION_UP://抬起
                if (recordStatus == ON_RECORD) {
                    recordStatus = OFF_RECORD;
                    if (recordDialog.isShowing()) {
                        recordDialog.dismiss();
                    }
                    recordStrategy.stop();
                    thread.interrupt();
                    recordVoiceValue = 0.0;
                    if (isCanceled) {
                        recordStrategy.delOldFile();
                    } else {
                        if (recordTime < MIN_RECORD_TIME) {
                            showWarnToast("时间太短 录音失败");
                            //时间太短
                            recordStrategy.delOldFile();
                        } else {
                            if (listener != null) {
                                listener.RecordEnd(recordStrategy.getFile());
                            }
                        }
                    }
                    isCanceled = false;
                    this.setText("按住 说话");
                }
                break;
        }
        return true;
    }

    private void showDialog(int recordStatus) {
        if (recordDialog == null) {
            recordDialog = new Dialog(ctx, R.style.Dialogstyle);
            recordDialog.setContentView(R.layout.dialog_record);
            dialog_img = (ImageView) recordDialog
                    .findViewById(R.id.record_dialog_img);
            dialog_tv = (TextView) recordDialog
                    .findViewById(R.id.record_dialog_txt);
        }
        switch (recordStatus) {
            case OFF_RECORD://关
                dialog_img.setImageResource(R.drawable.record_animate_01);
                dialog_tv.setText("向上滑动可 取消录音");
                this.setText("松开手指 完成录音");
                break;
            case ON_RECORD://ing
                dialog_img.setImageResource(R.drawable.record_cancel);
                dialog_tv.setText("手指上滑 消除发送");
                this.setText("松开 结束");
                break;
            default:
                break;
        }
        dialog_tv.setTextSize(14);
        recordDialog.show();
    }

    // 录音Dialog图片随录音音量大小切换
    private void setDialogImage() {
        if (recordVoiceValue < 600.0) {
            dialog_img.setImageResource(R.drawable.record_animate_01);
        } else if (recordVoiceValue > 600.0 && recordVoiceValue < 1000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_02);
        } else if (recordVoiceValue > 1000.0 && recordVoiceValue < 1200.0) {
            dialog_img.setImageResource(R.drawable.record_animate_03);
        } else if (recordVoiceValue > 1200.0 && recordVoiceValue < 1400.0) {
            dialog_img.setImageResource(R.drawable.record_animate_04);
        } else if (recordVoiceValue > 1400.0 && recordVoiceValue < 1600.0) {
            dialog_img.setImageResource(R.drawable.record_animate_05);
        } else if (recordVoiceValue > 1600.0 && recordVoiceValue < 1800.0) {
            dialog_img.setImageResource(R.drawable.record_animate_06);
        } else if (recordVoiceValue > 1800.0 && recordVoiceValue < 2000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_07);
        } else if (recordVoiceValue > 2000.0 && recordVoiceValue < 3000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_08);
        } else if (recordVoiceValue > 3000.0 && recordVoiceValue < 4000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_09);
        } else if (recordVoiceValue > 4000.0 && recordVoiceValue < 6000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_10);
        } else if (recordVoiceValue > 6000.0 && recordVoiceValue < 8000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_11);
        } else if (recordVoiceValue > 8000.0 && recordVoiceValue < 10000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_12);
        } else if (recordVoiceValue > 10000.0 && recordVoiceValue < 12000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_13);
        } else if (recordVoiceValue > 12000.0) {
            dialog_img.setImageResource(R.drawable.record_animate_14);
        }
    }

    // 录音时间太短时Toast显示
    private void showWarnToast(String toastText) {
        Toast toast = new Toast(ctx);
        View warnView = LayoutInflater.from(ctx).inflate(
                R.layout.toast_warn, null);
        toast.setView(warnView);
        toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间
        toast.show();
    }

    // 开启录音计时线程
    private void callRecordTimeThread() {
        thread = new Thread(recordThread);
        thread.start();
    }

    // 录音线程
    private Runnable recordThread = new Runnable() {

        @Override
        public void run() {
            recordTime = 0.0f;
            while (recordStatus == ON_RECORD) {

                try {
                    Thread.sleep(100);
                    recordTime += 0.1;
                    // 获取音量，更新dialog
                    if (!isCanceled) {
                        recordVoiceValue = recordStrategy.getAmplitude();
                        recordHandler.sendEmptyMessage(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    };


    private Handler recordHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setDialogImage();
        }
    };

    public interface RecordListener {
        void RecordEnd(File path);
    }

}
