package com.justshop.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
	private Integer code;   //1 成功, 0 失敗
	private String message;     //提示內容
	private Object data;    //資料數據
    
	public static Result success(Object data) {
		return new Result(1,"sucess",data);
	}
	public static Result success() {
		return new Result(1,"sucess",null);
	}
	public static Result error(String mag) {
		return new Result(0, mag, null);
	}
	
	
	@Override
	public String toString() {
		return "Result [code=" + code + ", mag=" + message + ", data=" + data + "]";
	}

}
