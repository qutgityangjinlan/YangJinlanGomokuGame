package com.yjl.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Table;

//@Data 注解可以省去代码中大量的get()、 set()、 toString()等方法；要使用 @Data 注解要先引入lombok,在maven中添加依赖
@Data
@TableName("score")
public class Score {
	@TableId("userid") //这个注解表示表的主键
	private String userid;//用户id
	private String time;    //时间
	private int score;    //类型
	private String level;    //状态

}
