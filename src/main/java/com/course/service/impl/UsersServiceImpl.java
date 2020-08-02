package com.course.service.impl;

import com.course.mapper.UsersMapper;
import com.course.model.Users;
import com.course.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users userLogin(Users users) {
        return this.usersMapper.SelectUsersInfoByLogin(users);
    }

    @Override
    public int AddUser(Users users) {
        return this.usersMapper.insert(users);
    }


}
