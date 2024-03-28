package com.justshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justshop.mapper.ProductMapper;
import com.justshop.pojo.Product;
import com.justshop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public List<Product> proList() {
		List<Product> list = productMapper.proList();
		return list;
	}

}
