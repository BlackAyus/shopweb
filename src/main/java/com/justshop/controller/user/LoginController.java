package com.justshop.controller.user;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justshop.pojo.Result;
import com.justshop.pojo.ShopUser;
import com.justshop.service.UserService;
import com.justshop.utils.JwtUtils;
import com.justshop.utils.Md5util;
import com.justshop.utils.MessageConstant;

import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/shop/user")
@Slf4j
@CrossOrigin
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	/*
	 * 登入API
	 */
	@PostMapping("/login")
	public Result login(@RequestBody ShopUser user) {
		// 1.查詢有無此用戶
		String md5pwd = Md5util.getMD5String(user.getPassword());
		user.setPassword(md5pwd);
		ShopUser u = userService.selectUser(user);
		
		if(u != null) {
			log.info("成功登入，用戶資訊為: {}", u);
			Map<String, Object> claims = new HashMap<>();
			claims.put("id", u.getId());
			claims.put("username", u.getUsername());	
			String jwt = JwtUtils.generateJwt(claims);
			/*
			 * 將JWT儲存一份到redis(k值與v值都設定同個jwt)，並設定失效期間(通常失效時間會與JWT相同)
			 */
			ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
			vo.set(jwt, jwt, 1, TimeUnit.HOURS);
			log.info("已將JWT儲存到redis");
			return Result.success(jwt);
			
		}
			
		return Result.error(MessageConstant.PASSWORD_ERROR);
	}

}
