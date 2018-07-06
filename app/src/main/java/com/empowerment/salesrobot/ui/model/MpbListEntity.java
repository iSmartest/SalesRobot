package com.empowerment.salesrobot.ui.model;
import java.util.List;

/**
 * 客户 维修保养保险记录
 */
public class MpbListEntity {

    /**
     * data : {"maintianList":[{"date":1526619672,"item":"清洗发动机"},{"date":1526533296,"item":"更换轮胎"}]}
     * msg : 查询成功
     * resultCode : 0
     */

    private DataBean data;
    private String msg;
    private int resultCode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public static class DataBean {
        private List<MaintianListBean> maintianList;

        public List<MaintianListBean> getMaintianList() {
            return maintianList;
        }

        public void setMaintianList(List<MaintianListBean> maintianList) {
            this.maintianList = maintianList;
        }

        public static class MaintianListBean {
            /**
             * date : 1526619672
             * item : 清洗发动机
             */

            private int date;
            private String item;
            private Object price;

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }
        }
    }
}
