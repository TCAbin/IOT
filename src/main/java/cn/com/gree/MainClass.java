package cn.com.gree;

import cn.com.gree.utils.DateConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class MainClass extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MainClass.class,args);

    }

    /**
     * @author 260172
     * @date 2018/6/26 9:04
     * 日期转换器
     */
    @Bean
    public DateConverter dateConverter(){
        return new DateConverter();
    }

    

}
