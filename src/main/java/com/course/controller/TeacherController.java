package com.course.controller;

import com.course.model.*;

import com.course.model.mymodels.ArchiveUserCourse;
import com.course.service.CourseService;
import com.course.service.StudentsService;
import com.course.service.TeacherService;
import com.course.service.UsersService;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.http.HttpRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentsService studentsService;


    @PostMapping("/teaRegister")
    public String teaRegister(Teachers teachers, HttpServletRequest hslr, Model mod){
        HttpSession session = hslr.getSession();
        try {
            //将数据插入到teachers表
            this.teacherService.teacherRegister(teachers);

            //将数据插入到users表
            Users us = new Users();
            us.setUserid(teachers.getTeacherid());
            us.setUserpassword(teachers.getTeacherpassword());
            us.setUsername(teachers.getTeachername());
            us.setUserschool(teachers.getTeacherschool());
            us.setUserrole(teachers.getRole());
            this.usersService.AddUser(us);

            session.setAttribute("loginUserId",teachers.getTeacherid());
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
     *
     * ajax获取数据信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkTeaidExisted" , method = RequestMethod.POST)
    public String checkUserIdExisted(@RequestBody String teaId){
        try {
            if(teaId.equals("邮箱/手机"))
                return "请输入账号";
            else if(ObjectUtils.isEmpty(this.teacherService.checkTeaidExisted(teaId)))
                return "检验通过";
            return "该账号已存在";


        }catch (Exception e){
            e.printStackTrace();
            System.out.println("检验出错");
            return "检验出错,请联系管理员";
        }
    }


    /*
     * 访问首页(teacher/teachercourse)时的登录验证,如果已经登录则绑定登录用户信息
     * */
    @RequestMapping("/teacher/teachercourse")
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
            courses = this.teacherService.getTeachersCourses(session.getAttribute("loginUserId").toString());


            if(courses.isEmpty()){
                System.out.println("未查询到该老师加入的课程");
            }

            //根据课程id 获取教师姓名集合 和 课程作业集合
            try{
                String courseid = null;

                for(int i=0;i<courses.size();i++){
                    //通过遍历将三个数据集整合到一个Map（resultDatas）
                    courseid = courses.get(i).getCourseid();

                    m = new HashMap<String, Object>();
                    m.put("courseid", courseid);
                    m.put("coursename", courses.get(i).getCoursename());
                    m.put("courseclass", courses.get(i).getCourseclass());
                    m.put("courseyear", courses.get(i).getCourseyear());
                    m.put("courseterm", courses.get(i).getCourseterm());
                    m.put("coursemembercount",courses.get(i).getMembercount());
                    if(!this.teacherService.allCourseHomeworkName(courseid).isEmpty()){
                        List<Homework> Homeworks = this.teacherService.allCourseHomeworkName(courseid);
                        String temp = "";
                        for(int j = 0;j<Homeworks.size();j++){
                            temp = temp + Homeworks.get(j).getHomeworkname()+"<br>";
                            m.put("homeworknames", temp);
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
            return "teacher/teachercourse";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }

    }



    /*
    * 实现添加作业
    * */
    @RequestMapping("/teacher/teaAddhomeworks")
    public String TeacherAddHomeworks(String courseid,Homework homework,String deadlinetime) throws ParseException {
        //时间类型需要单独设置转化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
        Date deadLine = simpleDateFormat.parse(deadlinetime);

        homework.setDeadline(deadLine);
        homework.setPublishdate(new java.sql.Date(new java.util.Date().getTime()));//获取当前时间未上传时间

        try {
            int homeworkId = Integer.parseInt(this.teacherService.getMaxHomeworkId())+1;
            homework.setHomeworkid(homeworkId+"");

            Couhomework couhomework = new Couhomework();
            couhomework.setCourseid(courseid);
            couhomework.setHomeworkid(homeworkId+"");
            couhomework.setSubmitstate("未交");
            //执行插入到homework表
            this.teacherService.AddHomeworks(homework);
            //执行插入到couhomework表
            this.teacherService.AddcourseidAndHomewordid(couhomework);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "redirect:/teacher/teachercourse";
    }



    /**
     *ajax 老师加入课程
     *
     */
    @ResponseBody
    @RequestMapping(value = "/teacher/joinCourse" , method = RequestMethod.POST)
    public String joinCourseByCourseId(@RequestBody String courseId,Teacourse teacourse,HttpServletRequest hslr){
        try {
            System.out.println("当前courseid为："+courseId);
            if(courseId.equals(null)){
                return "请输入课程";
            }else{
                if(ObjectUtils.isEmpty(this.teacherService.checkCourseidExisted(courseId))){
                    return "未找到课程，请检查加入课程验证码是否输入正确";
                }else {
                    HttpSession se = hslr.getSession();
                    Object loginSessionid = se.getAttribute("loginUserId");

                    teacourse.setTeacherid(loginSessionid.toString());
                    teacourse.setCourseid(courseId);
                    teacourse.setCoursestate("已创建");
                    this.teacherService.AddTeacherToCourse(teacourse);

                    return "添加成功";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("验证异常");
            return "error";
        }
    }


    /*
    * 创建课程
    * */
    @ResponseBody
    @RequestMapping("/teacher/createcourse")
    public String createCourse(@RequestBody Course course){
        try {
            int courseId = Integer.parseInt(this.teacherService.getMaxCourseId())+1;//主键+1
            course.setCourseid(courseId+"");
            course.setMembercount(0);//成员不能为空，默认为0
            this.courseService.AddCourse(course);
            return "添加成功,创建的课程号为："+course.getCourseid()+"请输入该课程号加入课程！";
        }catch (Exception e){
            e.printStackTrace();
            return "添加失败";
        }
    }


    /*
    *获取已归档课程信息
    * */
    @RequestMapping("/teacher/getArchiveInfos")
    public String getArchiveInfos(HttpServletRequest hslr,Model mod){
        try{
            HttpSession session = hslr.getSession();
            Object loginSeesion = session.getAttribute("loginUserId");
            List<ArchiveUserCourse> archiveResultData = this.teacherService.getArchiveCourseInfos(loginSeesion.toString());
            for(int i = 0;i<archiveResultData.size();i++){
                if(archiveResultData.get(i).getUserrole() == "教师"){
                    System.out.println("此条数据不是学生的");

                }
            }
            mod.addAttribute("ArchiveUserCourseInfos",archiveResultData);

            return "teacher/teachercourse::archiveCourseInfos";//页面位置::fragment名称
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }



    /*
    * ajax获取要修改课程的信息
    * */

    @ResponseBody
    @RequestMapping("/teacher/getUpdateCourseInfoBycourseid")
    public List<Course> getUpCourseInfos(@RequestBody String courseid){
        List<Course> result = new ArrayList<>();
        try {
            result.add(this.courseService.getCourseInfoByCourseid(courseid));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /*
     * 修改课程
     * */
    @ResponseBody
    @RequestMapping("/teacher/updatecourse")
    public String updateCourse(@RequestBody Course course){
        try {
            course.setMembercount(0);//成员不能为空，默认为0
            System.out.println(course);
            this.courseService.updateCourse(course);
            return "修改成功";
        }catch (Exception e){
            e.printStackTrace();
            return "修改失败";
        }
    }

    /*
    * 实现删除作业
    * */
    @ResponseBody
    @RequestMapping("/teacher/deletecoursebyid")
    public String deleteCourse(@RequestBody String courseid){
        try{
            //根据courseid查询所有homeworkid并在homework表删除对应所有信息
            List<Couhomework> homeworkidforCouhomework = this.courseService.selectHomeworkidByCourseid(courseid);
            for(int i=0;i<homeworkidforCouhomework.size();i++){
                this.courseService.deleteHomework(homeworkidforCouhomework.get(i).getHomeworkid());
            }
            //注意前后逻辑，如果先删除课程相关信息，可能就查询不到homesorkid
            //删除课程时，与课程相关的couhomework(作课程-作业关联表)、homework、stucourse(课程-学生关联表)、teacourse(课程-老师关联表)均要删除
            this.courseService.deleteCourse(courseid);//删除课程表及其关联表
            return "删除成功";
        }catch (Exception e){
            e.printStackTrace();
            return "删除失败";
        }
    }


    /*
    * 通过教师id查询教师个人信息
    * */
    @RequestMapping("/teacher/teaMyinfo")
    public String getTeachersInfos(Model mod,HttpServletRequest hslr){

        try{
            HttpSession se = hslr.getSession();
            Object loginSessionid = se.getAttribute("loginUserId");

            if(ObjectUtils.isEmpty(loginSessionid)){
                return "login";
            }else{
                //通过登录账号查询的所有数据返回给View
                Teachers teachers = this.teacherService.getTeachersInfos(loginSessionid.toString());
                mod.addAttribute("teacherInfos",teachers);
                return "teacher/tea-info";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }


    /*
     * 查看作业详情
     * */
    @GetMapping("/teacher/homeworkdetail")
    public String homeworkDetail(String courseid,String coursemembercount,Model mod){
        List<Homework> homeworkdetails; //存储作业所有信息
        try {
            homeworkdetails = this.teacherService.allCourseHomeworkName(courseid);
            if(homeworkdetails.isEmpty()){
                //Thread.currentThread() .getStackTrace()[1].getMethodName()此方法可以获取当前方法名
                System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName()+"未获取到数据");
                System.out.println(courseid);
            }
            mod.addAttribute("courseId",courseid);
            mod.addAttribute("membercount",coursemembercount);
            mod.addAttribute("homeworksdetails",homeworkdetails);
            return "teacher/tea-homeworkdetail";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }




    /*
    * 获取已提交作业列表到tea-showstudenthomeworklist
    * */
    @GetMapping("/teacher/teaGetHomeworkList")
    public String getStuHomeworkList(String courseid,String homeworkid,Model mod){
        Students students;//学生信息
//        Stuhomework stuhomeworks ;//作业信息
        List <Stuhomework> stuhomeworks ;//作业信息
        List<Map<String, Object>> resultDatas = new ArrayList<Map<String, Object>>();//整合数据
        Map<String, Object> m = new HashMap<String, Object>();//第二层的map
        try{
            //获取作业相关信息
            stuhomeworks = this.teacherService.getStuhomeworkInfosByHomeworkId(homeworkid);
            for(int j = 0; j < stuhomeworks.size(); j++){
                //通过作业表的学生id查询学生表的学生信息
                students = this.studentsService.getStudentsInfos(stuhomeworks.get(j).getStudentid());
                m = new HashMap<String, Object>();

                m.put("stuid",students.getStudentid());//学生账号 stuid
                m.put("stunumber",students.getStudentnumber());//学号
                m.put("stuname",students.getStudentname());//姓名

                m.put("submitdate",stuhomeworks.get(j).getSubmitdate());//提交时间
                m.put("message",stuhomeworks.get(j).getMessage());//提交留言
                m.put("score",stuhomeworks.get(j).getScore());//成绩

                resultDatas.add(m);
            }

            mod.addAttribute("stuhomeworklist",resultDatas);
            return "teacher/tea-showstudenthomeworklist";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }



    /*
    * 读取word数据
    * */
    @RequestMapping("/teacher/teaGetstuword")
    public String getStuWord(String stuid,String stunumber,Model mod){
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
                if(!path.exists()) path = new File("");
            File upload = new File(path.getAbsolutePath(),"static/homeworkfiles/");
                if(!upload.exists()) upload.mkdirs();
            String classpath = upload.getAbsolutePath();//文件根目录
            System.out.println("文件上传目录:"+classpath);


            File getFile_docx = new File(classpath+"/"+stuid+"/"+stunumber+".docx");//docx文件路径
            File getFile_doc = new File(classpath+"/"+stuid+"/"+stunumber+".doc");//doc文件路径
            File getImgFile = new File(classpath+"/"+stuid+"/image/");//html图片路径
            if(!getImgFile.exists()) getImgFile.mkdir();
            File getHtmlFile = new File(classpath+"/"+stuid+"/"+stunumber+".html");//html文件路径
            OutputStreamWriter outputStreamWriter = null;
            try {
                if(getFile_docx.exists()){
                    XWPFDocument document = new XWPFDocument(new FileInputStream(getFile_docx));
                    XHTMLOptions options = XHTMLOptions.create();
                    // 存放图片的文件夹
                    options.setExtractor(new FileImageExtractor(getImgFile));
                    // html中图片的路径
                    options.URIResolver(new BasicURIResolver("image"));
                    outputStreamWriter = new OutputStreamWriter(new FileOutputStream(getHtmlFile), "utf-8");
                    XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
                    xhtmlConverter.convert(document, outputStreamWriter, options);

                }else if(getFile_doc.exists()){
                    //处理doc
                    FileInputStream fis_doc = new FileInputStream(getFile_doc);
                    HWPFDocument hwpfd = new HWPFDocument(fis_doc);
                    WordExtractor wordExtractor = new WordExtractor(hwpfd);
                    String[] paragraph = wordExtractor.getParagraphText();
                    for (int i = 0; i < paragraph.length; i++) {
                        System.out.println(paragraph[i]);
                        mod.addAttribute("wordContent",paragraph[i]+="");
                    }
                    fis_doc.close();
                }else{
                    System.out.println("此文件不是doc或者docx文件");
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("打开文件失败");
            }
            mod.addAttribute("stuid",stuid);
            mod.addAttribute("stunumber",stunumber);
            mod.addAttribute("htmlpath","/homeworkfiles/"+stuid+"/"+stunumber+".html");
            return "teacher/tea-stuword";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    /*
    * 提交成绩
    * */

    @RequestMapping("/teacher/addScore")
    public String addScore(String stunumber,String stuid,Stuhomework stuhomework,Model mod){
        try {
            if(this.teacherService.addScore(stuhomework.getScore(),stuid) > 0){
                mod.addAttribute("addMessage","成绩已保存");
                mod.addAttribute("score",stuhomework.getScore());
                mod.addAttribute("htmlpath","/homeworkfiles/"+stuid+"/"+stunumber+".html");
            }else{
                System.out.println("添加失败");
            }
            return "teacher/tea-stuword";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

}
