package cn.com.gree.aop.annotation;

import cn.com.gree.aop.logEntity.OperateLog;
import cn.com.gree.utils.DateTransform;

import java.lang.reflect.Method;
import java.util.Date;

public class DataBaseLogParse {

    public static OperateLog parse(Class targetClass,String methodName){
        Method[] methods = targetClass.getMethods();
        for(Method m : methods){
            if(m.getName().equals(methodName)){
                if (m.isAnnotationPresent(DataBaseLog.class)) {
                    DataBaseLog dbl = m.getAnnotation(DataBaseLog.class);
                    OperateLog ol = new OperateLog();
                    String table = dbl.table();

                    ol.setModule(dbl.module());
                    ol.setOperateTable(table);
                    ol.setOperation(dbl.operate().name().toLowerCase());
                    ol.setDate(DateTransform.conver(new Date(),12));
                    return ol;
                }
                break;
            }
        }
        return null;
    }
}
