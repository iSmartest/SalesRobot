package com.empowerment.salesrobot.ui.model;

import com.empowerment.salesrobot.ui.base.BaseBean;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class NewPointBean extends BaseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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
