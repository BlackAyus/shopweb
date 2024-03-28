package com.justshop.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 分頁查詢類別
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageTotal {
    
	//總資料筆數
	public Long total;
	
	//儲存每頁筆數
	public List rows;
	
}
