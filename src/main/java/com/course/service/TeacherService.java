package com.course.service;

import com.course.model.*;
import com.course.model.mymodels.ArchiveUserCourse;

import java.util.List;

public interface TeacherService {
    //教师注册
    void teacherRegister(Teachers teachers);

    //检查教师账号是否存在
    Teachers checkTeaidExisted(String teaid);

    //通过课程id获取学生所选课程的老师姓名
    List<Teachers> stuCourseTeacherName(String courseid);

    //通过课程id查询所有作业相关信息
    List<Homework> allCourseHomeworkName(String courseid);

    //通过老师id连表查询所有课程信息
    List<Course> getTeachersCourses(String teaid);

    //查询出课程表最大homeworkid
    String getMaxHomeworkId();

    //将添加的作业信息插入到homework表
    void AddHomeworks(Homework homework);

    //将课程id和作业id插入couhomework表中
    void AddcourseidAndHomewordid(Couhomework couhomework);

    //验证课程id是否存在
    Course checkCourseidExisted(String courseid);

    //添加teacherid和courseid到选课表
    void AddTeacherToCourse(Teacourse teacourse);

    //获取course表最大courseid
    String getMaxCourseId();

    //通过老师id获取所有老师信息
    Teachers getTeachersInfos(String teaid);

    List<Stuhomework> getStuhomeworkInfosByHomeworkId(String homeworkid);

    int addScore(String score,String stuid);

    //通过教师id查询归档课程信息
    List<ArchiveUserCourse> getArchiveCourseInfos(String teaid);

    //添加归档课程到归档表
    int addArchiveCourse(Archive archive);

    //恢复归档数据到课程列表
    int deleteArchiveCourse(Integer courseid);
}
