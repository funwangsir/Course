package com.course.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
class WebAppConfig implements WebMvcConfigurer {
   
    @Override

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(!path.exists()) path = new File("");
        File upload = new File(path.getAbsolutePath(),"static/homeworkfiles/");
        if(!upload.exists()) upload.mkdirs();
        String classpath = upload.getAbsolutePath();//文件根目录

        System.out.println("文件上传目录:"+classpath);



        String staticMapping="/static/**";
        String localDirectory = "file:"+classpath;
        registry.addResourceHandler(staticMapping).addResourceLocations(localDirectory);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
