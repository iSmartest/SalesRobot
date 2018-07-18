package com.empowerment.salesrobot.ui.model;



import android.net.Uri;

import com.empowerment.salesrobot.config.Url;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/15.
 * Description:
 */
public class TrainRecordBean {

    private List<ContentRecord> contentRecord;

    public List<ContentRecord> getContentRecord() {
        return contentRecord;
    }

    public void setContentRecord(List<ContentRecord> contentRecord) {
        this.contentRecord = contentRecord;
    }

    public static class ContentRecord {
        private int leftOrRight;//0左1右
        private int contentType;//内容类型0图文1视频2文字3视频图文
        private String content;
        private Uri uri;//视频缩略图
        private String pic;//图片
        private String url;//视频地址
        private List<RobotResultBean.DataBean.Answers.Pics> picLists;
        //本地视频
        public ContentRecord(int leftOrRight, int contentType, Uri uri,String url) {
            this.leftOrRight = leftOrRight;
            this.contentType = contentType;
            this.uri = uri;
            this.url = url;
        }
        //本地图文
        public ContentRecord(int leftOrRight, int contentType, String pic,String content,List<RobotResultBean.DataBean.Answers.Pics> picLists,Uri uri) {
            this.leftOrRight = leftOrRight;
            this.contentType = contentType;
            this.picLists = picLists;
            this.pic = pic;
            this.uri = uri;
            this.content = content;
        }
        //纯文
        public ContentRecord(int leftOrRight, int contentType, String content) {
            this.leftOrRight = leftOrRight;
            this.contentType = contentType;
            this.content = content;
        }
        //图文
        public ContentRecord(int leftOrRight, int contentType, String pic, List<RobotResultBean.DataBean.Answers.Pics> picLists) {
            this.leftOrRight = leftOrRight;
            this.contentType = contentType;
            this.pic = pic;
            this.picLists = picLists;
        }
        //视频
        public ContentRecord(int leftOrRight, int contentType,String pic,String url) {
            this.leftOrRight = leftOrRight;
            this.contentType = contentType;
            this.pic = pic;
            this.url = url;
        }
        //视频+图文
        public ContentRecord(int leftOrRight, int contentType,String pic,String url, List<RobotResultBean.DataBean.Answers.Pics> picLists) {
            this.leftOrRight = leftOrRight;
            this.contentType = contentType;
            this.pic = pic;
            this.url = url;
            this.picLists = picLists;
        }



        public int getLeftOrRight() {
            return leftOrRight;
        }

        public void setLeftOrRight(int leftOrRight) {
            this.leftOrRight = leftOrRight;
        }

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        public Uri getUri() {
            return uri;
        }

        public void setUri(Uri url) {
            this.uri = url;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<RobotResultBean.DataBean.Answers.Pics> getPicLists() {
            return picLists;
        }

        public void setPicLists(List<RobotResultBean.DataBean.Answers.Pics> picLists) {
            this.picLists = picLists;
        }
    }
}
