package com.course.mapper;

import com.course.model.Homework;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface HomeworkMapper {
    int deleteByPrimaryKey(String homeworkid);

    int insert(Homework record);

    int insertSelective(Homework record);

    Homework selectByPrimaryKey(String homeworkid);

    int updateByPrimaryKeySelective(Homework record);

    int updateByPrimaryKey(Homework record);

    //一个课程可能有多个作业 ，这里查询的时未提交的作业
    @Select("select * from homework where homeworkId in (select homeworkId from couhomework where courseId  = #{courseId} and submitState = '未交')")
    List<Homework> selectHomewordNameByCourseId(String courseId);

    //老师只要不主动删除作业，永远可以查看所有作业信息
    @Select("select * from homework where homeworkId in (select homeworkId from couhomework where courseId  = #{courseId})")
    List<Homework> selecALLtHomeworkNameByCourseId(String courseId);

    //查询当前课程id下的最大homeworkid值
    @Select("select max(homeworkId) from homework")
    String selectMaxHomeworkByCourseId();

}