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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private String policy;
        private List<BuyPointDetail> buyPointDetail;

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }

        public List<BuyPointDetail> getBuyPointDetail() {
            return buyPointDetail;
        }

        public void setBuyPointDetail(List<BuyPointDetail> buyPointDetail) {
            this.buyPointDetail = buyPointDetail;
        }

        public static class BuyPointDetail{

            private String sellPoint;
            private List<SellPointdsc> sellPointdsc;
            private String sellPointvid;
            private int type;

            public String getSellPoint() {
                return sellPoint;
            }

            public void setSellPoint(String sellPoint) {
                this.sellPoint = sellPoint;
            }

            public List<SellPointdsc> getSellPointdsc() {
                return sellPointdsc;
            }

            public void setSellPointdsc(List<SellPointdsc> sellPointdsc) {
                this.sellPointdsc = sellPointdsc;
            }

            public String getSellPointvid() {
                return sellPointvid;
            }

            public void setSellPointvid(String sellPointvid) {
                this.sellPointvid = sellPointvid;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public static class SellPointdsc{
                private String img;
                private String dsc;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getDsc() {
                    return dsc;
                }

                public void setDsc(String dsc) {
                    this.dsc = dsc;
                }
            }
        }
    }
}
