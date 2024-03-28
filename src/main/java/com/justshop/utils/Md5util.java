package com.justshop.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.DigestUtils;

public class Md5util {

	protected static char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
	protected static MessageDigest messageDigest = null;
	
	static {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException n) {
			System.err.println(Md5util.class.getName() + "初始化失敗");
			n.printStackTrace();
		}
		
	}
	public static String getMD5String(String s) {
		return DigestUtils.md5DigestAsHex(s.getBytes());
	}

	
}
