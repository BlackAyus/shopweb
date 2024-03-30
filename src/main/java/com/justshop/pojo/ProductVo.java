package com.justshop.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/*
 * 返回給前端
 */
@Data
public class ProductVo implements Serializable{

	private Integer proId;
	private Integer cateId;
	private String prdName;
	private Double price;
	private String prdMainimage;
	private String proImage;
	private String detail;
	private Integer stock;
	private Integer status;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	
    private String cateName;
}
