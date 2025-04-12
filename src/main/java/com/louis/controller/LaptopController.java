package com.louis.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

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
		
		List<Laptop> laptopList = laptopService.getLaptops();
		
		return ResponseEntity.status(HttpStatus.OK).body(laptopList);
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		
		List<Laptop> laptopList = laptopService.getLaptops();

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
	
}
