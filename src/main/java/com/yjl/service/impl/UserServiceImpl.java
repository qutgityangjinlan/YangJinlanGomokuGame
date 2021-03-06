package com.yjl.service.impl;

import com.yjl.dao.UserDao;
import com.yjl.dao.UserScoreDao;
import com.yjl.pojo.Score;
import com.yjl.pojo.User;
import com.yjl.service.UserService;
import com.yjl.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value="userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;
	@Autowired
	UserScoreDao userScoreDao;
	public List<User> selectAll(int page, int pageSize) {
		// TODO Auto-generated method stub
		return userDao.selectAll(page, pageSize);
	}

	public User selectUserByUserId(String userid) {
		// TODO Auto-generated method stub
		return userDao.selectUserByUserId(userid);
	}

	public Integer selectCount() {
		// TODO Auto-generated method stub
		return userDao.selectCount();
	}

	public boolean insertUser(User user) {
		userDao.insertUser(user);
		userScoreDao.insert(new Score(){{
			setScore(0);
			setUserid(user.getUserid());
			setTime(new DateUtil().getDateformat());
			setLevel("0段");
		}});
		// TODO Auto-generated method stub
		return true;
	}

	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return userDao.updateUser(user);
	}

	public boolean deleteUser(String userid) {
		// TODO Auto-generated method stub
		return userDao.deleteUser(userid);
	}

}
