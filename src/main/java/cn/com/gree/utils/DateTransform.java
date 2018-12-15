package cn.com.gree.utils;

import java.util.Calendar;
import java.util.Date;

public class DateTransform {

    /**
     * @autor 260172
     * @date 2018/6/26 14:53
     * 增加hour小时
     */
    public static Date conver (Date date,int hour){
//        if(hour == 12){
//            return date;
//        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);
        return cal.getTime();
    }
}
