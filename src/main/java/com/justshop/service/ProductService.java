package com.justshop.service;

import java.util.List;

import com.justshop.pojo.PageTotal;
import com.justshop.pojo.Product;
import com.justshop.pojo.ProductPageQueryDTO;
import com.justshop.pojo.ProductVo;

public interface ProductService {



	//商品清單
	List<Product> proList();

	//新增商品
	void add(Product p);

	//分頁查詢
	PageTotal pageList(ProductPageQueryDTO pageQueryDTO);

	//刪除商品
	void del(List<Integer> proId);

	//顯示商品清單
	List<ProductVo> getProdoctVo();

	//更新商品
	void update(Product p);

}
