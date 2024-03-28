package com.justshop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.justshop.pojo.Product;

@Mapper
public interface ProductMapper {

	//查詢商品清單
	@Select("select * from product")
	List<Product> proList();

}
