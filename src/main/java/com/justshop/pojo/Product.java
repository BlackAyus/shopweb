package com.justshop.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 商品實體類
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable{
	
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
	private Integer createId;

}
