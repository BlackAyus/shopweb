package com.justshop.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 返回給前端的訂單明細
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersSubmitVO implements Serializable{

	private Integer orId;
	private String orNumber;
	private Double amount;
	private LocalDateTime orTime;
}
