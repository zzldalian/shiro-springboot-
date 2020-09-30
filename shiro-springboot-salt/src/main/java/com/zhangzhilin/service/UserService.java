package com.zhangzhilin.service;

import com.zhangzhilin.pojo.User;

public interface UserService {
    
    public void register(User user);

    public User queryUserByName(String name);
}
