package com.empowerment.salesrobot.ui.model;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/20.
 * Description:
 */
public class TrainKnowledgeBean {
    private String resultCode;
    private String msg;
    private DataBean data;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private String maintain;

        public String getMaintain() {
            return maintain;
        }

        public void setMaintain(String maintain) {
            this.maintain = maintain;
        }
    }

}
