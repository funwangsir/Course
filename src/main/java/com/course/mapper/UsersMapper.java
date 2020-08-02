package com.course.mapper;

import com.course.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper //指定这是一个操作数据库的Maper
@Repository
public interface UsersMapper {
    int deleteByPrimaryKey(String userid);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    @Select("select * from users where userId=#{userid} and userPassword=#{userpassword}")
    Users SelectUsersInfoByLogin(Users users);
}