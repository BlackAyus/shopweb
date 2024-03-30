package com.justshop.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justshop.mapper.UserMapper;
import com.justshop.pojo.ShopUser;
import com.justshop.service.UserService;
import com.justshop.utils.Md5util;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public ShopUser selectUser(ShopUser user) {
		ShopUser u = userMapper.selectUser(user);
		return u;
	}

	@Override
	public ShopUser findUserName(String username) {
		ShopUser u = userMapper.findUsername(username);
		return u;
	}


	//註冊帳號
	@Override
	public void register(ShopUser user) {
		  user.setPassword(Md5util.getMD5String(user.getPassword()));
		  user.setEmail("test123@gmail.com");
		  user.setLoginDate(LocalDateTime.now());
		  user.setStatus(0);
		  user.setRole(1);
		  user.setCreateTime(LocalDateTime.now());
		  user.setUpdateTime(LocalDateTime.now());
		  user.setRemark("測試帳號");
		  user.setAvatar("handsome.jpg");
		  userMapper.register(user);
	}

	//用戶禁用
	@Override
	public void status(Integer status, Integer id) {
		ShopUser shopUser = new ShopUser();
		shopUser.setUpdateTime(LocalDateTime.now());
		shopUser.setId(id);
		shopUser.setStatus(status);
		userMapper.statusXml(shopUser);
	}

 			

}
