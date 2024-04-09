package com.justshop.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 購物車實體類
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable{

	private Integer cartId;
	private Integer userId;
	private Integer proId;
	private String proName;
	private Integer acount;
	private Integer checked;
	private LocalDateTime createTime;
	private Double amount;
}
