package com.empowerment.salesrobot.ui.model;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/11.
 * Description:
 */
public class FieldRecordInfoBean {
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
        private List<ConList> conList;

        public List<ConList> getConList() {
            return conList;
        }

        public void setConList(List<ConList> conList) {
            this.conList = conList;
        }

        public static class ConList{
            private String date;
            private List<ConsultList> consultList;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public List<ConsultList> getConsultList() {
                return consultList;
            }

            public void setConsultList(List<ConsultList> consultList) {
                this.consultList = consultList;
            }

            public static class ConsultList{
                private String item;
                private String question;

                public String getItem() {
                    return item;
                }

                public void setItem(String item) {
                    this.item = item;
                }

                public String getQuestion() {
                    return question;
                }

                public void setQuestion(String question) {
                    this.question = question;
                }
            }
        }
    }
}
