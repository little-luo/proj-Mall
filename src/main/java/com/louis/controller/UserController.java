package com.louis.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
				
				System.out.println(this.referer);
				// 登入成功
				String html = "";
				html += "<script>window.location.href='";
				html += this.referer;
				html += "'</script>";
				
				return ResponseEntity.status(HttpStatus.OK).body(html);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
									 .body(LOGINPAGE);
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					 .body(LOGINPAGE);
		}
	}
}
