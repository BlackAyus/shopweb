package com.justshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justshop.pojo.PageTotal;
import com.justshop.pojo.Product;
import com.justshop.pojo.ProductPageQueryDTO;
import com.justshop.pojo.Result;
import com.justshop.service.CategoryService;
import com.justshop.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/shop/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	/*
	 * 商品清單列表
	 */
    @GetMapping("/list") 
	public Result proList() {
		List<Product> list = productService.proList();
    	log.info("商品清單: {}", list);
    	return Result.success(list);
	}
    
    /*
     * 新增商品
     */
    @PostMapping("/add")
    public Result add(@RequestBody Product p) {
    	
    	log.info("商品參數: {}",p);
    	productService.add(p);
    	return Result.success();
    }
    
    /*
     * 商品分頁查詢(同時涉及查詢"商品名稱","商品分類","販售狀態")
     *  => 這裡有個點是，前端傳過來的"商品分類"他是Integer，我們後端
     *     必須轉成對應的"字串"給前端
     *  1.吧前端傳來的參數額外建立DTO
     *  2.回傳時用VO   
     */
    
    @GetMapping("/page")
	public Result pageList(ProductPageQueryDTO pageQueryDTO) {
		log.info("分頁查詢參數: {},{},{}", pageQueryDTO);
		PageTotal pt = productService.pageList(pageQueryDTO);

		return Result.success(pt);
	}
}
