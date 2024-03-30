package com.justshop.pojo;

import java.io.Serializable;

import lombok.Data;

/*
 * 分頁查詢DTO類別
 */
@Data
public class ProductPageQueryDTO implements Serializable{

	private Integer pageNum;
	private Integer pageSize;
	private String proName;
	
	//分類ID
	private Integer cateId;
	private Integer status;
}
