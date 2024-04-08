package com.justshop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justshop.pojo.PageTotal;
import com.justshop.pojo.Product;
import com.justshop.pojo.ProductPageQueryDTO;
import com.justshop.pojo.ProductVo;
import com.justshop.pojo.Result;
import com.justshop.service.CategoryService;
import com.justshop.service.ProductService;
import com.justshop.utils.ThreadLocalUitls;

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
	@Autowired
	private RedisTemplate redisTemplate;
	
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
    @CacheEvict(cacheNames = "setPorduct",key = "#p.cateId")
    public Result add(@RequestBody Product p) {  
    	Map<String, Object> map = ThreadLocalUitls.get();
		Integer creadtId = (Integer)map.get("id");
		p.setCreateId(creadtId);
    	log.info("商品參數: {}",p);
    	productService.add(p);
    	//String key = "cate_" + p.getCateId();
    	//redisClear(key);
    	return Result.success();
    }
    
    /*
     * 商品分頁查詢(同時涉及查詢"商品名稱","商品分類","販售狀態")
     *  => 這裡有個點是，前端傳過來的"商品分類"他是Integer，我們後端
     *     必須轉成對應的"字串"給前端
     *  1.吧前端傳來的參數額外建立DTO
     *  2.而外建立VO(因為前端傳過來的"商品分類是cateId",之後基於業務邏輯顯示對應的cateName)   
     */
    
    @GetMapping("/page")
	public Result pageList(ProductPageQueryDTO pageQueryDTO) {
		log.info("分頁查詢參數: {},{},{}", pageQueryDTO);
		PageTotal pt = productService.pageList(pageQueryDTO);

		return Result.success(pt);
	}
    
    /*
     * 刪除商品:
     *  (1)可單筆刪除, 亦可批次刪除
     */
    @CacheEvict(cacheNames = "setProduct",allEntries = true)
    @DeleteMapping("/del")
    public Result del(@RequestParam List<Integer> proId) {
    	log.info("刪除的商品id: {}", proId);
    	productService.del(proId);
    	
    	/*
    	 * 這裡有個邏輯是，商品刪除可能會涉及很多個商品分類，那要如何清理快取資料?
    	 *  (1)直接清理全部快取省得麻煩，關鍵是要怎麼動態取得cate_ ?
    	 *  (2)先用redisTemplate.keys(Object parrern:"cate_*")取出所有keys
    	 *  (3)redisTemplate.delete()本身就支持以集合方式刪除，故這樣就可以了
    	 */
    	//redisClear("cate_*");
    	return Result.success();
    }
    
    /*
     * 修改商品內容
     */
    @CacheEvict(cacheNames = "setProduct",allEntries = true)
    @PutMapping("/updata")
    public Result update(@RequestBody Product p) {
        productService.update(p);
        
        //清理快取資料
        //redisClear("cate_*");
    	
    	return Result.success("修改成功");
    }
    
    /*
     * 查看整個商品，包含回應cateId對應的cateName
     */
    @GetMapping("/listvo")
    public Result getById() {
    	List<ProductVo> pv = productService.getProdoctVo();   	
    	return Result.success(pv);
    }

    /*統一清理快取資料 => 根據模型刪除keys
    private void redisClear(String parrtrn) {
    	Set keys = redisTemplate.keys(parrtrn);
    	redisTemplate.delete(keys);
    	log.info("redis快取清除成功");
    }*/
}

