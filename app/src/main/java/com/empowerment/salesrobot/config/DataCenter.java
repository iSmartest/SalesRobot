package com.empowerment.salesrobot.config;

import java.util.ArrayList;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/19.
 * Description:
 */
public class DataCenter {

    public static ArrayList<String> hourList;//时间
    public static ArrayList<String> minuteList;//分钟
    public static ArrayList<String> sexList;//性别
    public static ArrayList<String> industryList;//行业
    public static ArrayList<String> intentionList;//购车意向
    public static ArrayList<String> customerStyleList;//购车意向

    public static ArrayList<String> getHourList(){
        hourList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10){
                hourList.add("0" + i);
            }else {
                hourList.add("" + i);
            }
        }
        return hourList;
    }
    public static ArrayList<String> getMinuteList() {
        minuteList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 10){
                minuteList.add("0" + i);
            }else {
                minuteList.add("" + i);
            }
        }
        return minuteList;
    }

    public static ArrayList<String> getCustomerStyleList() {
        customerStyleList = new ArrayList<>();
        customerStyleList.add("普通客户");
        customerStyleList.add("VIP客户");
        customerStyleList.add("预成交客户");
        return customerStyleList;
    }
    public static ArrayList<String> getSexList() {
        sexList = new ArrayList<>();
        sexList.add("男");
        sexList.add("女");
        return sexList;
    }

    public static ArrayList<String> getIndustryList() {
        industryList = new ArrayList<>();
        industryList.add("党政机关人员");
        industryList.add("企事业单位工作人员");
        industryList.add("商业及服务业工作人员");
        industryList.add("农林牧渔劳动者");
        industryList.add("学生");
        industryList.add("军人");
        industryList.add("无业");
        industryList.add("其他");
        return industryList;
    }
    public static ArrayList<String> getIntentionList() {
        intentionList = new ArrayList<>();
        intentionList.add("一个月以内购车");
        intentionList.add("三个月以内购车");
        intentionList.add("六个月以内购车");
        intentionList.add("已购车");
        intentionList.add("其他");
        return intentionList;
    }
}
