package com.zhangzhilin;

import com.zhangzhilin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.apache.shiro.util.ByteSource.Util.bytes;

@SpringBootTest
class ShiroSpringbootSaltApplicationTests {
    
    @Autowired
    UserService userService;
    
    @Test
    void contextLoads() {
//        String name = "张三";
//        User user = userService.queryUserByName(name);
//        System.out.println(user);
//        
//        System.out.println(user.getSalt());
//        String salt = RandomStringUtils.randomAlphabetic(20);
//        ByteSource.Util.bytes(user.getSalt());
//        System.out.println(ByteSource.Util.bytes(user.getSalt()));
        
    }

}
