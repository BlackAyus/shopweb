package com.justshop.controller.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justshop.pojo.Address;
import com.justshop.pojo.Result;
import com.justshop.service.AddressService;
import com.justshop.utils.ThreadLocalUitls;

import lombok.extern.slf4j.Slf4j;

/*
 * 地址管理控制層
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/shop/address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	//根據用戶id查詢地址，用於回顯等功能
	@GetMapping("/list")
	public Result showAddress() {
		Map<String, Object> map = ThreadLocalUitls.get();
		Integer userId = (Integer)map.get("id");
		List<Address> list = addressService.showAddress(userId);
		return Result.success(list);
	}
	
	//新增地址
	@PostMapping("/add")
	public Result add(@RequestBody Address address) {
		log.info("地址參數為: {}", address);
		Map<String, Object> map = ThreadLocalUitls.get();
		Integer userId = (Integer)map.get("id");
		address.setUserId(userId);
		addressService.add(address);
		return Result.success();
	}
	
	//修改地址
	@PutMapping("/update")
	public Result update(@RequestBody Address address) {
		log.info("修改參數為: {}", address);
		Map<String, Object> map = ThreadLocalUitls.get();
		Integer userId = (Integer)map.get("id");
		address.setUserId(userId);
		addressService.update(address);
		return Result.success();
	}
	
	//刪除地址
	@DeleteMapping("/del")
	public Result del(@RequestParam Integer addId) {
		log.info("接收到的刪除id為: {}", addId);
		addressService.del(addId);
		return Result.success();
	}
	
	//設定預設地址 => 接收地址id(addId)將其改為預設地址
	@PostMapping("/default")
	public Result setDefault(@RequestBody Address address) {
		Map<String, Object> map = ThreadLocalUitls.get();
		Integer userId = (Integer)map.get("id");
		address.setUserId(userId);
		addressService.setDefault(address);
		return Result.success();
	}
}
