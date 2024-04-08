package com.justshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import lombok.extern.slf4j.Slf4j;

/*
 * redis設定類別
 */
@Slf4j
public class RedisConfiguration {

//	@Bean
//	public RedisTemplate redisTemplate(RedisConnectionFactory rcf) {
//		RedisTemplate rt = new RedisTemplate();		
//		//設定redis連接工廠物件
//		rt.setConnectionFactory(rcf);
//		
//		//設定redis key序列化
//		rt.setKeySerializer(new StringRedisSerializer());
//		
//		return rt;
//	}
	
	@Autowired
	private StringRedisTemplate srt;
	
	public void test() {
	   	
		ValueOperations<String, String> opsForValue = srt.opsForValue();
		HashOperations<String, Object, Object> opsForHash = srt.opsForHash();
		ListOperations<String, String> opsForList = srt.opsForList();
		SetOperations<String, String> opsForSet = srt.opsForSet();
		ZSetOperations<String, String> opsForZSet = srt.opsForZSet();
	}
}
