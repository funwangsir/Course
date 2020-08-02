package com.course;


import com.course.service.StudentsService;

import com.course.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;


@SpringBootTest
class MaincourseApplicationTests {

    @Autowired
    private StudentsService studentsService;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    DataSource dataSource;
/*
    @Test
    void contextLoads() throws SQLException {
        System.out.println("测试："+dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println("测试："+connection);
        connection.close();
     }
    */
/*
    @Test
    void getCourses(){
        List<Course> list = this.studentsService.getStudentsCourses("18290528646");

        for(int i = 0; i <list.size();i++){
            System.out.println(list.get(i).getCoursename());
        }

    }
*/
/*
    @Test
    void getTeacherNameAndHomeworkName(){
        Map<String,String> teachersName = new HashMap<>();;//存储教师姓名,关联courseid
        List<String> homeworkName = new ArrayList<>(); //存储作业信息，关联courseid
        try{
            List<Course> courses = this.studentsService.getStudentsCourses("18290528646");

            //根据课程id 获取教师姓名集合 和 课程作业集合
            String courseid = null;
            for(int i=0;i<courses.size();i++){
                courseid = courses.get(i).getCourseid();

                System.out.println(courseid);

                if(!this.teacherService.stuCourseTeacherName(courseid).equals(null)){
                   // System.out.println(this.teacherService.stuCourseTeacherName(courseid));
                    teachersName.put(courseid,this.teacherService.stuCourseTeacherName(courseid));
                }else{
                    System.out.println("此课程无教师，请检查数据库信息");
                }
                if(!this.homeworkService.courseHomeworkName(courseid).isEmpty()){
                    List<Homework> Homeworks = this.homeworkService.courseHomeworkName(courseid);
                    for(int j = 0;j<Homeworks.size();j++){
                        //System.out.println(Homeworks.get(j).getHomeworkname());
                        homeworkName.add(courseid);
                        homeworkName.add(Homeworks.get(j).getHomeworkname());
                    }
                }else{
                    System.out.println("课程号为"+courseid+"的课程无家庭作业");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("测试失败");
        }
        for (Map.Entry<String, String> entry : teachersName.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        for(int j = 0;j<homeworkName.size();j++){
            System.out.println(homeworkName.get(j));
        }
    }

 */

/*
    @Test
    public void getStaicPath() throws FileNotFoundException {
    String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        System.out.println(path);
//    File path = new File(ResourceUtils.getURL("classpath:").getPath());
//        System.out.println("path:"+path.getAbsolutePath());
    }
*/
}
