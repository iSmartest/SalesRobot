package com.empowerment.salesrobot.ui.model;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class NewPointBean {
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

    public static class DataBean{
        public List<CList> cList;

        public List<CList> getcList() {
            return cList;
        }

        public void setcList(List<CList> cList) {
            this.cList = cList;
        }

        public class CList{
            private String idea;
            private String pic;
            private String id;

            public String getIdea() {
                return idea;
            }

            public void setIdea(String idea) {
                this.idea = idea;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }

}
