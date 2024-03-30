package com.justshop.aop;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.justshop.anno.AutoFill;
import com.justshop.pojo.OperationType;
import com.justshop.utils.AutoFillConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect        //當前類別設定AOP
public class AutoFillPublicFields {

	 /*
	  * 設定切入點，攔截mapper包底下所有類別/介面, 方法, 參數，然後並非所有都要攔截，比如查詢, 刪除我們不需要，
	  * 故在&& 我們新增的註解，設定只有[更新、加入]會被攔截
	  */
	 @Pointcut("execution(* com.justshop.mapper.*.*(..)) && @annotation(com.justshop.anno.AutoFill) ")
	 public void autoPointCut(){}
	 
	 //設定通知
	 @Before("autoPointCut()")
	 public void autoFill(JoinPoint joinPoint) {
		 log.info("開始AutoFill...");
		 
		 /*
		  *  1.先取得當前被攔截之方法資料庫操作類型
		  *   (1)該資料操作類型我們已經在Mapper中註解他是何種，關鍵是要如何取得[AutoFill物件]?
		  *   (2)joinPoint.getSignature() => 取得該方法名並向下轉型MethodSignature(他是子介面)
		  *   (3)因為向下轉型後，可取得該方法，再透過反射，取得註解物件
		  *   (4)autoFill.value() => 就可以取得ENUM的屬性
		  */
         MethodSignature signature = (MethodSignature)joinPoint.getSignature();
         AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
         OperationType ot = autoFill.value();
         
		 /*
		  *  2.取得當前被攔截方法之所有參數:
		  *   (1)先統一約定: 要取得實體類別物件，將該參數放在方法第一位
		  *   (2)joinPoint.getArgs() => 取得所有參數
		  *   (3)先做基本debug，當args為空、或陣列長度為0時，直接return
		  *   (4)基於上述約定，我們取第一順位參數，即args[0]，然後指派給Object，
		  *     這裡要注意，不要直接強轉某個實體類別物件，因為這是不確定概念
		  */
		 Object[] args = joinPoint.getArgs();
		 if(args == null || args.length == 0) {
			 return;
		 }
		 Object o = args[0];
		 // 3.準備要自動填充的資料數據
		 LocalDateTime now = LocalDateTime.now();		 
		 
		 // 4.根據當前不同的資料庫操作類型，進行指派(填充)
		 if(ot == OperationType.UPDATE) {
			 
		 try {
			//通過反射取得setCreateTime方法
			Method setUpdateTime = o.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
			
			//開始對欄位指派值
			setUpdateTime.invoke(o, now);
		 } catch (Exception e) {
			e.printStackTrace();
		 } 
		 }else if(ot == OperationType.INSERT) {
			 try {
				Method setCreateTime = o.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
				Method setUpdateTime = o.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
				setCreateTime.invoke(o, now);
				setUpdateTime.invoke(o, now);
			} catch (Exception e) {	
				e.printStackTrace();
			} 
		 }
	 }
}
