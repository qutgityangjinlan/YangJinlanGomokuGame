package com.yjl.Realm;

import com.yjl.pojo.User;
import com.yjl.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Arrays;

public class myrealm extends AuthorizingRealm{
	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//把AuthenticationToken 转换为UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		//从UsernamePasswordToken中获取username
		String username = upToken.getUsername();
		String password = String.valueOf(upToken.getPassword());
		//调用数据库的方法，从数据库中查询username对应的用户记录
		System.out.println("从数据库获取数据");
		System.out.println("password:"+password);
		User user=userService.selectUserByUserId(username);
		System.out.println("userid:"+username);
		if(user!=null){
			String uname = user.getUserid();
			System.out.println("userid:"+uname);
			String pwd = user.getPassword();
			//若用户不存在，则可以抛出UnknownAccountException异常
		   if(!pwd.equals(password)){
				throw new IncorrectCredentialsException("密码错误");
			}
			//根据用户信息的情况，决定是否需要【抛出其他的UsernamePasswordToken异常.
			//根据用户的情况，来构建AuthenticationInfo 对象并返回，通常使用的实现类为SimpleAuthenticationInfo
			//以下信息是从数据库获取的
			//principal：认证的实体信息，可以是username，也可以是数据表对应的用户的实体对象
			Object principal = uname;
			//credentials :密码
			Object credentials = pwd;
			//realmName：当前realm对象的name,调用父类的getName()方法即可
			String realmName = getName();
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,realmName);
			return info;
		}
		return null;


//		String username=(String) token.getPrincipal();
//		System.out.println("userid:"+username);
//		User user=userService.selectUserByUserId(username);
//		if(user!=null)
//		{
//			SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
//			AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user.getUserid(),user.getPassword(),"myRealm");
//			return authenticationInfo;
//		}
//		return null;
	}

}
