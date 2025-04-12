package com.louis.dao;

import java.util.List;

import com.louis.module.Laptop;
import com.louis.module.Spec;

public interface LaptopDao {
	
	public abstract Laptop getLaptopById(Integer laptopId);
	
	public abstract List<Laptop> getLaptops();
	
	public abstract List<String> getSpecByLaptopId(Integer laptopId);
	
	public abstract List<Laptop> getLaptopByName(String name); 
}
