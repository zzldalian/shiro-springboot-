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
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了=>授权,doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        
        //拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        //拿到User对象
        User currentUser = (User) subject.getPrincipal();
        //设置当前用户的权限
        info.addStringPermission(currentUser.getRole());
        
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证,doGetAuthenticationInfo");
        //第六步 做认证的方法
        //第七步 连接真实数据库
        //根据token里存的用户名，从数据库拿到用户数据
        
        
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        User user = userService.queryUserByName(userToken.getUsername());

        if (user.getName()==null){
            return null;//没有该用户
        }
        
        //密码认证,shiro来做
              
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
