package test;

import com.yjl.service.impl.UserServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:ApplicationContext.xml"})
public class testmybatis {
	@Resource
	private UserServiceImpl userServiceImpl;
	public void testmybatis()throws Exception
	{
		Integer list=userServiceImpl.selectCount(); 
		System.out.println("list:"+list);
	}
}
