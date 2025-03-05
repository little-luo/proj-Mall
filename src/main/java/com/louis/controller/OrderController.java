package com.louis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.louis.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/order_id/last")
	public String getLastOrderId() {
		
		String lastOrderNo = orderService.getLastOrderId();
		
		return lastOrderNo;
	}
}
