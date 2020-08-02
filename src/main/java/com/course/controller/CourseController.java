package com.course.controller;


import com.course.model.*;
import com.course.service.StudentsService;
import com.course.service.TeacherService;
import com.course.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {


    @Autowired
    private UsersService usersService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private TeacherService teacherService;


    /**
     * 登录Controller
     *
     */
    @PostMapping("/toLogin")
    public String studentLogin(Users users, HttpServletRequest hslr, Model mod){
        HttpSession session = hslr.getSession();

        try {
            Users us = this.usersService.userLogin(users);
                if(ObjectUtils.isEmpty(us)){
                    mod.addAttribute("loginMessage","账号或密码错误");
                    return "login";
                }else{
                    mod.addAttribute("loginUsersInfo",us);
                    session.setAttribute("loginUserId",us.getUserid());
                    session.setAttribute("loginUserRole",us.getUserrole());
                    System.out.println("当前登录用户为："+session.getAttribute("loginUserId"));
                    System.out.println("当前登录身份："+us.getUserrole());
                    if(us.getUserrole().equals("学生")){
                        //重定向相当于重新发出/student/studentcourse请求，
                        //studentController中的LoginState方法就会判断session是否存在
                        return "redirect:/student/studentcourse";
                    }
                    else if(us.getUserrole().equals("老师")){
                        return "redirect:/teacher/teachercourse";
                    }
                    else {
                        System.out.println("登录账号异常情况");
                        return "error";
                    }
                }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public String Logout(HttpServletRequest hslr){
        HttpSession session = hslr.getSession();
        //session清空
        session.setAttribute("loginUserId",null);

        //重定向到login
        return "redirect:/login";

    }

/*
* 添加归档和删除归档 学生教师是同样的操作
*所以都放在courseController中
*
* */

    /*
     *添加课程到归档
     * */
    @ResponseBody
    @RequestMapping("/addArchivecourse")
    public String addArchiveCourse(@RequestBody String courseid, HttpServletRequest hslr) {
        Archive archive = new Archive();
        try{
            HttpSession session = hslr.getSession();
            Object loginSeesion = session.getAttribute("loginUserId");
            archive.setUserid(loginSeesion.toString());
            archive.setCourseid(Integer.parseInt(courseid));
            this.teacherService.addArchiveCourse(archive);
            return "该课程归档成功";
        }catch(Exception e){
            e.printStackTrace();
            return  "归档失败";
        }
    }

    /*
     *还原归档信息
     * */
    @ResponseBody
    @RequestMapping("/deleteArchiveByCourseid")
    public String deleteArchiveByCourseid(@RequestBody String courseid,HttpServletRequest hslr,Model mod){
        try{
            HttpSession session = hslr.getSession();
            Object loginSeesion = session.getAttribute("loginUserId");
            this.teacherService.deleteArchiveCourse(Integer.parseInt(courseid));
            return "恢复成功";
        }catch(Exception e){
            e.printStackTrace();
            return  "恢复失败";
        }
    }

}
