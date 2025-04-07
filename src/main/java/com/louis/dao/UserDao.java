package com.louis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.louis.module.User;

public interface UserDao {
	
	public abstract List<User> getUserByUsername(String username);
	
	public abstract void resetPassword(String email,String password);
	
	public abstract void createUser(Map<String, Object> params);
	
	public abstract List<User> getUserByFullName(String fullName);
	
	public abstract void updateUser(Map<String,Object> params,MultipartFile file);
}
