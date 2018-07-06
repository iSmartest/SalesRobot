package com.empowerment.salesrobot.view.voicebutton;

import java.io.File;

/**
 * 描述：添加类的描述
 * 录音相关的接口
 *
 * @author Created by wxl
 * @e-mail mmwxl666@163.com
 * @time Created on 2018/6/4
 */
public interface RecordStrategy {
    /*
    录音相关内容
     */
    //录音前 清除 重置等操作
    public void ready();

    //开始录音
    public void start();

    //结束录音
    public void stop();

    //录音失败删除原来的旧文件
    public void delOldFile();

    /**
     * 获取录音音量的大小
     *
     * @return
     */
    public double getAmplitude();

    //获取录音文件路径
    public String getFilePath();

    //录音时长超过60S
    public void maxStop();

    //获取文件
    File getFile();
}
