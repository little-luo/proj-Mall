package com.louis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.ListenerNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.expression.Lists;

import com.louis.dto.SearchQuery;
import com.louis.dto.SortQuery;
import com.louis.module.Laptop;
import com.louis.service.LaptopService;

@Controller
@Validated
public class LaptopController {
	
	@Autowired
	private LaptopService laptopService;
	
	@ResponseBody
	@GetMapping("/laptops/{laptopId}")
	public ResponseEntity<Laptop> getLaptop(@PathVariable @NotNull Integer laptopId){
		
		Laptop laptop = laptopService.getLaptopById(laptopId);
		
		return ResponseEntity.status(HttpStatus.OK).body(laptop);
	}
	
	@ResponseBody
	@GetMapping("/laptops")
	public ResponseEntity<List<Laptop>> getLaptops(){
		
		List<Laptop> laptopList = laptopService.getLaptops(null);
		
		return ResponseEntity.status(HttpStatus.OK).body(laptopList);
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		
		List<Laptop> laptopList = laptopService.getLaptops(null);

		model.addAttribute("laptops", laptopList);
		
		return "homepage";
	}

	@PostMapping("/search")
	public 	ResponseEntity<List<Laptop>> search(@RequestParam(name = "search") String name) throws Exception{
				
		List<Laptop> laptopList = laptopService.getLaptopsByName(name);
		
		if(laptopList.size() > 0) {			
			return ResponseEntity.status(HttpStatus.OK).body(laptopList);
		}else {
			throw new Exception("查無資料");
		}
	}
	
	@PostMapping("/products")
	public ResponseEntity<List<Laptop>> getProducts(@RequestParam(required = false, name = "brand_option") String brand,
													@RequestParam(required = false, name = "os_option") String os,
													@RequestParam(required = false, name = "size_option") String size,
													@RequestParam(required = false, name = "budget_option") String budget) throws Exception {
		SearchQuery query = new SearchQuery();
		query.setBrand(brand);
		query.setOs(os);
		query.setSize(size);
		query.setBudget(budget);
		
		List<Laptop> laptopList = laptopService.getProducts(query);
		
		if(laptopList.size() > 0) {			
			return ResponseEntity.status(HttpStatus.OK).body(laptopList);
		}else {
			throw new Exception("查無資料");
		}
	}
	
	@PostMapping("/createProduct")
	public ResponseEntity<String> createProduct(@RequestParam Map<String, Object> params, @RequestParam("prodImg") MultipartFile file) throws IOException{
		
		laptopService.createProduct(params,file);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("<script>window.location.href='/admin';</script>");
		
	}
	
	@PostMapping("/updateProduct/{id}")
	public ResponseEntity<String> updateProdcutById(@RequestParam Map<String, Object> params,@PathVariable String id, @RequestParam("prodImg") MultipartFile file) {
		
		laptopService.updateProductById(params,id,file);
		
		return ResponseEntity.status(HttpStatus.OK).body("<script>window.location.href='/admin';</script>");
	}
	
	@GetMapping("/deleteProduct/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable String id){

		laptopService.deleteProductById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body("刪除成功");
	}
	
	@GetMapping("/getPageTotal")
	public ResponseEntity<String> getPageTotal(@RequestParam(required = false,defaultValue = "laptop_id") String orderBy,
											   @RequestParam(required = false,defaultValue = "asc") String sort){
		
		SortQuery sortQuery = new SortQuery();
		sortQuery.setOrderBy(orderBy);
		sortQuery.setSort(sort);
		
		Integer size = laptopService.getLaptops(sortQuery).size();
		
		Integer pageTotal = null;
		if(size <= 10) {
			pageTotal = 1;
		}
		
		if(size > 10) {
			pageTotal = size / 10 + 1;
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(pageTotal));
	}
	
	@PostMapping("/addSpecs")
	public ResponseEntity<String> getProducts(@RequestParam(required = true) String laptopId, 
							@RequestParam(required = false,name = "spec") List<String> allSpecs){
		
		laptopService.createSpecItmes(laptopId,allSpecs);
		return ResponseEntity.status(HttpStatus.OK).body("<script>window.location.href='/admin';</script>");
	}
	
	@GetMapping("/laptops/{laptopId}/specs")
	public ResponseEntity<List<String>> getlaptopSpecsById(@PathVariable Integer laptopId){
		return ResponseEntity.status(HttpStatus.OK).body(laptopService.getSpecByLaptopId(laptopId));
	}
}
