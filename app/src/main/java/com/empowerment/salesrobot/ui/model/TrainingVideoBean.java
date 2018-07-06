package com.empowerment.salesrobot.ui.model;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/4.
 * Description:
 */
public class TrainingVideoBean {
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
        private List<VideoList> videoList;

        public List<VideoList> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoList> videoList) {
            this.videoList = videoList;
        }

        public static class VideoList{
            private String coverAddress;
            private String address;
            private String name;
            private String id;

            public String getCoverAddress() {
                return coverAddress;
            }

            public void setCoverAddress(String coverAddress) {
                this.coverAddress = coverAddress;
            }

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
        }
    }
}
