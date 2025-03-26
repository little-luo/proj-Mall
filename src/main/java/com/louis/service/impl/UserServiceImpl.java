package com.louis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.louis.dao.UserDao;
import com.louis.module.User;
import com.louis.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	public void resetPassword(String email, String password) {
		userDao.resetPassword(email,password);
	}

	@Override
	public boolean register(Map<String, Object> params) {
		
		String email = (String) params.get("username");
		
		List<User> userList = userDao.getUserByUsername(email);
		if(userList.size() == 0) {
			userDao.createUser(params);
			return true;
		}else {
			return false;
		}
	}
	
	
}
