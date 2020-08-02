package com.course.service.impl;

import com.course.mapper.*;
import com.course.model.Couhomework;
import com.course.model.Course;
import com.course.model.Homework;
import com.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeacourseMapper teacourseMapper;

    @Autowired
    private StucourseMapper stucourseMapper;

    @Autowired
    private CouhomeworkMapper couhomeworkMapper;

    @Autowired
    private HomeworkMapper homeworkMapper;

    @Override
    public Course getCourseInfoByCourseid(String courseid) {
        return courseMapper.selectByPrimaryKey(courseid);
    }

    @Override
    public int updateCourse(Course course) {
        return this.courseMapper.updateByPrimaryKey(course);
    }

    @Override
    public void AddCourse(Course course) {
        this.courseMapper.insert(course);
    }

    @Override
    public List<Couhomework> selectHomeworkidByCourseid(String courseid) {
        return this.couhomeworkMapper.selectHomeworkidByCourseid(courseid);
    }

    @Override
    public int deleteHomework(String homeworkid) {
        return this.homeworkMapper.deleteByPrimaryKey(homeworkid);
    }

    @Override
    public int deleteCourse(String courseid) {
        //删除课程时，与课程相关的couhomework(作课程-作业关联表)、homework(作业表)、stucourse(课程-学生关联表)、teacourse(课程-老师关联表)均要删除

        try {
            this.courseMapper.deleteByPrimaryKey(courseid);//删除course表
            this.teacourseMapper.deleteByCourseid(courseid);//删除teacourse表
            this.stucourseMapper.deleteByCourseid(courseid);//删除stucourse表
            this.couhomeworkMapper.deleteByCourseid(courseid);//删除couhomework表

            return 1;
        }catch (Exception e){
            return 0;
        }
    }

}
