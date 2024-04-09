package com.justshop.service;

import java.util.List;

import com.justshop.pojo.Cart;
import com.justshop.pojo.CartDTO;


public interface CartService {

	void add(CartDTO cart);

	List<Cart> showCart();

	void clear(Integer userId);

}
