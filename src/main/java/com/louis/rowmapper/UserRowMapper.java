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
		user.setGender(rs.getString("gender"));
		user.setFullName(rs.getString("full_name"));
		user.setCounty(rs.getString("county"));
		user.setDistrict(rs.getString("district"));
		user.setAddress(rs.getString("address"));
		
		return user;
	}
	
}
