package com.empowerment.salesrobot.ui.model;

import com.empowerment.salesrobot.ui.base.BaseBean;
import java.util.List;
public class VipOrYxBean extends BaseBean {


    /**
     * data : {"custList":[{"address":"北京上地","age":26,"id":1,"idCard":"412726199001301237","name":"郭靖","phone":"18332212561","pic":"aaa","sex":0,"work":"销售员"},{"address":"北京朝阳","age":65,"id":2,"idCard":"412726199001301237","name":"1111","phone":"15866585554","pic":"bbb","sex":1,"work":"销售主管"},{"address":"上海","age":30,"id":3,"idCard":"6664646546465","name":"黄蓉","phone":"15465464646","pic":"ccc","sex":2,"work":"老板"}]}
     * msg : 查询成功
     * resultCode : 0
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<CustListBean> custList;

        public List<CustListBean> getCustList() {
            return custList;
        }

        public void setCustList(List<CustListBean> custList) {
            this.custList = custList;
        }

        public static class CustListBean {
            /**
             * address : 北京上地
             * age : 26
             * id : 1
             * idCard : 412726199001301237
             * name : 郭靖
             * phone : 18332212561
             * pic : aaa
             * sex : 0
             * work : 销售员
             */
            private String date;
            private String address;
            private String phone;
            private String work;
            private String idCard;
            private int sex;
            private String name;
            private List<String> dates;
            private int id;
            private String pic;
            private String age;
            private int count;
            private String content;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getWork() {
                return work;
            }

            public void setWork(String work) {
                this.work = work;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<String> getDates() {
                return dates;
            }

            public void setDates(List<String> dates) {
                this.dates = dates;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
