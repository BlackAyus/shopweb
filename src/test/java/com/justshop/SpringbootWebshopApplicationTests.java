package com.justshop;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import com.alibaba.fastjson.JSONObject;

@SpringBootTest
class SpringbootWebshopApplicationTests {

	@Autowired
	private StringRedisTemplate  srt;
	
	
	public void test() {   	
		ValueOperations<String, String> opsForValue =  srt.opsForValue();
		HashOperations<String, Object, Object> opsForHash =  srt.opsForHash();
		ListOperations<String, String> opsForList =  srt.opsForList();
		SetOperations<String, String> opsForSet =  srt.opsForSet();
		ZSetOperations<String, String> opsForZSet =  srt.opsForZSet();
		
		opsForValue.set("id", "7414", 1, TimeUnit.MINUTES);
		String s = opsForValue.get("id");
		System.out.println(s);
		
		opsForHash.put("1", "name", "ming");
	}
	
	
	public void hashTest() {
		HashOperations<String, Object, Object> opsForHash =  srt.opsForHash();
		//加入元素
		opsForHash.put("1", "name", "ming");
		opsForHash.put("1", "age", "18");
		//取得key的值
		opsForHash.get("1", "name");
		//取得key裡面全部欄位，返回一個Set
		Set keys = opsForHash.keys("1");
		//取得Key裡面全部value，返回一個List
		List values = opsForHash.values("1");
		//刪除
		opsForHash.delete("1", "name");
	}

	
	public void hashList() {
		ListOperations<String, String> opsForList =  srt.opsForList();
		//從左插入元素，可加單筆leftPush(key,String)、數筆leftPushAll(key,String[])
		opsForList.leftPushAll("mylist", "a","b","c");
		opsForList.leftPush("mylist", "d");
		//查詢list某範圍的元素
		List list = opsForList.range("mylist", 1, -1);
		//從list表右側刪除一個元素
		opsForList.rightPop("mylist");
		//list表長度
		opsForList.size("mylist");
	}
	
	public void hashSet() {
		SetOperations<String, String> opsForSet =  srt.opsForSet();
	    //向指定集合加入元素
		opsForSet.add("myset", "z","x","y");
		opsForSet.add("myset2", "z","a","y");
		//取得某集合的元素 => 返回Set
		opsForSet.members("myset");
		//取得集合元素的個數
		opsForSet.size("myset");
		//計算集合交集 => 返回Set
		opsForSet.intersect("myset", "mylist2");
		//計算集合並集
		opsForSet.union("myset", "myset2");
		//刪除
		opsForSet.remove("myset", "z","x");
	}
	
	public void hashZSet() {
		ZSetOperations<String, String> opsForZSet =  srt.opsForZSet();
	    //將元素設定分數並插入add(key, String, double score)
		opsForZSet.add("zset1", "a", 7.5);
		opsForZSet.add("zset1", "b", 6.0);
		opsForZSet.add("zset1", "c", 9.5);
		//取得一定範圍的元素 => 返回Set
		opsForZSet.range("zset1", 0, -1);
		//為指定元素加分數
		opsForZSet.incrementScore("zset1", "b", 3.0);
        //刪除
		opsForZSet.remove("zset", "a");
	}
	
	
	public void testCommon() {
		//取得所有key => 返回Set
		Set keys = srt.keys("*");
		//查詢key是否存在 => 返回boolean
		srt.hasKey("mylist");
		//輸出所有key，並透過type.name()看是何種型態
		for(Object o: keys) {
		   DataType type= srt.type((String)o);
		   System.out.println(type.name());
		}
		//刪除某key
		srt.delete("zset1");
	}
	
	
	//@Test
	public void testGET() throws ClientProtocolException, IOException {
		//建立HttpClient物件
		CloseableHttpClient httpClient= HttpClients.createDefault();
		
		//建立請求物件
		 HttpGet httpGet = new HttpGet("http://localhost:8080//shop/user/status");
		 
		//發送請求(execute(HttpUriRequest request))，並接收
		
		CloseableHttpResponse cr = httpClient.execute(httpGet);
		
		//取得伺服器端狀態碼
		int statuscode = cr.getStatusLine().getStatusCode();
		System.out.println(statuscode);
		
		//取得具體回應的數據
		HttpEntity entity = cr.getEntity();
		String body=  EntityUtils.toString(entity);
		System.out.println(body);
		
		//關閉資源
		cr.close();
		httpClient.close();
		
	}
	
	@Test
	public void testPOST() throws Exception{
		//建立HttpClient物件
		CloseableHttpClient httpClient= HttpClients.createDefault();
		
		/*
		 * 建立請求物件，由於是POST要給予參數，至於要怎麼給?
		 *  1.setEntity(參數:HttpEntity物件)
		 *  2.建立HttpEntity，他本身是一個介面，我們選擇其實作類為StringEntity
		 *  3.StringEntity(String參數) => 裡面放JOSN格式，但這樣寫太麻煩，故
		 *  4.用一個JSONObject(com.alibaba.fastjson.JSONObject)封裝
		 *  5.將json.toString轉字串放到StringEntity(裡面)
		 *  6.指定請求編碼
		 *  7.指定傳輸格式
		 */
		 HttpPost httpPost = new HttpPost("http://localhost:8080//shop/user//login");
         JSONObject json = new JSONObject();
         json.put("username", "maxadmin");
         json.put("password", "123456");
		 StringEntity se = new StringEntity(json.toString());
		 httpPost.setEntity(se);
		 se.setContentEncoding("utf-8");
		 se.setContentType("application/json");
		//發送請求(execute(HttpUriRequest request))，並接收
		
		CloseableHttpResponse cr = httpClient.execute(httpPost);
		
		//取得伺服器端狀態碼
		int statuscode = cr.getStatusLine().getStatusCode();
		System.out.println(statuscode);
		
		//取得具體回應的數據，由於是封裝HttpEntity物件，故用EntityUtils解析
		HttpEntity entity = cr.getEntity();
		String body=  EntityUtils.toString(entity);
		System.out.println(body);
		
		//關閉資源
		cr.close();
		httpClient.close();
		
	}
}
