package com.empowerment.salesrobot.ui.model;

import com.empowerment.salesrobot.ui.base.BaseBean;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/25.
 * Description:
 */
public class BuyCarRecordBean extends BaseBean {
    private DataBean data;

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
