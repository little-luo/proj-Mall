package com.louis.dao.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.louis.dao.OrderDao;

@Repository
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public String getLastOrderId() {
		
		String sql = "select order_id from `weborder` order by `order_id` desc limit 1";
		
		String lastOrderNo = namedParameterJdbcTemplate.queryForObject(sql, 
				new HashMap<String, Object>(), 
				String.class);
		
		return lastOrderNo;
	}
	
}
