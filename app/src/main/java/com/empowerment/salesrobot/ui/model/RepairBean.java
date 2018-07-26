package com.empowerment.salesrobot.ui.model;

import com.empowerment.salesrobot.ui.base.BaseBean;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/25.
 * Description:
 */
public class RepairBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private List<RepairList> repairList;

        public List<RepairList> getRepairList() {
            return repairList;
        }

        public void setRepairList(List<RepairList> repairList) {
            this.repairList = repairList;
        }

        public static class RepairList{
            private Long date;
            private String item;
            private String price;

            public Long getDate() {
                return date;
            }

            public void setDate(Long date) {
                this.date = date;
            }

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
