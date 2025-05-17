package com.louis.dao.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.louis.dao.LaptopDao;
import com.louis.dto.SearchQuery;
import com.louis.dto.SortQuery;
import com.louis.module.Laptop;
import com.louis.rowmapper.LaptopRowMapper;

@Repository
public class LaptopDaoImpl implements LaptopDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private final static Logger log = LoggerFactory.getLogger(LaptopDaoImpl.class);
	@Override
	public Laptop getLaptopById(Integer laptopId) {
		
		String sql = "select laptop_id, laptop_name, price, image_url, brand, os, size "
				   + "from laptop where laptop_id = :laptopId and (state != 'd' or state is null)";
		
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
	public List<Laptop> getLaptops(SortQuery sortQuery) {
		
		String sql = "select laptop_id, laptop_name, price, image_url, brand, os, size "
				   + "from laptop "
				   + "where state != 'd' or state is null "
				   + "order by";
		
		String orderBy;
		String sort;
		if(sortQuery == null) {
			orderBy = "laptop_id";
			sort = "asc";
		}else {
			orderBy = sortQuery.getOrderBy();
			sort = sortQuery.getSort();
		}
		// order by 與 sort 不能使用動態參數綁定的方式
		sql = sql + " " + orderBy + " " + sort;
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

	@Override
	public void createProduct(Map<String, Object> params, MultipartFile file) throws IOException {
		String sql = "insert into laptop(laptop_id,laptop_name,price,image_url,brand,os,size) "
				   + "values(:laptop_id,:laptop_name,:price,:image_url,:brand,:os,:size)";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("laptop_id", (String)params.get("prodId"));
		map.put("laptop_name", (String)params.get("prodName"));
		map.put("price", (String)params.get("prodPrice"));
		
		String path ="/images/" + file.getOriginalFilename();
		map.put("image_url", path);
		
		map.put("brand", (String)params.get("prodBrand"));
		map.put("os", (String)params.get("prodOS"));
		map.put("size", (String)params.get("prodSize"));
		// 上傳圖片
		try {
			String IMAGE_DIRECTORY = new ClassPathResource("/static/images/").getFile().getAbsolutePath();
			//System.out.println("IMAGE_DIRECTORY:" + IMAGE_DIRECTORY);
			Files.write(Paths.get(IMAGE_DIRECTORY,file.getOriginalFilename()), file.getBytes());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		namedParameterJdbcTemplate.update(sql, map);
		
	}

	@Override
	public void updateProductById(Map<String, Object> params, String id, MultipartFile file, Laptop laptop) {
		StringBuilder sb = new StringBuilder("update laptop set ");
		List<String> sets = new ArrayList<String>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String laptopId = (String)params.get("prodId");
		if(params.containsKey("prodId")) {
			sets.add("laptop_id = :laptopId");
			map.put("laptopId", laptopId);
		}
		
		if(params.containsKey("prodName")) {
			sets.add("laptop_name = :laptopName");
			String laptopName = (String)params.get("prodName");
			map.put("laptopName", laptopName);
		}
		
		if(params.containsKey("prodBrand")) {
			sets.add("brand = :brand");
			String brand = (String)params.get("prodBrand");
			map.put("brand", brand);
		}
		
		if(params.containsKey("prodPrice")) {
			sets.add("price = :price");
			String price = (String)params.get("prodPrice");
			map.put("price", price);
		}
		
		if(params.containsKey("prodOS")) {
			sets.add("os = :os");
			String os = (String)params.get("prodOS");
			map.put("os", os);
		}
		
		if(params.containsKey("prodSize")) {
			sets.add("size = :size");
			String size = (String)params.get("prodSize");
			map.put("size", size);
		}
		
		sets.add("image_url = :imgUrl");
		map.put("imgUrl", "/images/" + file.getOriginalFilename());
		String imgUrl = laptop.getImageUrl();
		deleteImage(imgUrl);
		
		
		String path;
		try {
			path = new ClassPathResource("/static/images").getFile().getAbsolutePath();
			Files.write(Paths.get(path, file.getOriginalFilename()), file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			if(e instanceof FileNotFoundException) {
				log.info("路徑為/static/imagees資料夾不存在");
			}
		}
		
		sb.append(String.join(", ", sets));
		sb.append(" where laptop_id = :laptopId");
		
		String sql = sb.toString();
		namedParameterJdbcTemplate.update(sql, map);
		
	}
	
	public void deleteImage(String imgUrl) {
		try {
			File deleteFile = new ClassPathResource("/static" + imgUrl).getFile();
//			System.out.println(new ClassPathResource("/static" + imgUrl).getFile().getAbsolutePath());
			deleteFile.delete();

		} catch (IOException e) {
			e.printStackTrace();
			if(e instanceof FileNotFoundException) {
				log.info("欲刪除的圖片路徑 {} 不存在",imgUrl);
			}	
		}
	}

	@Override
	public void deleteProductById(String id) {
		String sql = "update laptop set state = 'd' "
				   + "where laptop_id = :id";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		
		namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public void createSpecItmes(String laptopId,List<String> specList) {
		String sql = "insert into laptop_spec(laptop_id,spec) values(:laptop_id, :spec)";
		
		if(specList != null) {
			// 刪除所有 spec
			deleteSpecItems(laptopId);
			// 新增spec
			MapSqlParameterSource[] mapSqlParameterSources = new MapSqlParameterSource[specList.size()];
			for(int i = 0; i < specList.size(); i++) {
				mapSqlParameterSources[i] = new MapSqlParameterSource();
				mapSqlParameterSources[i].addValue("laptop_id", laptopId);
				mapSqlParameterSources[i].addValue("spec", specList.get(i));
			}
			
			namedParameterJdbcTemplate.batchUpdate(sql, mapSqlParameterSources);
		}else {
			deleteSpecItems(laptopId);
		}
	}
	
	public void deleteSpecItems(String laptopId) {
		String sql = "delete from laptop_spec where laptop_id = :laptopId";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("laptopId", laptopId);
		
		namedParameterJdbcTemplate.update(sql, map);
	}
	
}
