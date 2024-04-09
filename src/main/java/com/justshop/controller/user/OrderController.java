package com.justshop.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justshop.pojo.OrdersSubmitDTO;
import com.justshop.pojo.OrdersSubmitVO;
import com.justshop.pojo.Result;
import com.justshop.service.OrderService;

import lombok.extern.slf4j.Slf4j;

/*
 * 訂單控制層
 */
@Slf4j
@RestController("userOrderController")
@CrossOrigin
@RequestMapping("/shop/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@PostMapping("/submit")
	public Result submit(@RequestBody OrdersSubmitDTO osd) {
        log.info("用戶下單參數: {}",osd);
		OrdersSubmitVO ordersSubmitVO = orderService.submitOrder(osd);
		return Result.success(ordersSubmitVO);
	}
}
