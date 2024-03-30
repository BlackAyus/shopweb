package com.justshop.service;

import java.util.List;

import com.justshop.pojo.PageTotal;
import com.justshop.pojo.Product;
import com.justshop.pojo.ProductPageQueryDTO;

public interface ProductService {

	//商品清單
	List<Product> proList();

	//新增商品
	void add(Product p);

	//分頁查詢
	PageTotal pageList(ProductPageQueryDTO pageQueryDTO);

}
