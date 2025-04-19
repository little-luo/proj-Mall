package com.louis.dao.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.louis.dao.UserDao;
import com.louis.module.User;
import com.louis.rowmapper.UserRowMapper;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<User> getUserByUsername(String username) {
		String sql = "select userid, username, password, gender, full_name, county, district, address, profile, role "
				   + "from webuser where username = :username";
		
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
		String sql = "insert into webuser(username, password, gender, full_name, county, district, address) "
				   + "values (:username, :password, :gender, :fullName, :county, :district, :address)";
		
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

	@Override
	public List<User> getUserByFullName(String fullName) {
		String sql = "select userid, username, password, gender, full_name, county, district, address, profile, role "
				   + "from webuser where full_name = :full_name";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("full_name", fullName);
		
		List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
		return userList;
	}

	@Override
	public void updateUser(Map<String, Object> params, MultipartFile file) {
		String sql = "update webuser set ";
		String fullName = (String) params.get("full_name");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(!fullName.equals("")) {
			sql = sql + "full_name = :full_name ";
			map.put("full_name", fullName);
		}
		
		String gender = (String) params.get("gender");
		if(!gender.equals("")) {
			sql = sql + "gender = :gender ";
			map.put("gender", gender);
		}
		
		String address = (String) params.get("address");
		if(!address.equals("")) {
			String county = address.substring(0, 3);
			sql = sql + "county = :county ";
			map.put("county", county);
			String district = address.substring(3,6);
			sql = sql + "district = :district ";
			map.put("district", district);
			String detailAddress = address.substring(6);
			sql = sql + "address = :address ";
			map.put("address", detailAddress);
		}
		
		// 圖片更新
		if(!file.getOriginalFilename().equals("")) {
			try {
				sql = sql + "profile = :profile";
				byte[] img = file.getBytes();
				map.put("profile", img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		sql = sql + " where userid = :user_id";
		map.put("user_id", (String) params.get("user_id"));
		// 加上逗號分隔
		String set = sql.substring(sql.indexOf("set"), sql.indexOf("where"));
		String reg = "(=\\s*:\\w+)\\s+(\\w+)";
		String result = set.replaceAll(reg, "$1, $2");
		
		String where = sql.substring(sql.indexOf("where"));
		sql = "update webuser " + result + where;
//		System.out.println(result);
//		System.out.println(sql);
		namedParameterJdbcTemplate.update(sql, map);
	}	
	
	
}
