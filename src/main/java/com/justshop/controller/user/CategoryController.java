package com.justshop.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import com.justshop.pojo.ProCategory;
import com.justshop.pojo.Product;
import com.justshop.pojo.Result;
import com.justshop.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

/*
 * 商品分類
 */

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/admin/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private RedisTemplate redisTemplate;

	
	/*
	 * 新增商品分類
	 */
	@PostMapping("/add")
	public Result addCategory(@RequestBody ProCategory s) {
		// 1.先查詢商品有無重複(返回的ProCategor s1之後也可以用在新增商品的商品分類中)
		log.info("加入商品分類參數: {}", s);
		ProCategory s1 = categoryService.selectCatName(s.getCateName());
		if(s1 != null) {
			return Result.error("重複啦");
		}
		categoryService.add(s);
		return Result.success("新增成功");
	}
	
	
	/*
	 * 分類分頁查詢，並可以根據商品分類(catId)與商品類型(proId)查詢
	 */
	@GetMapping("/page")
	public Result pageList(@RequestParam(defaultValue = "1") Integer pageNum,
			               @RequestParam(defaultValue = "5") Integer pageSize,
			               @RequestParam(required = false) Integer cateId,
			               @RequestParam(required = false) Integer proId) {
		log.info("分頁查詢參數: {},{},{}", pageNum, pageSize);
		PageTotal pt = categoryService.page(pageNum,pageSize,cateId,proId);
		log.info("分頁查詢結果: {}", pt);
		return Result.success(pt);
	}
	
	/*
	 * 根據id刪除分類
	 */
	@DeleteMapping("/del")
	public Result delete(@RequestParam Integer cateId) {
		log.info("要刪除的商品id為: {}", cateId);
		// 1.先查詢刪除分類底下有沒有其他商品
		Integer count = categoryService.findCateId(cateId);
		log.info("該分類底下商品總數: {}",count);
		if(count > 0) {
			return Result.error("該分類底下有其他商品，不能刪除");
		}
		
		categoryService.delete(cateId);
		return Result.success();
	}
	
	/*
	 * 修改分類
	 */
	@PutMapping("/update")
	public Result update(@RequestBody ProCategory s) {
		log.info("修改參數: {},{}", s);
		categoryService.update(s);	
		return Result.success("修改成功");
	}
	
	/*
	 * 啟用/禁用分類
	 */
	
	
	
//	/*
//	 * 根據商品分類id查詢有哪些商品(比如電腦類型，底下有很多分類，筆如主機板、CPU)
//	 */
//	@GetMapping("/list")
//	public Result list(@RequestParam Integer cateId) {
//		/*
//		 *  1.先查詢cateId有無在快取中，這裡定一個規則為商品分類id在redis的key值是[cate_動態id]
//		 *   ，然後實體類要記得implements序列化不然會報錯
//		 */
//		String key = "cate_" + cateId;
//		List<Product> list = (List<Product>)redisTemplate.opsForValue().get(key);
//		if(list != null && list.size() > 0) {
//			// 2.如果有查詢到，就直接返回
//			return Result.success(list);
//		}
//		
//		// 3.若未查詢到，就操作資料庫
//		list = categoryService.list(cateId);
//		log.info("查詢結果: {}", list);
//		
//		/*
//		 *  4.將查詢到之結果放到redis，由於返回的是List集合，在redis是無法取用，惟因有序列化
//		 *    故會先將List轉為redis可儲存的格式後(即redis的String類型)再儲存進去
//		 */
//		redisTemplate.opsForValue().set(key, list);
//		return Result.success(list);
//	}
	
	
	/*
	 * 改良上述API => 使用Spring Cache
	 */
	@Cacheable(cacheNames = "setProduct", key = "#cateId")
	@GetMapping("/list")
	public Result list(@RequestParam Integer cateId) {
		
		List<Product> list = categoryService.list(cateId);
		log.info("查詢結果: {}", list);
		return Result.success(list);
	}
	
}
