package com.louis.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.louis.dto.SearchQuery;
import com.louis.dto.SortQuery;
import com.louis.module.Laptop;
import com.louis.module.Spec;

public interface LaptopDao {
	
	public abstract Laptop getLaptopById(Integer laptopId);
	
	public abstract List<Laptop> getLaptops(SortQuery sortQuery);
	
	public abstract List<String> getSpecByLaptopId(Integer laptopId);
	
	public abstract List<Laptop> getLaptopByName(String name);
	
	public abstract List<Laptop> getProducts(SearchQuery query);
	
	public abstract void createProduct(Map<String, Object> params,MultipartFile file) throws IOException;
	
	public abstract void updateProductById(Map<String, Object> params, String id, MultipartFile file, Laptop laptop);
	
	public abstract void deleteProductById(String id);
	
	public abstract void createSpecItmes(String laptopId, List<String> specList);
}
