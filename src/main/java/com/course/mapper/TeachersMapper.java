package com.course.mapper;

import com.course.model.Teachers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper //指定这是一个操作数据库的Maper
@Repository
public interface TeachersMapper {
    int deleteByPrimaryKey(String teacherid);

    int insert(Teachers record);

    int insertSelective(Teachers record);

    Teachers selectByPrimaryKey(String teacherid);

    int updateByPrimaryKeySelective(Teachers record);

    int updateByPrimaryKey(Teachers record);

    @Select("select * from teachers where teacherId in (select teacherId from teacourse where courseId = #{courseId})")
    List<Teachers> getTeacherNameByCurseId(String courseId);
}