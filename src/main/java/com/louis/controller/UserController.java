package com.louis.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.louis.dto.ItemsDto;
import com.louis.module.User;
import com.louis.service.UserService;

@Validated
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentController paymentController;
	
	private final String LOGINPAGE = "<script>window.location.href='/loginpage';</script>";
	
	private final String HOMEPAGE = "<script>window.location.href='/home';</script>";
	
	private String referer = null;
	@CrossOrigin
	@ResponseBody
	@PostMapping(value = "/isAuthenticate",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> isAuthenticate(@RequestBody List<ItemsDto> itemList,
												 HttpServletRequest req) {
		this.referer = req.getHeader("referer");
		
		HttpSession session = req.getSession();
		String userName = (String) session.getAttribute("userName");
		
		if(userName == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	        					 .body(LOGINPAGE);
		}else {
			return ResponseEntity.ok().contentType(MediaType.TEXT_HTML)
		                               .body(paymentController.payment(itemList));
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginRequest(@ModelAttribute @Valid User user, HttpServletRequest req) {
		
		User dbUser;
		String formUserName = user.getUsername();
		String formPassword = user.getPassword();
		
		if (formUserName != null && formPassword != null) {
			List<User> userList = userService.getUserByUsername(formUserName);
			dbUser = userList.get(0);
			String dbUserName = dbUser.getUsername();
			String dbUserPassword = dbUser.getPassword();
			// 驗證帳號、密碼
			if (formUserName.equals(dbUserName) && formPassword.equals(dbUserPassword)) {
				// username存放到session
				HttpSession session = req.getSession();
				session.setAttribute("userName", dbUserName);
				
				String fullName = dbUser.getFullName();
				session.setAttribute("fullName", fullName);
				
				//System.out.println(this.referer);
				if(this.referer != null) {					
					// 登入成功
					String html = "";
					html += "<script>window.location.href='";
					html += this.referer;
					html += "'</script>";
					
					return ResponseEntity.status(HttpStatus.OK).body(html);
				}else {
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
							 .body(HOMEPAGE);
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
									 .body(LOGINPAGE);
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					 .body(LOGINPAGE);
		}
	}
	
	@PostMapping("/validate")
	public ModelAndView validateUser(@RequestParam Map<String, Object> params) {
		String email = (String) params.get("user_email");
		String errorMsg;
		ModelAndView modelAndView = new ModelAndView();
		if(email == null) {
			errorMsg = "EMAIL不能空白";
			modelAndView.addObject("errorMsg", errorMsg);
			modelAndView.setViewName("sendEmail");
			return modelAndView;
		}else {
			List<User> userList = userService.getUserByUsername(email);
			if(userList.size() != 0) {
				// 重設密碼頁面。
				String successMsg = "驗證通過";
				modelAndView.addObject("email",email);
				modelAndView.addObject("successMsg",successMsg);
				modelAndView.setViewName("new_password");
				return modelAndView;
			}else {
				errorMsg = "使用者不存在";
				modelAndView.addObject("errorMsg", errorMsg);
				modelAndView.setViewName("sendEmail");
				return modelAndView;
			}
		}
	}
	
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam Map<String, Object> params) {
		
		String email = (String) params.get("email");
		String password = (String) params.get("password");
		userService.resetPassword(email,password);
		
		return "login";
	}
	
	@PostMapping("/users/register")
	public String register(@RequestParam Map<String, Object> params) {
		
		userService.register(params);
		
		return "login";
	}
	
	@GetMapping("/getFullName")
	public ResponseEntity<String> getFullName(HttpServletRequest req){
		
		String fullName = (String) req.getSession().getAttribute("fullName");
		return ResponseEntity.status(HttpStatus.OK).body(fullName);	
	}
	
}
