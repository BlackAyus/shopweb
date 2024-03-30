package com.justshop.pojo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 商品分類實體類
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProCategory {

	private Integer cateId;
	private String cateName;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private Integer proStatus;
}
