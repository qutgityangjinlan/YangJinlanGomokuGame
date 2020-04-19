package test;
import com.yjl.service.impl.UserLogServiceImpl;
import com.yjl.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:ApplicationContext.xml"})
public  class testtest {
	@Resource
	private UserServiceImpl userServiceImpl;
	@Resource
	private UserLogServiceImpl userLogServiceImpl;
	@Test
	public void testmybatis()throws Exception
	{
//		Integer list=userServiceImpl.selectCount(); 
//		System.out.println("list:"+list);
//		List<User> list=userServiceImpl.selectAll(0, 1);
//		System.out.println(list.get(0).getPassword());
//		System.out.println(userServiceImpl.selectUserByUserId("admin").getNickname());
//		User user=new User();
//		user.setUserid("aaa");
//		user.setNickname("aaa");
//		user.setPassword("aaa");
//		System.out.println(userServiceImpl.deleteUser("aaa"));
//		System.out.println(userLogServiceImpl.selectAllLog(0, 1));
//		System.out.println(userLogServiceImpl.selectLogByUserid("admin", 0, 5).size());
//		UserLog userLog=new UserLog();
//		userLog.setId("ASD");
//		userLog.setTime("456");
//		System.out.println(userLogServiceImpl.deleteLog("ASD"));
//		User user=new User();
//		user.setUserid("admin");
//		user.setSex(-1);
//		userServiceImpl.updateUser(user);
//		System.out.println(userServiceImpl.selectUserByUserId("admin"));
//		System.out.println(userLogServiceImpl.selectAllLog(0, 1));                             
//		System.out.println(userLogServiceImpl.selectAllLog(0, 1).get(0).getId());
//		System.out.println(userLogServiceImpl.selectLogByUserid("admin", 0, 1));
//		System.out.println(userLogServiceImpl.selectLogCount());
//		System.out.println(userLogServiceImpl.selectLogC                                                                                                                                                                                                                                                                                                                   ountByUserid("admin"));
//		UserLog userLog=new UserLog();
//		userLog.setUserid("admin");
//		userLog.setDetail("1111");
//		userLogServiceImpl.insertLog(userLog);
//		userLogServiceImpl.deleteThisUserLog("admin");
//		userLogServiceImpl.deleteAllLog();
//		System.out.println(userLogServiceImpl.selectLogByUserid("admin", 1, 7).size());
	}
}
