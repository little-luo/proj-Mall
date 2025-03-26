package com.louis.service;

import java.util.List;
import java.util.Map;

import com.louis.module.User;

public interface UserService {
	
	public abstract List<User> getUserByUsername(String username);
	
	public abstract void resetPassword(String email,String password);
	
	public abstract boolean register(Map<String, Object> params);
}
