package com.zhangzhilin.controller;

import com.zhangzhilin.pojo.User;
import com.zhangzhilin.service.UserServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    
    @Autowired
    UserServiceImpl userService;
    
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    
    @RequestMapping("/add")
    public String add(){
        return "add";
    }

    @RequestMapping("/login")
    public String tologin(){
        return "login";
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/register")
    public String register(String name, String password1, String password2, Model model){
        
        if ( name!=null && password1!=null && password2!=null){
            if (password1.equals(password2)){
                User user = new User();
                String salt = RandomStringUtils.randomAlphabetic(20);
                SimpleHash simpleHash = new SimpleHash("SHA-256",password1, salt, 20);
                user.setName(name);
                user.setPassword(simpleHash.toString());
                user.setSalt(salt);

                userService.register(user);
                model.addAttribute("msg","注册成功");
            } 
            if (!password1.equals(password2)){
                model.addAttribute("msg","请再次确认密码");
            }
        }
        if ( name==null || password1==null || password2==null || "".equals(name) || "".equals(password1) || "".equals(password2)){
            model.addAttribute("msg","注册信息不能为空");
        }
        return "add";
    }
    
    @RequestMapping("/dologin")
    public String login(String name,String password,Model model){

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        try {
            subject.login(token);
            return "first";
        } catch (UnknownAccountException e) { //用户名不存在
            model.addAttribute("msg","用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e) { //密码不存在
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }
    
    @RequestMapping("/first")
    public String toFirst(){
        return "first";
    }
    
    @RequestMapping("user/second")
    public String toSecond(){
        return "user/second";
    }

    @RequestMapping("user/third")
    public String toThird(){
        return "user/third";
    }

    @RequestMapping("user/fourth")
    public String tofourth(){
        return "user/fourth";
    }

    @RequestMapping("/Unauthorized")
    @ResponseBody
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }
}
