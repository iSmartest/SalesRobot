package com.empowerment.salesrobot.ui.model;

/**
 * 销售员完善资料
 */
public class SalePerfectinBean {


    /**
     * data : {"sale":{"age":22,"id":6,"image":"15290260960399008.png","isOrPerfect":"已完善","name":"苏大大","number":"1","phone":"18332212589","saleIndex":0,"sex":"男","successIndex":0,"work":1}}
     * msg : 完善资料成功！
     * resultCode : 0
     */

    private DataBean data;
    private String msg;
    private int resultCode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * sale : {"age":22,"id":6,"image":"15290260960399008.png","isOrPerfect":"已完善","name":"苏大大","number":"1","phone":"18332212589","saleIndex":0,"sex":"男","successIndex":0,"work":1}
         */

        private SaleBean sale;

        public SaleBean getSale() {
            return sale;
        }

        public void setSale(SaleBean sale) {
            this.sale = sale;
        }

        public static class SaleBean {
            /**
             * age : 22
             * id : 6
             * image : 15290260960399008.png
             * isOrPerfect : 已完善
             * name : 苏大大
             * number : 1
             * phone : 18332212589
             * saleIndex : 0
             * sex : 男
             * successIndex : 0
             * work : 1
             */

            private int age;
            private int id;
            private String image;
            private String isOrPerfect;
            private String name;
            private String number;
            private String phone;
            private int saleIndex;
            private String sex;
            private int successIndex;
            private int work;

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getIsOrPerfect() {
                return isOrPerfect;
            }

            public void setIsOrPerfect(String isOrPerfect) {
                this.isOrPerfect = isOrPerfect;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getSaleIndex() {
                return saleIndex;
            }

            public void setSaleIndex(int saleIndex) {
                this.saleIndex = saleIndex;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public int getSuccessIndex() {
                return successIndex;
            }

            public void setSuccessIndex(int successIndex) {
                this.successIndex = successIndex;
            }

            public int getWork() {
                return work;
            }

            public void setWork(int work) {
                this.work = work;
            }
        }
    }
}
