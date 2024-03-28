package com.justshop.interceptor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.justshop.pojo.Result;
import com.justshop.utils.JwtUtils;
import com.justshop.utils.TheradLocalUitls;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor{

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	//目標資源[方法執行(指的是控制層的方法)]前執行, 返回true代表放行，反之攔截而不放行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
        
        
        //1. 取得請求的url(論點同Filter)
        String url = request.getRequestURL().toString();
        log.info("請求的url: {}", url);
        
        //2.判斷請求中是否包含login, 若包含則說明是登入操作，直接return true放行
        if(url.contains("login")) {
        	log.info("登入操作, 故放行...");
        	return true;
        }
        
        //3.取得請求頭(Header)的令牌
        String jwt = request.getHeader("Token");
        
        /*
		 * 4.判斷令牌是否存在，若[不存在]則返回錯誤結果，這裡要使用springboot提供的工具類別StringUtils內的hasLength()方法
		 *  ，可判斷[該令牌是否有長度，若沒有代表沒令牌]，並依照API規則返回一個Not_Login。最後，要注意一件事，[返回格式仍須為JSON]
		 *  ，之前在控制層有@ResponseBody註解可轉JSON，但這裡沒有，故要手動轉換，這裡可使用阿里提供的依賴(fastJson)，
		 *  然後取得JSON字串notLogin，接著用rep的getWriter().write(notLogin)回應notLogin給前端，一樣要return不執行下述 
		 */
        if(!StringUtils.hasLength(jwt)) {
			log.info("請求頭為null，返回未登入訊息");
			Result error = Result.error("Not_Login");
			String notLogin = JSONObject.toJSONString(error);
			response.getWriter().write(notLogin);
			return false;
		}
        
        //5.令牌驗證(道理同Filter)
        try {
        	//將JWT丟到redis中看是否存在(若有存在，會取出當時我們登入儲存的JWT給redisJwt)，若為空直接拋Exception給catch
        	ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        	String redisJwt = vo.get(jwt);
        	log.info("請求的jwt傳遞到redis查看是否有值(空值代表沒有該令牌): {}", redisJwt);
        	if(redisJwt == null) {
        		throw new RuntimeException();
        	}
			Map<String, Object> claims = JwtUtils.parseJWT(jwt);
			
			//將解析過後的令牌存放到ThreadLocal物件
			TheradLocalUitls.set(claims);
		} catch (Exception e) { 
			e.printStackTrace();
			log.info("解析令牌失敗，返回錯誤結果");
			//返回前端一個狀態碼401(401通常是代表權限不足，大部分是jwt令牌失效情形)
			response.setStatus(401);
			Result error = Result.error("error:401, Not_Login");
			String notLogin = JSONObject.toJSONString(error);
			log.info("401錯誤!，返回給客戶端: {}", notLogin);
			response.getWriter().write(notLogin);
			return false;	
		}
 
        
        //6.放行
        log.info("令牌合法，故放行");
		return true;
	}

	//控制層方法執行後才會執行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
        System.out.println("post....執行了");
         
	}
    
	//最後執行的方法
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			    Object handler, Exception ex)throws Exception {
		TheradLocalUitls.remove();
	}
}
