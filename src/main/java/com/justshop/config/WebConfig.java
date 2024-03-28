package com.justshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.justshop.interceptor.LoginCheckInterceptor;

/*
 * 透過@Configuration將此類別定義為[配置(設定)類別]，可取代xml
 */

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	//注入
	@Autowired
	private LoginCheckInterceptor lcInterceptor;
   
	/*呼叫addInterceptor()方法註冊攔截器，並用
	 *addPathPatterns()方法設定"/**" => 代表所有請求都要攔截
	 *excludePathPatterns() => 設定/login不攔截
	 */ 
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(lcInterceptor).addPathPatterns("/**").excludePathPatterns("/shop/login","/user/register");
	}
}
