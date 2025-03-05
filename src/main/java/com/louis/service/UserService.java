package com.louis.service;

import java.util.List;

import com.louis.module.User;

public interface UserService {
	
	public abstract List<User> getUserByUsername(String username);
}
