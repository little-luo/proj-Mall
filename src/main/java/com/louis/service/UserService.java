package com.louis.service;

import java.util.List;
import java.util.Map;

import javax.mail.Multipart;

import org.springframework.web.multipart.MultipartFile;

import com.louis.module.User;

public interface UserService {
	
	public abstract List<User> getUserByUsername(String username);
	
	public abstract void resetPassword(String email,String password);
	
	public abstract boolean register(Map<String, Object> params);
	
	public abstract List<User> getUserByFullName(String fullName);
	
	public abstract void updateUser(Map<String, Object> params, MultipartFile file);
}
