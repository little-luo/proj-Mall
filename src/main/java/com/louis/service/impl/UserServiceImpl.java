package com.louis.service.impl;

import java.util.List;

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
	
}
