package com.empowerment.salesrobot.view.voicebutton;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：添加类的描述
 *
 * @author Created by wxl
 * @e-mail mmwxl666@163.com
 * @time Created on 2018/6/4
 */
public class AudioRecorder implements RecordStrategy {
    private MediaRecorder recorder;
    private String fileName;
    private String fileFolder = Environment.getExternalStorageDirectory()
            .getPath() + "/TestRecord";
    private File file;
    private boolean isRecording = false;

    @Override
    public void ready() {
        file = new File(fileFolder);
        if (!file.exists()) {
            file.mkdir();
        }
        //获取文件名
        fileName = getCurrentDate();
        recorder = new MediaRecorder();
        recorder.setOutputFile(fileFolder + File.separator + fileName + ".amr");
        // 设置MediaRecorder的音频源为主麦克风
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置MediaRecorder录制的音频格式
        recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        // 设置音频编码为AMR_NB
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    private String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
        //获取当前时间
        return df.format(new Date(System.currentTimeMillis()));
    }

    @Override
    public void start() {
        if (!isRecording) {
            try {
                recorder.prepare();
                recorder.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
            isRecording = true;
        }

    }

    @Override
    public void stop() {
        if (isRecording) {
            recorder.stop();
            recorder.release();
            isRecording = false;
        }
    }

    @Override
    public void delOldFile() {
        file = new File(fileFolder + File.separator + fileName + ".amr");
        file.deleteOnExit();
    }

    @Override
    public double getAmplitude() {
        if (!isRecording) {
            return 0;
        }
        return recorder.getMaxAmplitude();
    }

    @Override
    public String getFilePath() {
        return fileFolder + File.separator + fileName + ".amr";
    }

    @Override
    public void maxStop() {

    }

    @Override
    public File getFile() {
        if (file.length() > 0) {
            return file;
        } else {
            return null;
        }
    }
}
