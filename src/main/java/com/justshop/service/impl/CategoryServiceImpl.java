package com.justshop.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justshop.mapper.CategoryMapper;
import com.justshop.pojo.PageTotal;
import com.justshop.pojo.ProCategory;
import com.justshop.pojo.Product;
import com.justshop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public ProCategory selectCatName(String cateName) {
		ProCategory s = categoryMapper.selectCatName(cateName);
		return s;
	}

	@Override
	public void delete(Integer cateId) {
		categoryMapper.delete(cateId);
	}

	@Override
	public void add(ProCategory s) {
		s.setCreateTime(LocalDateTime.now());
		s.setUpdateTime(LocalDateTime.now());
		s.setProStatus(1);
		categoryMapper.add(s);
	}

	//修改分類
	@Override
	public void update(ProCategory s) {
		s.setUpdateTime(LocalDateTime.now());
		categoryMapper.update(s);
	}

	//查詢分類底下有無其他商品
	@Override
	public Integer findCateId(Integer cateId) {
		Integer count = categoryMapper.findCateId(cateId);
		return count;
	}

	//商品分類列表
	@Override
	public List<Product> list(Integer cateId) {
		List<Product> list = categoryMapper.list(cateId);
		return list;
	}

	//分頁查詢
	@Override
	public PageTotal page(Integer pageNum, Integer pageSize) {
		Long count = categoryMapper.count();
		
		Integer start = (pageNum - 1) * pageSize;
		List<ProCategory> list = categoryMapper.page(start, pageSize);

		PageTotal pageTotal = new PageTotal(count, list);
		return pageTotal;
	}


	

}
