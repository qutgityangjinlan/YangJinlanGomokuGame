package com.yjl.pojo;

import lombok.Data;

@Data
public class UserLog {
	private int id;      //序号
	private String userid;  //用户ID
	private String time;    //时间
	private String type;    //类型
	private String detail;  //细节
	private String ip;      //地址
}
