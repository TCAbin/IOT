package cn.com.gree.utils;


import org.apache.commons.lang.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

public class DateConverter implements Converter<String,Date> {

    private static final String[] PETTERNS = {"yyyy-MM-dd HH:mm:ss", "yyyy:MM:dd HH:mm:ss",
            "yyyy-MM-dd", "yyyy:MM:dd", "HH:mm:ss","yyyy-MM-dd HH:mm"};


    @Override
    public Date convert(String s) {
        if(!"".equals(s)){
            try {
                return DateUtils.parseDateStrictly(s,PETTERNS);
            } catch (ParseException e) {
                System.out.println("暂不支持此格式，请在DateConverter添加");
            }
        }
        return null;
    }
}
