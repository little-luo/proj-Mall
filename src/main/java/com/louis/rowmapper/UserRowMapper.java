package com.louis.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.louis.module.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		User user = new User();
		
		user.setId(rs.getInt("userid"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		
		return user;
	}
	
}
