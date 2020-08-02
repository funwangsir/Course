package com.course.mapper;

import com.course.model.Stuhomework;
import com.course.model.StuhomeworkKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StuhomeworkMapper {
    int deleteByPrimaryKey(StuhomeworkKey key);

    int insert(Stuhomework record);

    int insertSelective(Stuhomework record);

    Stuhomework selectByPrimaryKey(StuhomeworkKey key);

    int updateByPrimaryKeySelective(Stuhomework record);

    int updateByPrimaryKey(Stuhomework record);

    @Select("select * from stuhomework where homeworkid = #{homeworkid}")
    List<Stuhomework> selectStuhomeworkInfosByHomeworkId(String homeworkid);

    @Update("update stuhomework set score = #{score} where studentId = #{stuid}")
    int updateSetScoreByStuid(String score,String stuid);
}