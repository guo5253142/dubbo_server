package com.main.server.system;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.main.server.TestBase;
import com.my.common.system.domain.User;
import com.my.common.system.service.UserService;

public class TestSystem extends TestBase {
	@Autowired  
	private UserService userService;
	
	@Test
	public void queryAllUserList(){
		List<User> list=userService.queryAllUserList();
		System.out.println(list.size());
	}
	
	
	
	
}
