package com.justshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justshop.pojo.OrdersSubmitDTO;
import com.justshop.pojo.OrdersSubmitVO;
import com.justshop.service.OrderMapper;
import com.justshop.service.OrderService;
import com.justshop.service.OrdersDetailMapper;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrdersDetailMapper ordersDetailMapper;

	@Override
	public OrdersSubmitVO submitOrder(OrdersSubmitDTO osd) {
		OrdersSubmitVO ordersSubmitVO = orderMapper.submitOrder(osd);
		return null;
	}

}
