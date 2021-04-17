package com.chen.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class fileConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 文件地址定位，兼容windows和Linux不同平台的写法
         * System.getProperty("user.dir")在Windows下指向的是项目的根目录，
         * 在Linux下指向的是 / 目录，测试如下（docker的openjdk11镜像中部署测试）：
         * root@7f93e1991064:/# find / -name 'img'
         *          /usr/src/myapp/resources/img
         *          /resources/img
         */
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {   //windows
            registry.addResourceHandler("/resources/**").addResourceLocations(
                    "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") +
                            "resources" + System.getProperty("file.separator"));
        }else{  //linux or mac
            registry.addResourceHandler("/resources/**").addResourceLocations(
                    "file:" + System.getProperty("user.dir") +
                            "usr" + System.getProperty("file.separator") +
                            "src" + System.getProperty("file.separator") +
                            "myapp" + System.getProperty("file.separator") +
                            "resources" + System.getProperty("file.separator"));
        }

    }
}
