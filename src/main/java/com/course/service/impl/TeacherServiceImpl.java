package com.course.service.impl;

import com.course.mapper.*;
import com.course.model.*;
import com.course.model.mymodels.ArchiveUserCourse;
import com.course.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeachersMapper teachersMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private CouhomeworkMapper couhomeworkMapper;
    @Autowired
    private TeacourseMapper teacourseMapper;
    @Autowired
    private StuhomeworkMapper stuhomeworkMapper;
    @Autowired
    private ArchiveMapper archiveMapper;

    @Override
    public void teacherRegister(Teachers teachers) {
        this.teachersMapper.insertSelective(teachers);
    }

    @Override
    public Teachers checkTeaidExisted(String teaid) {
        return this.teachersMapper.selectByPrimaryKey(teaid);
    }

    @Override
    public List<Teachers> stuCourseTeacherName(String courseid) {
        return this.teachersMapper.getTeacherNameByCurseId(courseid);
    }

    @Override
    public List<Homework> allCourseHomeworkName(String courseid) {
        return this.homeworkMapper.selecALLtHomeworkNameByCourseId(courseid);
    }

    @Override
    public List<Course> getTeachersCourses(String teaid) {
        return this.courseMapper.selectCoursesByTeaId(teaid);
    }

    @Override
    public String getMaxHomeworkId() {
        return this.homeworkMapper.selectMaxHomeworkByCourseId();
    }

    @Override
    public void AddHomeworks(Homework homework) {
        this.homeworkMapper.insert(homework);
    }

    @Override
    public void AddcourseidAndHomewordid(Couhomework couhomework) {
        this.couhomeworkMapper.insert(couhomework);
    }

    @Override
    public Course checkCourseidExisted(String courseid) {
        return this.courseMapper.selectByPrimaryKey(courseid);
    }

    @Override
    public void AddTeacherToCourse(Teacourse teacourse) {
        this.teacourseMapper.insert(teacourse);
    }

    @Override
    public String getMaxCourseId() {
        return courseMapper.selectMaxCourseId();
    }

    @Override
    public Teachers getTeachersInfos(String teaid) {
        return this.teachersMapper.selectByPrimaryKey(teaid);
    }

    @Override
    public List<Stuhomework> getStuhomeworkInfosByHomeworkId(String homeworkid) {
        return this.stuhomeworkMapper.selectStuhomeworkInfosByHomeworkId(homeworkid);
    }

    @Override
    public int addScore(String score,String stuid) {
        return this.stuhomeworkMapper.updateSetScoreByStuid(score,stuid);
    }

    @Override
    public List<ArchiveUserCourse> getArchiveCourseInfos(String teaid) {
        return this.archiveMapper.selectArchiveCourseInfoByTeaid(teaid);
    }

    @Override
    public int addArchiveCourse(Archive archive) {
        return this.archiveMapper.insert(archive);
    }

    @Override
    public int deleteArchiveCourse(Integer courseid){
        return this.archiveMapper.deleteByCourseid(courseid);
    }


}
