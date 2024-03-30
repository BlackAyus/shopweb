package com.justshop.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.justshop.pojo.OperationType;

/*
 * 自訂註解，用於自動補滿公共欄位
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoFill {
	
	//資料庫操作類型 value()代表ENUM欄位的值，我們目前設定有INSERT,UPDATE
	OperationType value();

}
