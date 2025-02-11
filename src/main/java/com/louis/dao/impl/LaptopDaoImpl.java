package com.louis.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.louis.dao.LaptopDao;
import com.louis.module.Laptop;
import com.louis.rowmapper.LaptopRowMapper;

@Repository
public class LaptopDaoImpl implements LaptopDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<Laptop> getLaptops() {
		
		String sql = "select laptop_id, laptop_name, price, image_url "
				+ "from laptop";
				
		List<Laptop> laptopList = namedParameterJdbcTemplate.query(sql, new LaptopRowMapper());
		
		return laptopList;
	}
	
	
}
