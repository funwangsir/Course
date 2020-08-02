package com.course.service;

import com.course.model.*;
import com.course.model.mymodels.ArchiveUserCourse;

import java.util.List;

public interface StudentsService {
    //学生注册
    void studentRegister(Students students);

    //验证注册账号是否存在
    Students checkStuidExisted(String stuid);

    //通过登录id查询学生相关信息
    Students getStudentsInfos(String stuid);

    //通过学生id连表查询所有课程信息
    List<Course> getStudentsCourses(String stuid);

    //验证课程id是否存在
    Course checkCourseidExisted(String courseid);

    //添加studentid和courseid到选课表
    void AddStudentToCourse(Stucourse stucourse);

    //通过studentid和courseid删除课程
    int DeleteStudentcourse(String stuid,String courseid);

    //通过课程id查询作业相关信息
    List<Homework> courseHomeworkName(String courseid);

    //上传作业，插入上传作业信息，修改作业状态为“已交”
    int Uploadhomework(Stuhomework stuhomework,String homeworkId);

    //通过studetid查询课程相关信息
    List<ArchiveUserCourse> getArchiveCourseInfos(String stuid);
}
