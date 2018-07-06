package com.empowerment.salesrobot.ui.model;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class TrainingDocBean {
    //{"data":{"tdoctList":[{"address":"/upload/2018/6/doc20180620135831190.pdf","name":"岗前培训","id":12,"content":"岗前培训"}]},"resultCode":0,"msg":"查询成功"}
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
        private List<TdoctList> tdoctList;

        public List<TdoctList> getTdoctList() {
            return tdoctList;
        }

        public void setTdoctList(List<TdoctList> tdoctList) {
            this.tdoctList = tdoctList;
        }

        public static class TdoctList{
            public String address;
            public String name;
            public String id;
            public String content;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
