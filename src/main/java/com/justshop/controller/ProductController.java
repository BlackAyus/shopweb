package com.justshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justshop.pojo.Product;
import com.justshop.pojo.Result;
import com.justshop.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/shop/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	/*
	 * 商品清單列表
	 */
    @GetMapping("/list") 
	public Result proList() {
		List<Product> list = productService.proList();
    	log.info("商品清單: {}", list);
    	return Result.success(list);
	}
}
