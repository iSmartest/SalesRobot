package com.empowerment.salesrobot.ui.model;

import com.empowerment.salesrobot.ui.base.BaseBean;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/4.
 * Description:
 */
public class TrainingVideoBean extends BaseBean {

    private DataBean data;

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
