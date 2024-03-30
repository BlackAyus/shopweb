package com.justshop.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.justshop.pojo.Result;

/*
 * 例外處理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
	@ExceptionHandler(Exception.class)
	public Result ex(Exception ex) {
		ex.printStackTrace();
		return Result.error("發生異常，操作失敗!");	
	}
}

