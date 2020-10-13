package com.zhangzhilin.config;

import com.zhangzhilin.pojo.User;
import com.zhangzhilin.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserRealm extends AuthorizingRealm {
    
    @Autowired
    UserService userService;
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了=>授权,doGetAuthorizationInfo");
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        //拿到User对象
        User currentUser = (User) subject.getPrincipal();
       
        //设置当前用户的权限
        //info.addStringPermission(currentUser.getRole());
        
        
        String[] split = currentUser.getRole().trim().split("-");
        System.out.println(split);
        List<String> list = Arrays.asList(split);
        info.addStringPermissions(list);
        return info;
    }
    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)throws AuthenticationException {
        System.out.println("执行了=>认证,doGetAuthenticationInfo");
        
        //根据用户名字去数据库查询
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        
        //这里表示的是数据库查询
        User user = userService.queryUserByName(username);
        
        //把数据库的密码和用户登录用的明文密码加密之后的结果进行对比,进行认证
        
        //第二个参数传的是查询数据库之后的数据库里的密码,而不是用户登录的明文密码
        if (user == null) {
            return null;
        }
        
        //转换盐值数据类型
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        //返回认证结果
        return new SimpleAuthenticationInfo(user,user.getPassword(),credentialsSalt,"");
    }
}
