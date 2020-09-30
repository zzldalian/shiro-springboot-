package com.zhangzhilin.mapper;

import com.zhangzhilin.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersMapper {
    
    public void register(User user);
    
    public User queryUserByName(String name);
    
}
