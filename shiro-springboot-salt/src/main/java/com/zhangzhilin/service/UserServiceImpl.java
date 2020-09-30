package com.zhangzhilin.service;


import com.zhangzhilin.mapper.UsersMapper;
import com.zhangzhilin.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    
    @Autowired
    UsersMapper usersMapper;
    
    @Override
    public void register(User user) {
        usersMapper.register(user);
    }

    public User queryUserByName(String name) {
        return usersMapper.queryUserByName(name);
    }
}
