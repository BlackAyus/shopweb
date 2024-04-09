package com.justshop.controller.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justshop.pojo.Cart;
import com.justshop.pojo.CartDTO;
import com.justshop.pojo.Result;
import com.justshop.service.CartService;
import com.justshop.utils.ThreadLocalUitls;

import lombok.extern.slf4j.Slf4j;

/*
 * 購物車控制層
 */
@RestController
@Slf4j
@RequestMapping("/shop/cart")
@CrossOrigin
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/add")
	public Result add(@RequestBody CartDTO cart) {
		log.info("新增購物車其商品資訊為: {}", cart);
		cartService.add(cart);
		return Result.success();	
	}

	//查看購物車
	@GetMapping("/list")
	public Result list() {
	   List<Cart> list = cartService.showCart();	
       return Result.success(list);		
	}
	
	//清空購物車(依據當前用戶id清空，直接從ThreadLocal取出id即可，無須接收參數)
	@DeleteMapping("/clear")
	public Result clear() {
        Map<String, Object> map = ThreadLocalUitls.get(); 
        Integer userId = (Integer)map.get("id");
		cartService.clear(userId);
		return Result.success();
	}
}
