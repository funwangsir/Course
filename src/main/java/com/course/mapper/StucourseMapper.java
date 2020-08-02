package com.course.mapper;

import com.course.model.Course;
import com.course.model.Stucourse;
import com.course.model.StucourseKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StucourseMapper {
    int deleteByPrimaryKey(StucourseKey key);

    int insert(Stucourse record);

    int insertSelective(Stucourse record);

    Stucourse selectByPrimaryKey(StucourseKey key);

    int updateByPrimaryKeySelective(Stucourse record);

    int updateByPrimaryKey(Stucourse record);

    //根据courseid删除数据
    int deleteByCourseid(String courseid);
}