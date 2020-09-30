package com.zhangzhilin.mapper;

import com.zhangzhilin.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    
    public User queryUserByName(String name);
    
    
    
}
