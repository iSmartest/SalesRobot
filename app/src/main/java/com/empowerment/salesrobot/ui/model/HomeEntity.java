package com.empowerment.salesrobot.ui.model;

import java.util.List;

public class HomeEntity {

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

    public static class DataBean {
        private String image;
        private String name;
        private int companyNoticeCount;
        private String notice;
        private List<ImageList> imageList;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCompanyNoticeCount() {
            return companyNoticeCount;
        }

        public void setCompanyNoticeCount(int companyNoticeCount) {
            this.companyNoticeCount = companyNoticeCount;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public List<ImageList> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageList> imageList) {
            this.imageList = imageList;
        }

        public static class ImageList{
            private String address;
            private String name;
            private String link;

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

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }
}
