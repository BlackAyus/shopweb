package com.justshop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.justshop.pojo.Cart;
import com.justshop.pojo.CartDTO;

@Mapper
public interface CartMapper {

	//查詢用戶個人購物車的商品id是否有重複
	@Select("select * from cart where user_id = #{userId} and pro_id = #{proId}")
	CartDTO findByUserIdAndProId(CartDTO cart);

	//根據購物車id更新商品數量
	@Update("update cart set acount = #{acount} where cart_id = #{cartId}")
	void updateNumber(Cart c1);

	//新增購物車
	@Insert("insert into cart(user_id,pro_id,pro_name,acount,checked,create_time,amount)"+
	        " values(#{userId},#{proId},#{proName},#{acount},#{checked},#{createTime},#{amount})")
	void add(Cart cart);


	//通用查詢，用動態sql
	List<Cart> list(Cart c);

    //清空購物車
	@Delete("delete from cart where user_id = #{userId}")
	void clear(Integer userId);

}
