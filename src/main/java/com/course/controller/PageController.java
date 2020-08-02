package com.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
* 页面跳转Controller
* */
@Controller
public class PageController {
    /*
    * 动态页面跳转
    * */
    @RequestMapping(value="/{pageName}")
    public String autoPage(@PathVariable String pageName){
        System.out.println("发出的请求为:"+pageName);
        return pageName;
    }
    @RequestMapping(value="/student/{pageName}")
    public String studentPages(@PathVariable String pageName){
        System.out.println("发出的请求为:student/"+pageName);
        return "student/"+pageName;
    }
    @RequestMapping(value="/teacher/{pageName}")
    public String teacherPages(@PathVariable String pageName){
        System.out.println("发出的请求为:teacher/"+pageName);
        return "teacher/"+pageName;
    }
}
