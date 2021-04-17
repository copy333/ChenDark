package com.chen.context.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /*
    Spring Boot 默认提供了静态资源处理，可以直接使用Spring Boot的默认配置即可，默认提供的静态资源映射如下:
        classpath:/META-INF/resources
        classpath:/resources
        classpath:/static
        classpath:/public
    这些目录的静态资源是可以直接访问到的，上面这几个都是静态资源的映射路径，优先级顺序为：
           META-INF/resources > resources > static > public

    */
    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns("*")//允许远程端访问的域名,全局支持CORS（跨源请求）
                .allowedMethods("*") //允许请求的方法
                .allowedHeaders("*") //允许请求头
                .allowCredentials(true) // 是否允许证书（cookies）
                .maxAge(3600);   //跨域允许时间
    }
}