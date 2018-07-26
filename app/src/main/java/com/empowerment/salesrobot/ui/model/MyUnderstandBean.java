package com.empowerment.salesrobot.ui.model;

import com.empowerment.salesrobot.ui.base.BaseBean;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/5.
 * Description:
 */
public class MyUnderstandBean extends BaseBean {

    /**
     * data : {"experienceList":[{"image":"/upload/image/touxiang.jpg","name":"李广","time":1525743499000,"content":"要买就买奔驰"},{"image":"/upload/image/touxiang.jpg","name":"武则天","time":1525657103000,"content":"要买就买宝马"},{"image":"/upload/image/touxiang.jpg","name":"李广","time":1525484309000,"content":"要买就买挖掘机"},{"image":"/upload/image/touxiang.jpg","name":"武则天","time":1525397905000,"content":"要买就买奥托"}]}
     * resultCode : 0
     * msg : 查询成功
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ExperienceListBean> experienceList;

        public List<ExperienceListBean> getExperienceList() {
            return experienceList;
        }

        public void setExperienceList(List<ExperienceListBean> experienceList) {
            this.experienceList = experienceList;
        }

        public static class ExperienceListBean {
            /**
             * image : /upload/image/touxiang.jpg
             * name : 李广
             * time : 1525743499000
             * content : 要买就买奔驰
             * id:1
             */

            private String image;
            private String name;
            private long time;
            private String content;
            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

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

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
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
