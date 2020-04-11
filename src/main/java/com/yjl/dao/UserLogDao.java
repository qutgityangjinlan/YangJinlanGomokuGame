package com.yjl.dao;

import com.yjl.pojo.UserLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserLogDao {
	List<UserLog> selectAllLog(@Param("offset") int offset, @Param("limit") int limit);
	List<UserLog> selectLogByUserid(@Param("userid") String userid, @Param("offset") int offset, @Param("limit") int limit);
	Integer selectLogCount();
	Integer selectLogCountByUserid(@Param("userid") String userid);
	boolean insertLog(UserLog userLog);
	boolean deleteLog(Integer id);
	boolean deleteThisUserLog(String userid);
	boolean deleteAllLog();
}
