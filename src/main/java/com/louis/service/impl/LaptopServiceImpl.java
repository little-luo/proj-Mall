package com.louis.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.louis.dao.LaptopDao;
import com.louis.dto.SearchQuery;
import com.louis.module.Laptop;
import com.louis.module.Spec;
import com.louis.service.LaptopService;

@Service
public class LaptopServiceImpl implements LaptopService {
	
	@Autowired
	private LaptopDao dao;
	
	@Override
	public Laptop getLaptopById(Integer laptopId) {
		return dao.getLaptopById(laptopId);
	}

	@Override
	public List<Laptop> getLaptops() {
		return dao.getLaptops();
	}

	@Override
	public List<String> getSpecByLaptopId(Integer laptopId) {
		return dao.getSpecByLaptopId(laptopId);
	}

	@Override
	public List<Laptop> getLaptopsByName(String name) {
		return dao.getLaptopByName(name);
	}

	@Override
	public List<Laptop> getProducts(SearchQuery query) {
		return dao.getProducts(query);
	}

	@Override
	public void createProduct(Map<String, Object> params, MultipartFile file) throws IOException {
		dao.createProduct(params,file);
	}
	
	
	
	
}
