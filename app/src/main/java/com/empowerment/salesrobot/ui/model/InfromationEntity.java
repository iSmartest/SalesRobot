package com.empowerment.salesrobot.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * 客户资料
 */
public class InfromationEntity implements Serializable{


    /**
     * data : {"customerList":[{"date":1525925591000,"address":"头说啦咯人咯啦","idCard":"1545454545","work":"某公司高管","sex":20,"pic":"bbb","content":"买啥买  没钱","carType":"奔驰","phone":"18668656566","name":"李世民","id":2,"age":20,"hobby":"足球"},{"date":1525925556000,"address":"喜欢自驾游，想买一款越野山地SUV","idCard":"1122222222","work":"某公司高管","sex":10,"pic":"aaa","content":"？款越野山地SUV","carType":"宝马","phone":"18332212560","name":"李牧","id":1,"age":10,"hobby":"篮球"}]}
     * resultCode : 0
     * msg : 查询成功！
     */

    private DataBean data;
    private int resultCode;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        private List<CustomerListBean> customerList;

        public List<CustomerListBean> getCustomerList() {
            return customerList;
        }

        public void setCustomerList(List<CustomerListBean> customerList) {
            this.customerList = customerList;
        }

        public static class CustomerListBean {
            /**
             * date : 1525925591000
             * address : 头说啦咯人咯啦
             * idCard : 1545454545
             * work : 某公司高管
             * sex : 20
             * pic : bbb
             * content : 买啥买  没钱
             * carType : 奔驰
             * phone : 18668656566
             * name : 李世民
             * id : 2
             * age : 20
             * hobby : 足球
             */

            private long date;
            private String address;
            private String idCard;
            private String work;
            private int sex;
            private String pic;
            private String content;
            private String carType;
            private String phone;
            private String name;
            private int id;
            private int age;
            private String hobby;

            public long getDate() {
                return date;
            }

            public void setDate(long date) {
                this.date = date;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public String getWork() {
                return work;
            }

            public void setWork(String work) {
                this.work = work;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCarType() {
                return carType;
            }

            public void setCarType(String carType) {
                this.carType = carType;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getHobby() {
                return hobby;
            }

            public void setHobby(String hobby) {
                this.hobby = hobby;
            }
        }
    }
}
