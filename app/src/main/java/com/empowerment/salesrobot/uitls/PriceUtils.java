package com.empowerment.salesrobot.uitls;

import java.math.BigDecimal;

/**
 * Created by za on 2018/7/26.
 */

public class PriceUtils {
    public static String getprice(double price){
        if(price<10000){
            return  price+"元";
        }else {
            double v = price / 10000;
            double value =new BigDecimal(v).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            return value+"万";
        }
    }
}
