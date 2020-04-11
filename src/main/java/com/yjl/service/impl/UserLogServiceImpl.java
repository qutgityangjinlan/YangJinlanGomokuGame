package com.yjl.service.impl;

import com.yjl.dao.UserLogDao;
import com.yjl.pojo.UserLog;
import com.yjl.service.UserLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value="userLogService")
public class UserLogServiceImpl implements UserLogService {
	@Resource
	private UserLogDao userLogDao;
	public List<UserLog> selectAllLog(int page, int pageSize) {
		// TODO Auto-generated method stub
		return userLogDao.selectAllLog(page, pageSize);
	}

	public List<UserLog> selectLogByUserid(String userid, int page, int pageSize) {
		// TODO Auto-generated method stub
		int start=(page-1)*pageSize;
		int end=pageSize;
		return userLogDao.selectLogByUserid(userid, start, end);
	}

	public Integer selectLogCount() {
		// TODO Auto-generated method stub
		return userLogDao.selectLogCount();
	}
	public Integer selectLogcountfromuserid(String userid)
	{
		int all=userLogDao.selectLogCountByUserid(userid);
		return all;
	}
	//	返回总页数
	public Integer selectLogCountByUserid(String userid,int pageSize) {
		int all=userLogDao.selectLogCountByUserid(userid);
		return all%pageSize==0?all/pageSize:all/pageSize+1;
	}

	public boolean insertLog(UserLog userLog) {
		// TODO Auto-generated method stub
		return userLogDao.insertLog(userLog);
	}

	public boolean deleteLog(Integer id) {
		// TODO Auto-generated method stub
		return userLogDao.deleteLog(id);
	}

	public boolean deleteThisUserLog(String userid) {
		// TODO Auto-generated method stub
		return userLogDao.deleteThisUserLog(userid);
	}

	public boolean deleteAllLog() {
		// TODO Auto-generated method stub
		return userLogDao.deleteAllLog();
	}

}
