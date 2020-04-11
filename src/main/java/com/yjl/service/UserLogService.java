package com.yjl.service;


import com.yjl.pojo.UserLog;

import java.util.List;

public interface UserLogService {
	List<UserLog> selectAllLog(int page, int pageSize);
	List<UserLog> selectLogByUserid(String userid, int page, int pageSize);
	Integer selectLogCount();
	Integer selectLogCountByUserid(String userid, int PageSize);
	Integer selectLogcountfromuserid(String userid);
	boolean insertLog(UserLog userLog);
	boolean deleteLog(Integer id);
	boolean deleteThisUserLog(String userid);
	boolean deleteAllLog();
}
