package com.justshop.utils;

/*
 * ThreadLocal工具類別
 */
public class TheradLocalUitls {
    //建立ThreadLocal物件
	private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();
	
	//根據T取得值
	public static <T> T get() {
		return (T) THREAD_LOCAL.get();
	}
	//儲存K值
	public static void set(Object value) {THREAD_LOCAL.set(value);}
	
	//清除ThreadLocal防止內存洩漏
	public static void remove() {THREAD_LOCAL.remove();}
}
