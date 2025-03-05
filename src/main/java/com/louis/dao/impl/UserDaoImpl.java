package com.louis.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.louis.dao.UserDao;
import com.louis.module.User;
import com.louis.rowmapper.UserRowMapper;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<User> getUserByUsername(String username) {
		String sql = "select userid, username, password from webuser where username = :username";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		
		List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
		return userList;
	}
	
}
