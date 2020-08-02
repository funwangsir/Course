package com.course.mapper;

import com.course.model.Teacourse;
import com.course.model.TeacourseKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Mapper
@Repository
public interface TeacourseMapper {
    int deleteByPrimaryKey(TeacourseKey key);

    int insert(Teacourse record);

    int insertSelective(Teacourse record);

    Teacourse selectByPrimaryKey(TeacourseKey key);

    int updateByPrimaryKeySelective(Teacourse record);

    int updateByPrimaryKey(Teacourse record);

    //根据courseid删除数据
    int deleteByCourseid(String courseid);
}