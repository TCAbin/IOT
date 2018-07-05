package cn.com.gree;

import cn.com.gree.utils.DateConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
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
     * @author Abin
     * @date 2018/7/4 12:50
     * tomcat重建入口
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainClass.class);
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
