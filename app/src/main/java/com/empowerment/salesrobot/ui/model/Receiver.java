package com.empowerment.salesrobot.ui.model;

import java.io.Serializable;

/**
 * Created by 小火
 * Create time on  2018/3/24
 * My mailbox is 1403241630@qq.com
 */

public class Receiver implements Serializable {
    private String title;
    private String text;
    private String msg;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
