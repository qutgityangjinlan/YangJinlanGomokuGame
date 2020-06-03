package com.yjl.controller;

import com.yjl.pojo.User;
import com.yjl.pojo.UserLog;
import com.yjl.service.UserLogService;
import com.yjl.service.UserService;
import com.yjl.utils.DateUtil;
import com.yjl.utils.WordDefined;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//登录注册控制器
@Controller
@RequestMapping(value = "/user")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserLogService userLogService;

    //	登陆
    //@ModelAttribute注解用于将方法的参数或方法的返回值绑定到指定的模型属性上，并返回给Web视图
    @RequestMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request, RedirectAttributes redirectAttributes, WordDefined defined,Model model) {
        //获取当前的subject，调用SecurityUtils.getSubject()方法
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserid(), user.getPassword());
        try {
            DateUtil dateUtil = new DateUtil();
            String ip = request.getRemoteAddr();
            subject.login(token);
            User user2 = new User();
            user2.setUserid(user.getUserid());
            user2.setLasttime(new DateUtil().getDateformat());
//			修改最近一次登陆日志
            userService.updateUser(user2);
            UserLog userLog = new UserLog();
            userLog.setUserid(user.getUserid());
            userLog.setTime(dateUtil.getDateformat());
            userLog.setType("登录");
            userLog.setDetail("用户登录");
            userLog.setIp(ip);
//			插入登陆日志
            userLogService.insertLog(userLog);
            int lognumber = userLogService.selectLogcountfromuserid(user.getUserid());
            request.getSession().setAttribute("userid", user.getUserid());
            request.getSession().setAttribute("login_status", true);
            request.getSession().setAttribute("lognumber", lognumber);
            redirectAttributes.addFlashAttribute("message", defined.LOGIN_SUCCESS);
            return "index";

        }catch (IncorrectCredentialsException e ){
            System.out.println("密码错误~");
            model.addAttribute("submit","密码错误!");
            request.getSession().setAttribute("pwdInfo", "用户密码错误");
            request.getSession().setAttribute("pwdError", defined.LOGIN_PASSWORD_ERROR);
            e.printStackTrace();
            return "login";
        }
        catch (Exception e) {
            System.out.println("登录失败");
            request.getSession().setAttribute("userError", defined.LOGIN_USERID_ERROR);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("errorInfo", "用户密码错误");
            e.printStackTrace();
            return "login";
        }
    }


    //	退出
    @RequestMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();
        String result = "logout1";
        subject.logout();
        return "login";

    }

    @RequestMapping("/toregister")
    public String toregister(User user, RedirectAttributes redirectAttributes, Model model) {
        return "register";
    }

    @RequestMapping("/checkLogin")
    @ResponseBody
    public String  checkLogin(@RequestParam("userid") String userid, HttpServletResponse response) {
        User user3 =userService.selectUserByUserId(userid);
        if(user3 == null){
            return "{\"msg\":\"false\"}";
        }
        return "{\"msg\":\"true\"}";
    }

    @RequestMapping("/checkLoginPwd")
    @ResponseBody
    public String  checkLoginPwd(@RequestParam("userid") String userid, @RequestParam("password") String password) {
        User user3 =userService.selectUserByUserId(userid);
        String pwd = user3.getPassword();
        if(!pwd.equals(password)){
            return "{\"msg\":\"false\"}";
        }
        return "{\"msg\":\"true\"}";
    }

    @RequestMapping("/checkRegister")
    @ResponseBody
    public String  checkRegister(@RequestParam("userid") String userid, HttpServletResponse response) {
        User user3 =userService.selectUserByUserId(userid);
       if(user3 != null){
           return "{\"msg\":\"false\"}";
       }
        return "{\"msg\":\"true\"}";
    }


    @RequestMapping("/register")
    public String RegisterableService(User user, RedirectAttributes redirectAttributes,  Model model) {

        User user1 = new User();
        user1.setUserid(user.getUserid());
        user1.setPassword(user.getPassword());
        user1.setFirsttime(new DateUtil().getDateformat());
        if (userService.insertUser(user1)) {
            return "login";
        } else {
            return "register";
        }


    }
}
