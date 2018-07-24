package com.empowerment.salesrobot.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/16.
 * Description:
 */
public class RobotResultBean {
    private String msg;
    private int resultCode;
    private DataBean data;

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
        private int type;
        private String image;
        private String question;
        private String name;
        private int id;
        private int position;
        private int isCheck;
        private List<Answers> answers;

        public int getIsCheck() {
            return isCheck;
        }

        public void setIsCheck(int isCheck) {
            this.isCheck = isCheck;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Answers> getAnswers() {
            return answers;
        }

        public void setAnswers(List<Answers> answers) {
            this.answers = answers;
        }

        public static class Answers{
            private String question;
            private String videoAddress;
            private List<Pics> pics;

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getVideoAddress() {
                return videoAddress;
            }

            public void setVideoAddress(String videoAddress) {
                this.videoAddress = videoAddress;
            }

            public List<Pics> getPics() {
                return pics;
            }

            public void setPics(List<Pics> pics) {
                this.pics = pics;
            }

            public static class Pics implements Serializable{
                private String des;
                private String pic;

                public Pics(String image, String content) {
                    this.pic = image;
                    this.des = content;
                }

                public String getDes() {
                    return des;
                }

                public void setDes(String des) {
                    this.des = des;
                }

                public String getPics() {
                    return pic;
                }

                public void setPics(String pics) {
                    this.pic = pics;
                }
            }
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
