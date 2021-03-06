package com.yjl.controller;

import com.yjl.pojo.Score;
import com.yjl.pojo.User;
import com.yjl.pojo.UserLog;
import com.yjl.service.UserLogService;
import com.yjl.service.UserScoreService;
import com.yjl.service.UserService;
import com.yjl.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"userid"})
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserLogService userLogService;
	@Autowired
	private UserScoreService userScoreService;
	DateUtil dateUtil=new DateUtil();
	//	展示用户信息
	//@PathVariable接收请求路径中占位符的值，跟RequestMapping中的参数一致
	@RequestMapping("information/{userid}")
	public ModelAndView information(@PathVariable("userid")String userid,HttpServletRequest request)
	{
		System.out.println("当前用户："+userid);
		request.getSession().setAttribute("userid", userid);
		ModelAndView modelAndView=new ModelAndView();
		User user=userService.selectUserByUserId(userid);
		Score score = userScoreService.selectByUserId(userid);
		modelAndView.addObject("user", user);
		modelAndView.addObject("score", score);
		modelAndView.setViewName("information");
		return modelAndView;
	}
	//	信息设置
	@RequestMapping("infosetting/{userid}")
	public ModelAndView infosetting(@PathVariable("userid")String userid,Model model){
		Score score=userScoreService.selectByUserId(userid);
		ModelAndView modelAndView=new ModelAndView();
		User user=userService.selectUserByUserId(userid);
		modelAndView.addObject("user", user);
		model.addAttribute("score",score);
		modelAndView.setViewName("info-setting");
		return modelAndView;
	}
	//	修改用户信息
	@RequestMapping("/updateUser/{userid}")
	public String updateUser(User user,@PathVariable("userid")String userid,RedirectAttributes redirectAttributes,HttpServletRequest request){
		user.setUserid(userid);
		boolean flag=userService.updateUser(user);
		if(flag)
		{
			UserLog userLog=new UserLog();
			String ip=request.getRemoteAddr();
			userLog.setUserid(user.getUserid());
			userLog.setTime(dateUtil.getDateformat());
			userLog.setType("修改");
			userLog.setDetail("修改资料");
			userLog.setIp(ip);
			userLogService.insertLog(userLog);
			redirectAttributes.addFlashAttribute("message", "["+userid+"]资料修改成功!");
			return "redirect:/information/"+userid;
		}
		else
		{
			redirectAttributes.addFlashAttribute("error", "["+userid+"]资料修改失败!");
			return "redirect:/information/"+userid;
		}

	}
	//	上传头像
	@RequestMapping("/upload/{userid}")
	public String upload(@PathVariable("userid")String userid,MultipartFile file,HttpServletRequest request,RedirectAttributes redirectAttributes) throws IllegalStateException, IOException{
		String real=request.getServletContext().getRealPath("/");
		String imagename=file.getOriginalFilename();
		System.out.println("imagename:"+imagename);
		String imageurlnotag="/information/upload/"+userid+"/"+imagename;
		File file1=new File(real+"\\information\\upload"+"\\"+userid);
		if(!file1.exists())
		{
			file1.mkdirs();
		}
		File imageurl=new File(file1+"\\"+imagename);
		file.transferTo(imageurl);//将源图片复制到制定位置
		User user=new User();
		user.setUserid(userid);
		user.setProfilehead(imageurlnotag);
		boolean flag=userService.updateUser(user);
		if(flag)
		{
			UserLog userLog=new UserLog();
			String ip=request.getRemoteAddr();
			userLog.setUserid(user.getUserid());
			userLog.setTime(dateUtil.getDateformat());
			userLog.setType("修改");
			userLog.setDetail("修改头像");
			userLog.setIp(ip);
			userLogService.insertLog(userLog);
			redirectAttributes.addFlashAttribute("message", "["+userid+"]头像上传成功!");
			return "redirect:/information/"+userid;
		}
		else
		{
			redirectAttributes.addFlashAttribute("error", "["+userid+"]头像上传失败!");
			return "redirect:/information/"+userid;
		}
	}
	//	修改密码
	@RequestMapping("/modifypassword/{userid}")
	public String modifyPassowrd(@PathVariable("userid")String userid,String oldpass,String newpass,RedirectAttributes redirectAttributes,HttpServletRequest request){
		User user=userService.selectUserByUserId(userid);
		String password=user.getPassword();
		if(password.equals(oldpass))
		{
			User user1=new User();
			user1.setPassword(newpass);
			user1.setUserid(userid);
			boolean flag=userService.updateUser(user1);
			if(flag)
			{
				UserLog userLog=new UserLog();
				String ip=request.getRemoteAddr();
				userLog.setUserid(user.getUserid());
				userLog.setTime(dateUtil.getDateformat());
				userLog.setType("修改");
				userLog.setDetail("修改密码");
				userLog.setIp(ip);
				userLogService.insertLog(userLog);
				redirectAttributes.addFlashAttribute("message", "["+userid+"]密码修改成功!");
				return "redirect:/information/"+userid;
			}
			else {
				redirectAttributes.addFlashAttribute("error", "["+userid+"]密码修改失败!");
				return "redirect:/infosetting/"+userid;
			}
		}
		else{
			System.out.println("修改失败!");
			redirectAttributes.addFlashAttribute("error", "["+userid+"]密码修改失败!");
		}
		return "redirect:/infosetting/"+userid;
	}
	//	积分更新
	@RequestMapping("/insertOrUpdateScore")
	@ResponseBody
	public Boolean CollectScore(@RequestParam("userid")String userid){
			Score score = userScoreService.selectByUserId(userid);
			if (score!=null){
				score.setScore(score.getScore()+10);
				score.setLevel((int)score.getScore()/20+"段");
				score.setTime(dateUtil.getDateformat());
				userScoreService.updateByUserId(score);
			}else {
				userScoreService.insertScore(new Score(){{
					setUserid(userid);
					setScore(10);
					setTime(dateUtil.getDateformat());
					setLevel("一段");
				}});
			}
			return true;
	}
	//	查看日志
	@RequestMapping("/log/{userid}")
	public String log(@PathVariable("userid")String userid,@RequestParam("page")int page,HttpServletRequest request)
	{
		if( request.getSession().getAttribute("pageSize") == null)
		{
			request.getSession().setAttribute("pageSize", 5);
		}
		int pageSize=(Integer) request.getSession().getAttribute("pageSize");
		int count;
		List<UserLog> loglist=new ArrayList<UserLog>();
		loglist=userLogService.selectLogByUserid(userid, page, pageSize);
		count=userLogService.selectLogCountByUserid(userid, pageSize);
		request.getSession().setAttribute("loglist", loglist);
		request.getSession().setAttribute("count", count);
		return "log";
	}
	//	用于获取发送信息时的头像，使用流
	@RequestMapping("/head/{userid}")
	public void gethead(@PathVariable("userid")String userid,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String realurl=request.getServletContext().getRealPath("/");
		String imageurl=userService.selectUserByUserId(userid).getProfilehead();
		String url=realurl+imageurl;
		InputStream inputStream=new FileInputStream(url);
		ServletOutputStream outputStream=response.getOutputStream();
		response.setContentType("image/jpeg; charset=UTF-8");
		byte[] buffer=new byte[1024];
		int i=-1;
		while((i=inputStream.read(buffer))!=-1)
		{
			outputStream.write(buffer, 0, i);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();
	}
	//	帮助
	@RequestMapping("/help")
	public String help()
	{
		return "help";

	}
	//	关于
	@RequestMapping("/about")
	public String about()
	{
		return "about";
	}
	//	系统设置
	@RequestMapping("system-setting/{userid}")
	public String systemsetting(@PathVariable("userid")String userid,HttpServletRequest request)
	{
		User user=userService.selectUserByUserId(userid);
		request.setAttribute("user", user);
		return "system-setting";
	}
	//	修改系统设置
	@RequestMapping("changesystem/{userid}")
	public String changesystem(@PathVariable("userid")String userid,int options,int secrecy,HttpServletRequest request,RedirectAttributes redirectAttributes)
	{
		request.getSession().setAttribute("pageSize", options);
		User user=new User();
		user.setUserid(userid);
		user.setStatus(secrecy);
		boolean flag=userService.updateUser(user);
		if(flag)
		{
			redirectAttributes.addFlashAttribute("message", "系统设置修改成功!");
		}
		else
		{
			redirectAttributes.addFlashAttribute("error", "系统修改失败!");
		}
		return "redirect:/system-set";
	}
	//	其他人信息
	@RequestMapping("otherinfo/{userid}")
	public String otherinformation(@PathVariable("userid")String userid,HttpServletRequest request,RedirectAttributes redirectAttributes,Model model)
	{
		User user =userService.selectUserByUserId(userid);
		if(user.getStatus()==-1)
		{
			System.out.println("当前用户名："+userid);
			redirectAttributes.addFlashAttribute("error", userid+"的信息未公开!");
			model.addAttribute("username",userid);
			model.addAttribute("image",user.getProfilehead());
			model.addAttribute("nickName",user.getNickname());
			return "errorinfo";
		}
		else
		{
			return "redirect:/information/"+userid;
		}
	}
	@RequestMapping("errorinfo")
	public String error()
	{
		return "errorinfo";
	}
	@RequestMapping("/system-set")
	public String systemsett()
	{
		return "system-setting";
	}
}
