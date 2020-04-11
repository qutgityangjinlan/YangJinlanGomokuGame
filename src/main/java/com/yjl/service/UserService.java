package com.yjl.service;

import com.yjl.pojo.User;

import java.util.List;

public interface UserService {
	List<User> selectAll(int page, int pageSize);
	User selectUserByUserId(String userid);
	Integer selectCount();
	boolean insertUser(User user);
	boolean updateUser(User user);
	boolean deleteUser(String userid);
}
