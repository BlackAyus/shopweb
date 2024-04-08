package com.justshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.justshop.mapper.ProductMapper;
import com.justshop.pojo.PageTotal;
import com.justshop.pojo.Product;
import com.justshop.pojo.ProductPageQueryDTO;
import com.justshop.pojo.ProductVo;
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

	//新增商品
	@Override
	public void add(Product p) {
		productMapper.add(p);
	}

	//分頁查詢
	@Override
	public PageTotal pageList(ProductPageQueryDTO ppqd) {
		PageHelper.startPage(ppqd.getPageNum(), ppqd.getPageSize());
		List<ProductVo> list = productMapper.pageList(ppqd);
		Page<ProductVo> page = (Page<ProductVo>)list;
		return new PageTotal(page.getTotal(),page.getResult());
	}

	//刪除商品
	@Override
	public void del(List<Integer> proId) {
	   productMapper.del(proId);	
	}

	@Override
	public List<ProductVo> getProdoctVo() {
		List<ProductVo> list = productMapper.getProductVo();
		return list;
	}

	@Override
	public void update(Product p) {
		productMapper.update(p);	
	}	
}
