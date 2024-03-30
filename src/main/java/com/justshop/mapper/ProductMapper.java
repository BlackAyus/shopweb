package com.justshop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.justshop.anno.AutoFill;
import com.justshop.pojo.OperationType;
import com.justshop.pojo.Product;
import com.justshop.pojo.ProductPageQueryDTO;
import com.justshop.pojo.ProductVo;

@Mapper
public interface ProductMapper {

	//查詢商品清單
	@Select("select * from product")
	List<Product> proList();

	//新增商品
	@Insert("insert into product(cate_id,prd_name,prd_mainimage,pro_image,detail,price,stock,status,create_time,update_time)"
			 + " values(#{cateId},#{prdName},#{prdMainimage},#{proImage},#{detail},#{price},#{stock},#{status},#{createTime},#{updateTime})")
    @AutoFill(value = OperationType.INSERT)
	void add(Product p);

	//分頁查詢
	List<ProductVo> pageList(ProductPageQueryDTO ppqd);

}
