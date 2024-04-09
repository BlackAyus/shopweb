package com.justshop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.justshop.anno.AutoFill;
import com.justshop.pojo.Address;
import com.justshop.pojo.OperationType;

@Mapper
public interface AddressMapper {

	//根據id查詢地址
	@Select("select * from address where user_id = #{userId}")
	List<Address> list(Integer userId);

	//新增地址
	@Insert("insert into address values(null,#{userId},#{name},#{phone},#{adres},#{createTime},#{updateTime},#{addDefault})")
	@AutoFill(value = OperationType.INSERT)
	void add(Address address);

	//修改地址
	@AutoFill(value = OperationType.UPDATE)
	void update(Address address);

	//刪除地址
	@Delete("delete from address where add_id = #{addId}")
	void del(Integer addId);

	//將用戶的所設定地址全部改為0
	@Update("update address set add_default = #{addDefault} where user_id = #{userId}")
	void updateAddDefaultByUserId(Address address);

}
