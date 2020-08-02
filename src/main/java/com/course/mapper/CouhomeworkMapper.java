package com.course.mapper;

import com.course.model.Couhomework;
import com.course.model.CouhomeworkKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.ManagedBean;
import java.util.List;

@Mapper
@Repository
public interface CouhomeworkMapper {
    int deleteByPrimaryKey(CouhomeworkKey key);

    int insert(Couhomework record);

    int insertSelective(Couhomework record);

    Couhomework selectByPrimaryKey(CouhomeworkKey key);

    int updateByPrimaryKeySelective(Couhomework record);

    int updateByPrimaryKey(Couhomework record);

//    提交作业后，修改couhomework的作业提交状态
    @Update("update couhomework set submitState = '已交' where homeworkId = #{homeworkId} ")
    void updateHomeworkSubmitState(String homeworkId);

    //根据courseid删除数据
    int deleteByCourseid(String courseid);

    //通过课程id查询出所有作业id
    @Select("select homeworkId from couhomework where courseId = #{courseid}")
    List<Couhomework> selectHomeworkidByCourseid(String courseid);
}