package com.louis.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.louis.dao.LaptopDao;
import com.louis.dto.SearchQuery;
import com.louis.module.Laptop;
import com.louis.module.Spec;
import com.louis.rowmapper.LaptopRowMapper;

@Repository
public class LaptopDaoImpl implements LaptopDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	@Override
	public Laptop getLaptopById(Integer laptopId) {
		
		String sql = "select laptop_id, laptop_name, price, image_url, brand, os, size "
				   + "from laptop where laptop_id = :laptopId";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("laptopId", laptopId);
		
		List<Laptop> laptopList = namedParameterJdbcTemplate.query(sql, map,new LaptopRowMapper());
		
		if(laptopList.size() > 0) {
			return laptopList.get(0);
		}else {			
			return null;
		}
	}

	@Override
	public List<Laptop> getLaptops() {
		
		String sql = "select laptop_id, laptop_name, price, image_url, brand, os, size "
				   + "from laptop";
				
		List<Laptop> laptopList = namedParameterJdbcTemplate.query(sql, new LaptopRowMapper());
		
		return laptopList;
	}

	@Override
	public List<String> getSpecByLaptopId(Integer laptopId) {
		
		String sql = "select spec from laptop_spec where laptop_id = :laptopId";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("laptopId", laptopId);
		
		List<String> specList = namedParameterJdbcTemplate.queryForList(sql, map,String.class);
		
		return specList;
	}

	@Override
	public List<Laptop> getLaptopByName(String name) {
		
		String sql = "select laptop_id,laptop_name,price,image_url "
				   + "from laptop where laptop_name like :laptop_name";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("laptop_name", "%" + name + "%");
		
		List<Laptop> laptopList = namedParameterJdbcTemplate.query(sql, map, new LaptopRowMapper());
		return laptopList;
	}

	@Override
	public List<Laptop> getProducts(SearchQuery query) {
		
		String sql = "select laptop_id,laptop_name,price,image_url "
				   + "from laptop where 1=1";
		Map<String, Object> map = new HashMap<String, Object>();
		
		String brand = query.getBrand();
		if(brand != null) {
			brand = brand.toUpperCase();
			sql = sql + " and brand = :brand";
			map.put("brand", brand);
//			System.out.println(brand);
		}
		
		String os = query.getOs();
		if(os != null) {
			os = os.replaceFirst("_", " ").toUpperCase();
			sql = sql + " and os = :os";
			map.put("os", os);
//			System.out.println(os);
		}
		
		String size = query.getSize();
		if(size != null) {
			size = size.replaceFirst("_", " ");
			sql = sql + " and size >= :size";
			map.put("size", size);
//			System.out.println(size);
		}
		
		String budget = query.getBudget();
		if(budget != null) {
			sql = sql + " and price <= :budget";
			map.put("budget", budget);
//			System.out.println(budget);
		}
//		System.out.println(sql);
		List<Laptop> laptopList = namedParameterJdbcTemplate.query(sql, map, new LaptopRowMapper());
		
		return laptopList;
	}
	
}
