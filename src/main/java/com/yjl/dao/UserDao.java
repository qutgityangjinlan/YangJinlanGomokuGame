package com.yjl.dao;

import com.yjl.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
	List<User> selectAll(@Param("offset") int offset, @Param("limit") int limit);
	User selectUserByUserId(String userid);
	int selectCount();
	boolean insertUser(User user);
	boolean updateUser(User user);
	boolean deleteUser(String userid);
}
