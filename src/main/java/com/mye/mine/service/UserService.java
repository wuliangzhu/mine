package com.mye.mine.service;

import com.mye.mine.entity.User;
import com.mye.mine.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;


    public User getUserById(int id) {
        return this.userMapper.getUserById(id);
    }

    public int insertUser(User user) {
        return this.userMapper.insertUser(user);
    }
}
