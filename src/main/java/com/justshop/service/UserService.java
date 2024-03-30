package com.justshop.service;

import com.justshop.pojo.ShopUser;

public interface UserService {
	//查詢有無該用戶
	ShopUser selectUser(ShopUser user);

	//查詢username
	ShopUser findUserName(String username);

	//註冊用戶
	void register(ShopUser user);

	//用戶禁用
	void status(Integer status, Integer id);

}
