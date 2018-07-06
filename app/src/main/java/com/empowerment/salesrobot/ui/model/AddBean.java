package com.empowerment.salesrobot.ui.model;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/2.
 * Description:
 */
public class AddBean {
    /**
     * data : {}
     * resultCode : 0
     * msg : 添加成功
     */

    private DataBean data;
    private int resultCode;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class DataBean {
    }
}
