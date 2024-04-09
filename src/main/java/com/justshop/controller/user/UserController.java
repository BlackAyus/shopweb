package com.justshop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justshop.pojo.Result;
import com.justshop.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/shop/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/*
	 * 用戶status相關(1啟用 0禁用)
	 * 這部分前端會給2個參數:
	 *  1.第一個是狀態碼(這裡用${}接收)
	 *  2.id(這部分從jwt解析出來的Thread物件取得即可)
	 */
	@PostMapping("/status/{status}")
	public Result status(@PathVariable Integer status, Integer id) {
		log.info("員工狀態: {},{}", status, id);
		userService.status(status,id);
		return Result.success();
	}

}
