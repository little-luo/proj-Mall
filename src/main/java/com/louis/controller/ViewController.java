package com.louis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.louis.module.Laptop;
import com.louis.service.LaptopService;

@Controller
public class ViewController {
	
	@Autowired
	private LaptopService laptopService;
	
	@GetMapping("/laptop_spec")
	public ModelAndView toLaptopSpec(@RequestParam Integer laptopId) {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("laptop_spec");
		
		Laptop laptop = laptopService.getLaptopById(laptopId);
		modelAndView.addObject("laptop", laptop);
		
		List<String> specList = laptopService.getSpecByLaptopId(laptopId);
		modelAndView.addObject("specs",specList);
		
		return modelAndView;
	}
	
	@GetMapping("/cart")
	public String toCart() {
		return "cart";
	}
	
	@GetMapping("/loginpage")
	public String loginpage() {
		return "login";
	}
	
	@GetMapping("/sendEmail")
	public String sendEmail() {
		return "sendEmail";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/newPassword")
	public String newPassWord() {
		return "new_password";
	}
	
	@GetMapping("/member")
	public String member() {
		return "member";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

}
