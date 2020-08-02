package com.course.controller;

import com.course.mapper.HomeworkMapper;
import com.course.model.*;
import com.course.model.mymodels.ArchiveUserCourse;
import com.course.service.StudentsService;
import com.course.service.TeacherService;
import com.course.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {
    @Autowired
    private StudentsService studentsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private TeacherService teacherService;


    @PostMapping("/stuRegister")
    public String stuRegister(Students students,Model mod){

        try {
            //将数据插入到students表
            this.studentsService.studentRegister(students);

            //将数据插入到users表
            Users us = new Users();
            us.setUserid(students.getStudentid());
            us.setUserpassword(students.getStudentpassword());
            us.setUsername(students.getStudentname());
            us.setUserschool(students.getStudentschool());
            us.setUserrole(students.getRole());
            this.usersService.AddUser(us);

            mod.addAttribute("loginUsersInfo",us);
            System.out.println("注册成功");
            return "login";
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("注册失败，请认真核对注册信息，如无误则联系管理员");
            mod.addAttribute("loginUsersInfo","注册失败，请认真核对注册信息，如无误则联系管理员");
            return "register";
        }
    }


/**
 *ajax获取学生注册账号信息
 */
    @ResponseBody
    @RequestMapping(value = "/checkStuidExisted" , method = RequestMethod.POST)
    public String checkUserIdExisted(@RequestBody String stuId){
        try {
            if(stuId.equals("邮箱/手机"))
                return "请输入账号";
            else if(ObjectUtils.isEmpty(this.studentsService.checkStuidExisted(stuId)))
                return "检验通过";
            return "该账号已存在";

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("检验出错");
            return "检验出错,请联系管理员";
        }
    }


    /*
    * 访问首页(student/studentcourse)时的登录验证,如果已经登录则绑定登录用户信息
    * */
    @RequestMapping("/student/studentcourse")
    public String LoginState(HttpServletRequest hslr,Model mod){
        try{
            HttpSession session = hslr.getSession();
            Object loginSeesion = session.getAttribute("loginUserId");
            if(ObjectUtils.isEmpty(loginSeesion)){
                return "login";
            }

            List<Course> courses ;//存储课程信息
            List<Map<String, Object>> resultDatas = new ArrayList<Map<String, Object>>();//整合数据
            Map<String, Object> m = new HashMap<String, Object>();

            //通过登录学生的id查询学生的课程相关信息
            courses = this.studentsService.getStudentsCourses(session.getAttribute("loginUserId").toString());
            //将课程信息整合到结果集

            if(courses.isEmpty()){
                System.out.println("未查询到该学生加入的课程");
            }

            //根据课程id 获取教师姓名集合 和 课程作业集合
            try{
                String courseid = null;

                for(int i=0;i<courses.size();i++){
                    //通过遍历将三个数据集整合到一个Map（resultDatas）
                    courseid = courses.get(i).getCourseid();

                    m = new HashMap<String, Object>();//每次遍历创建一个新的map对象
                    m.put("courseid", courseid);
                    m.put("coursename", courses.get(i).getCoursename());
                    m.put("courseclass", courses.get(i).getCourseclass());
                    m.put("courseyear", courses.get(i).getCourseyear());
                    m.put("courseterm", courses.get(i).getCourseterm());

                    if(!this.teacherService.stuCourseTeacherName(courseid).isEmpty()){
                        List<Teachers> li = this.teacherService.stuCourseTeacherName(courseid);
                        String tempTeaName = "";
                        //遍历添加教师名
                        for(int n= 0;n<li.size();n++){
                            tempTeaName = tempTeaName+li.get(n).getTeachername()+"&nbsp;";
                            m.put("teachername", tempTeaName);
                        }
                    }else{
                        System.out.println("此课程无教师，请检查数据库信息");
                    }

                    if(!this.studentsService.courseHomeworkName(courseid).isEmpty()){
                        List<Homework> Homeworks = this.studentsService.courseHomeworkName(courseid);
                        String temphomename = "";
                        //遍历添加课程名
                        for(int j = 0;j<Homeworks.size();j++){
                            temphomename = temphomename + Homeworks.get(j).getHomeworkname()+"<br>";
                            m.put("homeworknames", temphomename);
                        }
                    }else{
                        m.put("homeworknames", "暂无任务");
                        System.out.println("课程号为"+courseid+"的课程无homework");
                    }

                    resultDatas.add(m);//循环添加到List
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("获取数据库信息失败");
            }
            mod.addAttribute("resultData",resultDatas);
            return "student/studentcourse";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }

    }

    /*
    * 通过登录id查询学生账户相关信息
    * */
    @RequestMapping("/student/stuMyinfo")
    public String getStudentsInfos(Model mod,HttpServletRequest hslr){

        try{
            HttpSession se = hslr.getSession();
            Object loginSessionid = se.getAttribute("loginUserId");

            if(ObjectUtils.isEmpty(loginSessionid)){
                return "login";
            }else{
                //通过登录账号查询的所有数据返回给View
                Students students = this.studentsService.getStudentsInfos(loginSessionid.toString());
                mod.addAttribute("studentsInfo",students);
                return "student/stu-myinfo";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

/**
 *ajax 学生加入课程
 */

    @ResponseBody
    @RequestMapping(value = "/student/joinCourse" , method = RequestMethod.POST)
    public String joinCourseByCourseId(@RequestBody String courseId,Stucourse stucourse,HttpServletRequest hslr){
        try {
            System.out.println("当前courseid为："+courseId);
            if(courseId.equals(null)){
                return "请输入课程";
            }else{
                if(ObjectUtils.isEmpty(this.studentsService.checkCourseidExisted(courseId))){
                    return "未找到课程，请检查加入课程验证码是否输入正确";
                }else {
                    HttpSession se = hslr.getSession();
                    Object loginSessionid = se.getAttribute("loginUserId");

                    stucourse.setStudentid(loginSessionid.toString());
                    stucourse.setCourseid(courseId);
                    this.studentsService.AddStudentToCourse(stucourse);

                    return "添加成功";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("验证异常");
            return "error";
        }
    }


    /**
     *ajax 学生退出课程
     */
    @ResponseBody
    @RequestMapping("/student/quitCourse")
    public String quidCouseByCourseidAndStuid(@RequestBody String courseid,HttpServletRequest hsrt){
        HttpSession se = hsrt.getSession();
        Object loginSessionid = se.getAttribute("loginUserId");
        try {
            String stuid = loginSessionid.toString();
            this.studentsService.DeleteStudentcourse(stuid,courseid);
            return "退课成功";
        }catch (Exception e){
            return "退课失败，请联系管理员";
        }
    }


    /*
     *获取已归档课程信息
     * */
    @RequestMapping("/student/getArchiveInfos")
    public String getArchiveInfos(HttpServletRequest hslr,Model mod){
        try{
            HttpSession session = hslr.getSession();
            Object loginSeesion = session.getAttribute("loginUserId");
            List<ArchiveUserCourse> archiveResultData = this.studentsService.getArchiveCourseInfos(loginSeesion.toString());
            mod.addAttribute("ArchiveUserCourseInfos",archiveResultData);

            return "student/studentcourse::archiveCourseInfos";//页面位置::fragment名称
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }



    /*
    * 查看作业详情
    * */
    @GetMapping("/student/homeworkdetail")
    public String homeworkDetail(String courseid,Model mod){
        List<Homework> homeworkdetails; //存储作业信息
            try {
                homeworkdetails = this.studentsService.courseHomeworkName(courseid);
                if(homeworkdetails.isEmpty()){
                    System.out.println("未获取到数据");
                    System.out.println(courseid);
                }
                mod.addAttribute("courseId",courseid);
                mod.addAttribute("homeworksdetails",homeworkdetails);
                return "student/stu-homeworkdetail";
            }catch (Exception e){
                e.printStackTrace();
                return "error";
            }
    }


    /*
    * 提交作业- 文件上次
    *
    * 注意此方法的参数MultipartFile uploadfile，这里的uploadfile必须要和提交文件表单name一致
    * */

    @PostMapping("/student/uploadhomework")
    public String uplocadHomeWork(MultipartFile uploadfile,Stuhomework stuhomework,StuhomeworkKey stuhomeworkKey,Homework homework,Students students) throws IOException {
        //上传时间参数需要处理
        stuhomework.setSubmitdate(new java.sql.Date(new java.util.Date().getTime()));

        //获取项目根目录路径
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) path = new File("");
        File upload = new File(path.getAbsolutePath(),"static/homeworkfiles/");
            if(!upload.exists()) upload.mkdirs();
        String classpath = upload.getAbsolutePath();
        System.out.println("文件上传目录为:"+classpath);

        //每个学生的作业以学号分目录存放
        String filePath = classpath+"/"+students.getStudentid()+"/";//每位学生一个目录，以学号命名
        File files = new File(filePath);
        try {
            if(this.studentsService.Uploadhomework(stuhomework,stuhomework.getHomeworkid()) > 0){
                if(!files.exists()){
                    files.mkdir();//若此学生第一次提交作业 则创建新的目录 以学号命名
                    System.out.println("创建目录成功");
                }
                uploadfile.transferTo(new File(filePath + uploadfile.getOriginalFilename()));//将文件存在对应学号目录下 ，文件名不修改
                System.out.println("文件上传成功");
            }
            else{
                System.out.println("插入数据失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("文件上次失败");
        }
        return "redirect:/student/studentcourse";
    }


}
