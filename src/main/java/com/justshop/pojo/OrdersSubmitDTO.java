package com.justshop.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 用戶訂單提交儲存類別
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersSubmitDTO implements Serializable{

	   private Integer addId;
	   private Integer payMethod;
	   private String remark;
	   private Integer status;
	   private BigDecimal amount; //總金額
}
