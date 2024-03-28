package com.justshop.pojo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 用戶實體類別
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopUser {
	
	private Integer id;
	private String username;
	private String password;
	private String avatar;
	private String email;
	private LocalDateTime loginDate;
	private Integer status;
	private Integer role;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private String remark;
	

}
