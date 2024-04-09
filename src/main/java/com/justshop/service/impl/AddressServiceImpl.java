package com.justshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justshop.mapper.AddressMapper;
import com.justshop.pojo.Address;
import com.justshop.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressMapper addressMapper;
	
	//查詢用戶地址
	@Override
	public List<Address> showAddress(Integer userId) {
		List<Address> list = addressMapper.list(userId);
		return list;
	}

	//新增地址
	@Override
	public void add(Address address) {
		/*先判斷地址有無重複...功能待續
		Integer userId = address.getUserId();
		List<Address> list = addressMapper.list(userId);
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getAdres().equals(address.getAdres())) {		
			}
		}*/  	
		address.setAddDefault(0);
		addressMapper.add(address);
	}

	//修改地址
	@Override
	public void update(Address address) {	
		addressMapper.update(address);
	}

	//刪除地址
	@Override
	public void del(Integer addId) {
		addressMapper.del(addId);
	}

	//設定預設地址
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void setDefault(Address address) {
		//由於預設地址只能唯一，所以先將所有地址改為0，接著再針對特定地址改為預設，兩者操作要一致，故使用事務
		address.setAddDefault(0);
		addressMapper.updateAddDefaultByUserId(address);
		
		//上述會將所有地址為0，接著我們再設定特定地址為1
		address.setAddDefault(1);
		addressMapper.update(address);
	}
}
