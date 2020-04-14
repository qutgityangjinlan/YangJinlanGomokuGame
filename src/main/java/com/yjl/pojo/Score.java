package com.yjl.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Table;

@Data
@TableName("score")
public class Score {
	@TableId("userid")
	private String userid;//用户id
	private String time;    //时间
	private int score;    //类型
	private String level;    //状态

}
