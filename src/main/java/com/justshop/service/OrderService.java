package com.justshop.service;

import com.justshop.pojo.OrdersSubmitDTO;
import com.justshop.pojo.OrdersSubmitVO;

public interface OrderService {

	OrdersSubmitVO submitOrder(OrdersSubmitDTO osd);

}
