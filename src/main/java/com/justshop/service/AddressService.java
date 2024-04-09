package com.justshop.service;

import java.util.List;

import com.justshop.pojo.Address;

public interface AddressService {

	List<Address> showAddress(Integer userId);

	void add(Address address);

	void update(Address address);

	void del(Integer addId);

	void setDefault(Address address);

}
