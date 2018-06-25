package cn.com.gree.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @autor 260172
 * @date 2018/6/25 15:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataBaseLog {
    /**
     * 操作类型
     * @return
     */
    Operate operate();
    /**
     * 操作表
     * @return
     */
    String table();
    /**
     * 操作模块
     * @return
     */
    String module();
}
