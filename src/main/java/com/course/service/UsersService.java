package com.course.service;

import com.course.model.Users;

public interface UsersService {
    Users userLogin(Users users);
    int AddUser(Users users);
}
