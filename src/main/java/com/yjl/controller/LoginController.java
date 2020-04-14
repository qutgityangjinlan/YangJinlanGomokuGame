package com.yjl.controller;

import com.yjl.pojo.User;
import com.yjl.pojo.UserLog;
import com.yjl.service.impl.UserLogServiceImpl;
import com.yjl.service.impl.UserServiceImpl;
import com.yjl.utils.DateUtil;
import com.yjl.utils.WordDefined;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//登录注册控制器
@Controller
@RequestMapping(value="/user")
public class LoginController {
	@Resource
	private UserServiceImpl userServiceImpl;
	@Resource
	private UserLogServiceImpl userLogServiceImpl;
	//	登陆
	//@ModelAttribute注解用于将方法的参数或方法的返回值绑定到指定的模型属性上，并返回给Web视图
	@RequestMapping("/login")
	public String login(@ModelAttribute  User user,HttpServletRequest request, HttpSession session,RedirectAttributes redirectAttributes, WordDefined defined){
		Subject subject= SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUserid(),user.getPassword());
		try
		{
			DateUtil dateUtil=new DateUtil();
			String ip=request.getRemoteAddr();
			subject.login(token);
			User user2=new User();
			user2.setUserid(user.getUserid());
			user2.setLasttime(new DateUtil().getDateformat());
//			修改最近一次登陆日志
			userServiceImpl.updateUser(user2);
			UserLog userLog=new UserLog();
			userLog.setUserid(user.getUserid());
			userLog.setTime(dateUtil.getDateformat());
			userLog.setType("登录");
			userLog.setDetail("用户登录");
			userLog.setIp(ip);
//			插入登陆日志
			userLogServiceImpl.insertLog(userLog);
			int lognumber=userLogServiceImpl.selectLogcountfromuserid(user.getUserid());
			request.getSession().setAttribute("userid", user.getUserid());
			request.getSession().setAttribute("login_status", true);
			request.getSession().setAttribute("lognumber", lognumber);
			redirectAttributes.addFlashAttribute("message",defined.LOGIN_SUCCESS);
			return "index";

		}catch (Exception e) {
			System.out.println("登录失败");
			redirectAttributes.addFlashAttribute("error", defined.LOGIN_PASSWORD_ERROR);
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("errorInfo", "用户密码错误");
			e.printStackTrace();
			return "login";
		}
	}


	//	退出
	@RequestMapping("/logout")
	public String logout(HttpSession session,RedirectAttributes redirectAttributes)
	{
		Subject subject=SecurityUtils.getSubject();
		String result="logout1";
		subject.logout();
		return "login";

	}
	@RequestMapping("/toregister")
	public String toregister(User user, RedirectAttributes redirectAttributes, Model model)

	{
		User user2 = userServiceImpl.selectUserByUserId(user.getUserid());
		if (user2 != null){
			model.addAttribute("registerError","用户名重复！");

		}
		return "register";
	}

	@RequestMapping("/register")
	public String RegisterableService(User user, RedirectAttributes redirectAttributes, Model model)
	{

		User user1=new User();
		user1.setUserid(user.getUserid());
		user1.setPassword(user.getPassword());
		user1.setFirsttime(new DateUtil().getDateformat());

		if(userServiceImpl.insertUser(user1))
		{
			return "login";
		}
		else
		{
			return "register";
		}

	}
}
