package com.empowerment.salesrobot.ui.model;

import com.empowerment.salesrobot.ui.base.BaseBean;

import java.util.List;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class CarBrandBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private List<CarList> carList;

        public List<CarList> getCarList() {
            return carList;
        }

        public void setCarList(List<CarList> carList) {
            this.carList = carList;
        }

        public static class CarList{
            private String motor;
            private float score;
            private String level;
            private double prePrice;
            private String name;
            private double guidePrice;
            private String gearBox;
            private String structure;
            private String pic;
            public String getMotor() {
                return motor;
            }

            public void setMotor(String motor) {
                this.motor = motor;
            }

            public float getScore() {
                return score;
            }

            public void setScore(float score) {
                this.score = score;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public double getPrePrice() {
                return prePrice;
            }

            public void setPrePrice(double prePrice) {
                this.prePrice = prePrice;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getGuidePrice() {
                return guidePrice;
            }

            public void setGuidePrice(double guidePrice) {
                this.guidePrice = guidePrice;
            }

            public String getGearBox() {
                return gearBox;
            }

            public void setGearBox(String gearBox) {
                this.gearBox = gearBox;
            }

            public String getStructure() {
                return structure;
            }

            public void setStructure(String structure) {
                this.structure = structure;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}
