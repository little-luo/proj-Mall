package com.louis.dao;

import java.util.List;

import com.louis.module.User;

public interface UserDao {
	
	public abstract List<User> getUserByUsername(String username);
}
