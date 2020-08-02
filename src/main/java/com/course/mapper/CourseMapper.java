package com.course.mapper;

import com.course.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper {
    int deleteByPrimaryKey(String courseid);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(String courseid);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    //通过学生id连表查询学生课程信息 只显示未在归档表的课程信息
    @Select("select * from course where courseId in(select courseId from stucourse where studentId = #{stuid}) and courseId not in (select courseId from archive where userid = #{stuid})")
    List<Course> selectCoursesByStuId(String stuid);

    //每次学生选课，在membercount中 +1
    @Update("update course set membercount = membercount+1 where courseId = #{couid}")
    int updateMembercountByCourseId(String couid);


    //通过老师id连表查询老师课程信息 只显示未在归档表的课程信息
    @Select("select * from course where courseId in (select courseId from teacourse where teacherId = #{teaid}) and courseId not in (select courseId from archive where userid = #{teaid})")
    List<Course> selectCoursesByTeaId(String teaid);

    //获取最大课程id
    @Select("select max(courseId) from course")
    String selectMaxCourseId();
}