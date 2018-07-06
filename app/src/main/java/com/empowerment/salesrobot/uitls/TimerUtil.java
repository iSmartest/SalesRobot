package com.empowerment.salesrobot.uitls;


import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Random;

/**
 * 获取验证码倒计时
 * Created by 小火
 * Create time on  2017/3/22
 * My mailbox is 1403241630@qq.com
 */
public class TimerUtil {

    private TextView tv;
    private CountDownListener mCountDownListener;

    public TimerUtil(TextView tv) {
        this.tv = tv;
    }

    public TimerUtil(TextView tv, CountDownListener mCountDownListener) {
        this.tv = tv;
        this.mCountDownListener = mCountDownListener;
    }

    public void timers() {
        timer.start();
    }

    public void countDown() {
        count.start();
    }


    public CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tv.setEnabled(false);
            tv.setText((millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            tv.setEnabled(true);
            tv.setText("重新获取");
        }
    };
    public CountDownTimer count = new CountDownTimer(1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tv.setEnabled(false);
        }

        @Override
        public void onFinish() {
            tv.setEnabled(true);
            mCountDownListener.sendMessage(true);
        }
    };

    public interface CountDownListener {
        void sendMessage(boolean isFinish);
    }

    /**
     * 获取随机验证码
     */
    public static String getNum() {
        StringBuilder sb = new StringBuilder();
        //随机生成6位数  发送到聚合
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int a = random.nextInt(10);
            sb.append(a);
        }
        return sb.toString();
    }

}
