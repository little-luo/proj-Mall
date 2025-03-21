package com.louis.dao;

import java.util.List;
import java.util.Map;

import com.louis.module.User;

public interface UserDao {
	
	public abstract List<User> getUserByUsername(String username);
	
	public abstract void resetPassword(String email,String password);
	
	public abstract void createUser(Map<String, Object> params);
}
