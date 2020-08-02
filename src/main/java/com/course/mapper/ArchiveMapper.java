package com.course.mapper;

import com.course.model.Archive;
import com.course.model.mymodels.ArchiveUserCourse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface ArchiveMapper {
    int deleteByPrimaryKey(Integer archiveid);

    int insert(Archive record);

    int insertSelective(Archive record);

    Archive selectByPrimaryKey(Integer archiveid);

    int updateByPrimaryKeySelective(Archive record);

    int updateByPrimaryKey(Archive record);

    //通过教师id查询对应归档课程信息
    @Select("select c.courseName,c.memberCount,c.courseId,u.userId,u.userRole from course c,users u where u.userid = #{teaid} and c.courseId in (select courseId from archive where userid = #{teaid})")
    List<ArchiveUserCourse> selectArchiveCourseInfoByTeaid(String teaid);


    //通过学生id查询对应归档课程信息 此sql因为关联五张表，所以用了四次内连接等值查询 inner join on
    @Select("SELECT * FROM (SELECT archive.*,teachers.teacherName,course.courseName,users.userRole FROM archive INNER JOIN teacourse ON archive.userid = #{stuid} and archive.courseid = teacourse.courseId INNER JOIN teachers ON teacourse.teacherId = teachers.teacherId INNER JOIN course ON archive.courseid = course.courseId INNER JOIN users ON archive.userId = users.userId group by archive.archiveid) AS results ")
    List<ArchiveUserCourse> selectArchiveCourseInfoByStuid(String stuid);


    @Delete("delete from archive where courseid = #{courseid}")
    int deleteByCourseid(Integer courseid);
}