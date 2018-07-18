package com.empowerment.salesrobot.ui.model;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/17.
 * Description:
 */
public class Topic {
    private String title;
    private String time;
    private String localName; //本地的名称
    private String localVideoPath; //本地的路径
    private String duration; //本地视频时长

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLocalVideoPath() {
        return localVideoPath;
    }

    public void setLocalVideoPath(String localVideoPath) {
        this.localVideoPath = localVideoPath;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
