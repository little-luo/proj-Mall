package com.louis.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.louis.dao.LaptopDao;
import com.louis.dto.SearchQuery;
import com.louis.dto.SortQuery;
import com.louis.module.Laptop;
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
	public List<Laptop> getLaptops(SortQuery sortQuery) {
		return dao.getLaptops(sortQuery);
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

	@Override
	public void updateProductById(Map<String, Object> params, String id, MultipartFile file) {
		Laptop laptop = dao.getLaptopById(Integer.valueOf(id));
		
		dao.updateProductById(params,id,file,laptop);
	}

	@Override
	public void deleteProductById(String id) {
		dao.deleteProductById(id);
	}

	@Override
	public void createSpecItmes(String laptopId ,List<String> specList) {
		
//		List<String> allSpec = getSpecByLaptopId(Integer.parseInt(laptopId));
		
//		for(String spec1 : allSpec) {
//			System.out.println("spec1:" + spec1);
//		}
//		
//		for(String spec2 : specList) {
//			System.out.println("spec2:" + spec2);
//		}
//		List<String> temp = new ArrayList<String>();
//		for(int i = 0; i < specList.size(); i++) {
//			for(int j = 0; j < allSpec.size(); j++) {
//				if(specList.get(i).equals(allSpec.get(i))) {
//					continue;
//				}
//				temp.add(specList.get(i));
//			}
//		}
		dao.createSpecItmes(laptopId,specList);
	}
	
	
	
}
