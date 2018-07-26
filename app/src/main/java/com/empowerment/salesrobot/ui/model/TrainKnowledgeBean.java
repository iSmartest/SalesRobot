package com.empowerment.salesrobot.ui.model;

import com.empowerment.salesrobot.ui.base.BaseBean;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/20.
 * Description:
 */
public class TrainKnowledgeBean extends BaseBean {

    private DataBean data;

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
