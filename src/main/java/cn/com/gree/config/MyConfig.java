package cn.com.gree.config;

import cn.com.gree.filter.UserSecurityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfig extends WebMvcConfigurerAdapter {


    /**
     * @author Abin
     * @date 2018/6/23 16:52
     * 跨域处理
     *
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowCredentials(true);
    }

    /**
     * @author Abin
     * @date 2018/6/23 16:52
     * 拦截器
     *
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserSecurityInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}
