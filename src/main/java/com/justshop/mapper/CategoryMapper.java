package com.justshop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.justshop.anno.AutoFill;
import com.justshop.pojo.OperationType;
import com.justshop.pojo.ProCategory;
import com.justshop.pojo.Product;

@Mapper
public interface CategoryMapper {

	//查詢商品分類
	@Select("select * from procate where cate_name = #{cateName}")
	ProCategory selectCatName(String cateName);

	//根據id刪除商品
	@Delete("delete from procate where cate_id = #{cateId}")
	void delete(Integer cateId);

	//新增商品分類
    @Insert("insert into procate(cate_name,create_time,update_time,pro_status) values(#{cateName},#{createTime},#{updateTime},#{proStatus}) ")
	@AutoFill(value = OperationType.INSERT)
    void add(ProCategory s);

	//修改分類
    @AutoFill(value = OperationType.UPDATE)
	void update(ProCategory s);

	//查詢該分類底下有無其他商品
	@Select("select count(*) from product where cate_id = #{cateId}")
	Integer findCateId(Integer cateId);

	//根據商品類型查詢有哪些商品
	@Select("select * from product where cate_id = #{cateId}")
	List<Product> list(Integer cateId);

	//取得資料總數
	@Select("select count(*) from procate")
	Long count();

	//用Limit查詢
	@Select("SELECT * FROM procate LIMIT #{start},#{pageSize}")
	List<ProCategory> page(Integer start, Integer pageSize);

	//用動態sql
	List<Product> pageList(Integer cateId, Integer proId);


}
