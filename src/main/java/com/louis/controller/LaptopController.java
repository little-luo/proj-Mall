package com.louis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.louis.module.Laptop;
import com.louis.service.LaptopService;

@Controller
public class LaptopController {
	
	@Autowired
	private LaptopService laptopService;
	
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
	
	
}
