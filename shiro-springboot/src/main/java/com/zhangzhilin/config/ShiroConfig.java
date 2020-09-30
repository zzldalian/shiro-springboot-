package com.zhangzhilin.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    
    //shiro三大对象
    
    //ShiroFilterFactoryBean 第三步
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);
        
        //添加shiro的内置过滤器 第四步
        /*
         anon:无需认证就可以访问
         authc:必须认证才能访问
         user:必须拥有 记住我功能才能用
         perms:拥有对某个资源的权限才能访问
         role:拥有某个角色权限才能访问
         roles:拥有某个角色权限才能访问
         */
        
//        filterMap.put("/user/add","authc");
//        filterMap.put("/user/update","authc");
        
        //拦截
        Map<String, String> filterMap = new LinkedHashMap<>();

        //授权
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");
        //所有用户都要认证
        filterMap.put("/user/*","authc");
        //设置注销授权
        filterMap.put("/logout", "logout");
        
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录请求
        bean.setLoginUrl("/toLogin");
        //设置未授权的请求
        bean.setUnauthorizedUrl("/Unauthorized");
        
        return bean;
    }
    
    //DefaultWebSecurityManager 第二步
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager; 
    }
    
    //Realm 第一步 创建UserRealm实体类 重写授权和认证的方法
    @Bean(name="userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }
    
}
