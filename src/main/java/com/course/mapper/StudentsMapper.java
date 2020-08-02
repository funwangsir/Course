package com.course.mapper;

import com.course.model.Students;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper //指定这是一个操作数据库的Maper
@Repository
public interface StudentsMapper {
    int deleteByPrimaryKey(String studentid);

    int insert(Students record);

    int insertSelective(Students record);

    Students selectByPrimaryKey(String studentid);

    int updateByPrimaryKeySelective(Students record);

    int updateByPrimaryKey(Students record);
}