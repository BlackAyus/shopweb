package com.justshop.utils;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * Jwt工具類別
 */
public class JwtUtils {
	/*
	 * 1. 設定密鑰為itheima
	 * 2. 設定過期時間12小時
	 */
	private static String signKey = "itheima";
	private static Long exprie = 43200000L;
	
    //生成令牌
	public static String generateJwt(Map<String, Object> claims) {
		/* 1. builder() => 生成令牌
		 * 2. signWith(SignatureAlgorithm alg, String) => 設定數字簽名算法
		 *     (1)選擇HS256算法
		 *     (2)指定加入的密鑰
		 * 3. setClaims(Map<String, Object>) => 設定要存放的用戶資料數據，我們選擇
		 *      Map集合(前面已經用戶資訊封裝到Map)
		 * 4. setExpiration(Date) => 設定令牌有效時間，假設要1小時 
		 * 5. compact() => 取得令牌的內容  
		 */
		String jwt = Jwts.builder()
			  .addClaims(claims)
			  .signWith(SignatureAlgorithm.HS256, signKey)
			  .setExpiration(new Date(System.currentTimeMillis() + exprie))
			  .compact();
	    return jwt;		  
	}	
	/*
	 * 1. parser() => 解析令牌(驗證處理)
	 * 2. setSigningKey(String s) => 指定解析的密鑰(我們設定是ithem)
	 * 3. parseClaimsJws(String) => 解析令牌內容
	 * 4. getBody() => 取得令牌第二部分(自定義的用戶訊息內容)
	 */
	public static Claims parseJWT(String jwt) {
		Claims claims = (Claims) Jwts.parser()
				.setSigningKey(signKey)
				.parse(jwt)
				.getBody();
		return claims;
	}
	
	

}
