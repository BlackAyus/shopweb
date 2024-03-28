package com.justshop.pojo;

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
public class Product {
	
	private Integer proId;
	private Integer cateId;
	private String proName;
	private String proMainimage;
	private String proImage;
	private String detail;
	private Double price;
	private Integer stock;
	private Integer status;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;

}
