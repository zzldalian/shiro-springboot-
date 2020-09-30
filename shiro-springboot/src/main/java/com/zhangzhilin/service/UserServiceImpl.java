package com.zhangzhilin.service;

import com.zhangzhilin.mapper.UserMapper;
import com.zhangzhilin.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    UserMapper userMapper;
    
    @Override
    public User queryUserByName(String name) {
        User user = userMapper.queryUserByName(name);
        return user;
    }
}
