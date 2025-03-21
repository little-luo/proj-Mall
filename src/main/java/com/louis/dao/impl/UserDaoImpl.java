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
		String sql = "select userid, username, password, gender, full_name, county, district, address from webuser where username = :username";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		
		List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
		return userList;
	}

	@Override
	public void resetPassword(String email, String password) {
		String sql = "update webuser set password = :password where username = :email";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("password", password);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public void createUser(Map<String, Object> params) {
		String sql = "insert into webuser(username, password, gender, full_name, county, district, address)"
				+ " values (:username, :password, :gender, :fullName, :county, :district, :address)";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String username = (String) params.get("username");
		map.put("username", username);
		
		String password = (String) params.get("password");
		map.put("password", password);
		
		String gender = (String) params.get("gender");
		map.put("gender", gender);
		
		String fullName = (String) params.get("full_name");
		map.put("fullName", fullName);
		
		String county = (String) params.get("county");
		map.put("county", county);
		
		String district = (String) params.get("district");
		map.put("district", district);
		
		String address = (String) params.get("address");
		map.put("address", address);
		
		namedParameterJdbcTemplate.update(sql, map);

	}
	
	
}
