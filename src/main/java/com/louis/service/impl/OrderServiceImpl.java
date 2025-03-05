package com.louis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.louis.dao.OrderDao;
import com.louis.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	@Override
	public String getLastOrderId() {
		return orderDao.getLastOrderId();
	}
	
}
