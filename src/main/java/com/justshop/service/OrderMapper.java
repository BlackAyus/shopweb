package com.justshop.service;

import org.apache.ibatis.annotations.Mapper;

import com.justshop.pojo.OrdersSubmitDTO;
import com.justshop.pojo.OrdersSubmitVO;

@Mapper
public interface OrderMapper {

	//生成訂單
	OrdersSubmitVO submitOrder(OrdersSubmitDTO osd);

}
