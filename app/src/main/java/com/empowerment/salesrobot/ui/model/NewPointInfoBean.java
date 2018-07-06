package com.empowerment.salesrobot.ui.model;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class NewPointInfoBean {

    private DataBean data;
    private String msg;
    private int resultCode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private String policy;
        private BuyPointDetail buyPointDetail;

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }

        public BuyPointDetail getBuyPointDetail() {
            return buyPointDetail;
        }

        public void setBuyPointDetail(BuyPointDetail buyPointDetail) {
            this.buyPointDetail = buyPointDetail;
        }

        public static class BuyPointDetail{
            /**
             * "detailDesc": "这款车的细节做的非常到位。",
             "bodyDesc": "这是一款三厢的车，高端大气上档次，你值得拥有。",
             "innerPic": "/upload/imgae/777.jpg",
             "innerDesc": "这款车的内饰非常棒！！！！",
             "detailPic": "/upload/detail/555.jpg",
             "bodyPic": "/upload/image/888.jpg"
             */
            private String detailDesc;
            private String bodyDesc;
            private String innerPic;
            private String innerDesc;
            private String detailPic;
            private String bodyPic;

            public String getDetailDesc() {
                return detailDesc;
            }

            public void setDetailDesc(String detailDesc) {
                this.detailDesc = detailDesc;
            }

            public String getBodyDesc() {
                return bodyDesc;
            }

            public void setBodyDesc(String bodyDesc) {
                this.bodyDesc = bodyDesc;
            }

            public String getInnerPic() {
                return innerPic;
            }

            public void setInnerPic(String innerPic) {
                this.innerPic = innerPic;
            }

            public String getInnerDesc() {
                return innerDesc;
            }

            public void setInnerDesc(String innerDesc) {
                this.innerDesc = innerDesc;
            }

            public String getDetailPic() {
                return detailPic;
            }

            public void setDetailPic(String detailPic) {
                this.detailPic = detailPic;
            }

            public String getBodyPic() {
                return bodyPic;
            }

            public void setBodyPic(String bodyPic) {
                this.bodyPic = bodyPic;
            }
        }
    }
}
