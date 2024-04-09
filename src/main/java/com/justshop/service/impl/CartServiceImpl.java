package com.justshop.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justshop.mapper.CartMapper;
import com.justshop.mapper.ProductMapper;
import com.justshop.pojo.Cart;
import com.justshop.pojo.CartDTO;
import com.justshop.pojo.Product;
import com.justshop.service.CartService;
import com.justshop.utils.ThreadLocalUitls;

import lombok.extern.slf4j.Slf4j;

/*
 * 購物車service類
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService{

	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public void add(CartDTO cart) {
		/*
		 *  1.先判斷加入購物車的商品，是否已在購物車中(商品id + 用戶id，因為用戶間購物車獨立，所以需要再判斷用戶id)
		 *   (1)若已在購物車中: 則直接數量+1(updata)
		 *     => 至於如何updata，首先將CartDTO的欄位值set到cart類中，然後用ThreadLocal取出userId也set到cart
		 *   (2)若未在購物車中 => 新增到購物車
		 */		
		Cart c = new Cart();
		
		Map<String, Object> map = ThreadLocalUitls.get();
		Integer userId = (Integer)map.get("id");
		c.setCartId(cart.getCartId());
		c.setProId(cart.getProId());
		c.setUserId(userId);
		
		List<Cart> list = cartMapper.list(c);
		
		
		if(list != null && list.size() > 0) {
			log.info("有查詢到相同商品故數量+1");
			//通常這時候若有查詢到，基本上只會有一筆，故我們取得第一筆List的值進行修改(將number+1)
			Cart c1 = list.get(0);
			c1.setAcount(c1.getAcount() + 1);
			cartMapper.updateNumber(c1);
		}
		else {
			//這裡要補欄位值，前端只傳遞2個參數，尚有name(商品名)、amount(價格)要補
            Product product = productMapper.getById(c);
            c.setProName(product.getPrdName());
            c.setAmount(product.getPrice());
            c.setAcount(1);
            c.setChecked(1);
            //由於AOP設定插入時，自帶updateTime、createTime，但cart只有createTime故沒辦法AOP，我們用手動補充
            c.setCreateTime(LocalDateTime.now());
			cartMapper.add(c);
		}
	}

	@Override
	public List<Cart> showCart() {
		Cart cart = new Cart();
		Map<String, Object> map = ThreadLocalUitls.get();
		Integer userId = (Integer)map.get("id");
		cart.setUserId(userId);
		List<Cart> list = cartMapper.list(cart);
		return list;
	}

	@Override
	public void clear(Integer userId) {
		cartMapper.clear(userId);
	}

}
