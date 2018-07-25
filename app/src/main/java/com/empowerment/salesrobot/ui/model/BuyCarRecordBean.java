package com.empowerment.salesrobot.ui.model;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/25.
 * Description:
 */
public class BuyCarRecordBean {
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
        private List<CarBuyList> carBuyList;

        public List<CarBuyList> getCarBuyList() {
            return carBuyList;
        }

        public void setCarBuyList(List<CarBuyList> carBuyList) {
            this.carBuyList = carBuyList;
        }

        public static class CarBuyList{
            private Long date;
            private String carType ;
            private String reBuy;

            public Long getDate() {
                return date;
            }

            public void setDate(Long date) {
                this.date = date;
            }

            public String getCarType() {
                return carType;
            }

            public void setCarType(String carType) {
                this.carType = carType;
            }

            public String getReBuy() {
                return reBuy;
            }

            public void setReBuy(String reBuy) {
                this.reBuy = reBuy;
            }
        }


    }
}
