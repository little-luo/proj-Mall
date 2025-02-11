package com.louis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.louis.dao.LaptopDao;
import com.louis.module.Laptop;
import com.louis.service.LaptopService;

@Service
public class LaptopServiceImpl implements LaptopService {
	
	@Autowired
	private LaptopDao dao;

	@Override
	public List<Laptop> getLaptops() {
		return dao.getLaptops();
	}
	
	
}
