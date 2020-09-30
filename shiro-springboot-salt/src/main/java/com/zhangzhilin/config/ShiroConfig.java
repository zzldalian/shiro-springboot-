package com.zhangzhilin.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //权限过滤
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("SecurityManager") DefaultWebSecurityManager securityManager){
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
        filterMap.put("/user/second","perms[user:second]");
        filterMap.put("/user/third","perms[user:third]");
        filterMap.put("/user/fourth","perms[user:fourth]");
        //所有用户都要认证
        filterMap.put("/user/*","authc");
        //设置注销授权
        filterMap.put("/logout", "logout");

        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录请求
        bean.setLoginUrl("/login");
        //设置未授权的请求
        bean.setUnauthorizedUrl("/Unauthorized");

        return bean;
    }
    
    
    //安全管理
    @Bean(name="SecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    //加密配置
    @Bean("credentialsMatcher")
    public HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //加密算法的名字，也可以设置MD5等其他加密算法名字       
        credentialsMatcher.setHashAlgorithmName("SHA-256");
        //加密次数
        credentialsMatcher.setHashIterations(20);
        //加密为哈希
        credentialsMatcher.setStoredCredentialsHexEncoded(true);

        return credentialsMatcher;
    }
    
    //开启缓存验证授权
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

//    @Bean
//    public SimpleCookie rememberMeCookie() {
//        // 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        // <!-- 记住我cookie生效时间30天 ,单位秒;-->
//        simpleCookie.setMaxAge(259200);
//        return simpleCookie;
//    }
    
//    @Bean
//    public CookieRememberMeManager rememberMeManager() {
//        logger.info("ShiroConfiguration.rememberMeManager()");
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
//        cookieRememberMeManager.setCipherKey(cipherKey);
//        return cookieRememberMeManager;
//    }
    
    @Bean(name="userRealm")
    public UserRealm userRealm(@Qualifier("credentialsMatcher") HashedCredentialsMatcher credentialsMatcher){
        
        UserRealm userRealm = new UserRealm();
        
        userRealm.setCredentialsMatcher(credentialsMatcher);
        
        return userRealm;
    }

    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
