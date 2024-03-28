package com.justshop.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.justshop.pojo.ShopUser;

@Mapper
public interface UserMapper {
    //查詢帳號密碼否正確
	@Select("select * from shopuser where username=#{username} and password=#{password}")
	ShopUser selectUser(ShopUser user);

	//查詢username
	@Select("select * from shopuser where username=#{username}")
	ShopUser findUsername(String username);

	//註冊帳號
	@Insert("insert into shopuser values(null,#{username},#{password},#{avatar},#{email},#{loginDate},"
			+"#{status},#{role},#{createTime},#{updateTime},#{remark})")
	void register(ShopUser user);

}
