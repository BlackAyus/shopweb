package com.justshop.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * 地址類別
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable{

	private Integer addId;    //主鍵id
	private Integer userId;   //用戶id
	private String name;      //姓名
	private String phone;     //手機
	private String adres;     //地址
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private Integer addDefault; //預設地址 1是 0否
}
