package com.course.service;

import com.course.model.Couhomework;
import com.course.model.Course;

import java.util.List;

public interface CourseService {
    //添加课程信息到course表
    void AddCourse(Course course);

    //通过课程id查询课程信息
    Course getCourseInfoByCourseid(String courseid);

    //修改课程
    int updateCourse(Course course);

    //查询要删除课程的所有homeworkid
    List<Couhomework> selectHomeworkidByCourseid(String courseid);

    //通过homeworkid删除对应的所用homework信息
    int deleteHomework(String homeworkid);

    //删除课程
    int deleteCourse(String courseid);


}
