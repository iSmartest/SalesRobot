package com.empowerment.salesrobot.ui.model;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/23.
 * Description:
 */
public class CustomerCountBean {
    private int resultCode;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private int nowCustomer;
        private int pastCustomer;
        private int vipCustomer;
        private int yiXiangCustomer;

        public int getNowCustomer() {
            return nowCustomer;
        }

        public void setNowCustomer(int nowCustomer) {
            this.nowCustomer = nowCustomer;
        }

        public int getPastCustomer() {
            return pastCustomer;
        }

        public void setPastCustomer(int pastCustomer) {
            this.pastCustomer = pastCustomer;
        }

        public int getVipCustomer() {
            return vipCustomer;
        }

        public void setVipCustomer(int vipCustomer) {
            this.vipCustomer = vipCustomer;
        }

        public int getYiXiangCustomer() {
            return yiXiangCustomer;
        }

        public void setYiXiangCustomer(int yiXiangCustomer) {
            this.yiXiangCustomer = yiXiangCustomer;
        }
    }
}
