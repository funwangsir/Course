package com.course.service.impl;

import com.course.mapper.*;
import com.course.model.*;
import com.course.model.mymodels.ArchiveUserCourse;
import com.course.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentsServiceImpl implements StudentsService {
    @Autowired
    private StudentsMapper studentsMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StucourseMapper stucourseMapper;
    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private StuhomeworkMapper stuhomeworkMapper;
    @Autowired
    private CouhomeworkMapper couhomeworkMapper;
    @Autowired
    private ArchiveMapper archiveMapper;
    @Override
    public void studentRegister(Students students) {
        this.studentsMapper.insertSelective(students);
    }

    @Override
    public Students checkStuidExisted(String stuid) {
        return this.studentsMapper.selectByPrimaryKey(stuid);
    }

    @Override
    public Students getStudentsInfos(String stuid) {
        return this.studentsMapper.selectByPrimaryKey(stuid);
    }

    @Override
    public List<Course> getStudentsCourses(String stuid) {
        return this.courseMapper.selectCoursesByStuId(stuid);
    }

    @Override
    public Course checkCourseidExisted(String courseid) {
        return this.courseMapper.selectByPrimaryKey(courseid);
    }

    @Override
    public void AddStudentToCourse(Stucourse stucourse) {
        this.stucourseMapper.insert(stucourse);//插入数据到选课表
        this.courseMapper.updateMembercountByCourseId(stucourse.getCourseid());//通过课程id在选课人数上 +1
    }
    @Override
    public List<Homework> courseHomeworkName(String courseid) {
        return this.homeworkMapper.selectHomewordNameByCourseId(courseid);
    }

    @Override
    public int Uploadhomework(Stuhomework stuhomework,String homeworkId) {
        this.couhomeworkMapper.updateHomeworkSubmitState(homeworkId); //在couhomework修改作业提交状态
        return this.stuhomeworkMapper.insert(stuhomework);//将提交的作业信息存入stuhomework表
    }

    @Override
    public List<ArchiveUserCourse> getArchiveCourseInfos(String stuid) {
        return this.archiveMapper.selectArchiveCourseInfoByStuid(stuid);
    }

    @Override
    public int DeleteStudentcourse(String stuid, String courseid) {
        try {
            Stucourse stucourse = new Stucourse();
            stucourse.setStudentid(stuid);
            stucourse.setCourseid(courseid);
            //删除选课表信息
            this.stucourseMapper.deleteByPrimaryKey(stucourse);
            //成员数减一

        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
