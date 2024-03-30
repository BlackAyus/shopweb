package com.justshop.service;

import java.util.List;

import com.justshop.pojo.PageTotal;
import com.justshop.pojo.ProCategory;
import com.justshop.pojo.Product;

public interface CategoryService {
    //查詢商品類型
	ProCategory selectCatName(String cateName);

	//根據id刪除商品
	void delete(Integer cateId);

	//新增商品分類
	void add(ProCategory s);

	//修改分類
	void update(ProCategory s);

	//查詢分類底下有無其他商品
	Integer findCateId(Integer cateId);

	//查詢商品分類有哪些(用cate_id查)
	List<Product> list(Integer cateId);

	//分頁查詢
	PageTotal page(Integer pageNum, Integer pageSize);

}
