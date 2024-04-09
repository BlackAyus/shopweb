package com.justshop.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 購物車接收類
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO implements Serializable{
	
	private Integer cartId;
	private Integer proId;

}
