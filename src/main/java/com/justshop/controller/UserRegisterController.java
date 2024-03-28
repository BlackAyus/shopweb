package com.justshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justshop.pojo.Result;
import com.justshop.pojo.ShopUser;
import com.justshop.service.UserService;

import lombok.extern.slf4j.Slf4j;

/*
 * 註冊類別
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin
public class UserRegisterController {
	
	@Autowired
	private UserService userService;
	
	//註冊功能
//	@PostMapping("/register")
//	public Result register(@Pattern(regexp = "^\\S{5,10}$") String username, @Pattern(regexp = "^\\S{5,10}$")String password) {
//	    //1.先用@Pattern註解對參數驗證是否合乎規範，若是較複雜的驗證邏輯，還是用基本的if-else比較好
//		
//		//2.查詢用戶
//		User u = userService.findUserName(username);	
//		if(u == null) {
//			//若查詢結果為空，則可以註冊
//			userService.register(username, password);
//			log.info("註冊成功，用戶名為: {}", username);
//			return Result.success("註冊成功");
//		}else {		
//			return Result.error("用戶名已存在，註冊失敗");
//		}
//		
//	}
	
	//註冊功能(用json接收)
	@PostMapping("/register")
	public Result register(@RequestBody ShopUser user) {
	//1.做一些帳號驗驗
	if(user.getUsername().length() < 5 || user.getUsername().length() > 10) {
		return Result.error("長度必須是 5 - 10"); 
	}
	if(user.getPassword().length() < 6 || user.getPassword().length() > 15) {
		return Result.error("長度必須是 6 - 15");
	}	
	//2.查詢用戶
	ShopUser u = userService.findUserName(user.getUsername());	
    if(u == null) {
		//若查詢結果為空，則可以註冊
		userService.register(user);
		log.info("註冊成功，用戶名為: {}", user.getUsername());
		return Result.success("註冊成功");
		}else {		
			return Result.error("用戶名已存在，註冊失敗");
	}
	}
}
