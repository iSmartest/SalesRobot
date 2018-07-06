package com.empowerment.salesrobot.ui.model;


/**
 * Name 赋睿智能
 * Date 2018/6/21  10:12
 * 站在顶峰,看世界 跌落谷底,思人生
 */


public class GridViewBean {

    private int img;
    private String name;

    public GridViewBean(int img, String name) {
        this.img = img;
        this.name = name;
    }
    public GridViewBean() {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
