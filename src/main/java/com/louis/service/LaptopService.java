package com.louis.service;

import java.util.List;

import com.louis.dto.SearchQuery;
import com.louis.module.Laptop;
import com.louis.module.Spec;

public interface LaptopService {
	
	public abstract Laptop getLaptopById(Integer laptopId);
	
	public abstract List<Laptop> getLaptops();
	
	public abstract List<String> getSpecByLaptopId(Integer laptopId);
	
	public abstract List<Laptop> getLaptopsByName(String name);
	
	public abstract List<Laptop> getProducts(SearchQuery query);
}
