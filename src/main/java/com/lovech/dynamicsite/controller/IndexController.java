package com.lovech.dynamicsite.controller;

import com.lovech.dynamicsite.entity.User;
import com.lovech.dynamicsite.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by luowq on 2017/8/3.
 */
@Controller
public class IndexController {
    @Autowired
   private FooService fooService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        User user=fooService.getUserById("1");
        model.addAttribute("user",user);
        return "index";
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String showLogin(){
        return "login";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam("username")String username, @RequestParam("password")String password,
                        HttpServletRequest request, RedirectAttributes redirectAttributes){
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
        }catch (AuthenticationException e){
            return "login";
        }
        return "home";
    }
}
