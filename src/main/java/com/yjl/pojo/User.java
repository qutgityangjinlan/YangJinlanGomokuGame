package com.yjl.pojo;

import lombok.Data;

@Data
public class User {
	private String userid;
	private String password;    //密码
	private String nickname;    //昵称
	private int sex;            //性别
	private int age;            //年龄
	private String profilehead; //头像
	private String profile;     //个性说明
	private String firsttime;   //第一次登录时间
	private String lasttime;    //最后一次登陆时间
	private int status;      //状态
}
