package com.justshop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.justshop.anno.AutoFill;
import com.justshop.pojo.Cart;
import com.justshop.pojo.OperationType;
import com.justshop.pojo.Product;
import com.justshop.pojo.ProductPageQueryDTO;
import com.justshop.pojo.ProductVo;

@Mapper
public interface ProductMapper {

	//查詢商品全部清單
	List<Product> list();

	//新增商品
	@Insert("insert into product(cate_id,prd_name,prd_mainimage,pro_image,detail,price,stock,status,create_time,update_time,create_id)"
			 + " values(#{cateId},#{prdName},#{prdMainimage},#{proImage},#{detail},#{price},#{stock},#{status},#{createTime},#{updateTime},#{createId})")
    @AutoFill(value = OperationType.INSERT)
	void add(Product p);

	//分頁查詢
	List<ProductVo> pageList(ProductPageQueryDTO ppqd);

	//單筆/批次刪除 => 動態sql
	void del(List<Integer> proId);

	//查詢product表 + catId顯示對應的catName
	@Select("SELECT p.*,c.cate_name FROM product p,procate c WHERE p.cate_id = c.cate_id")
	List<ProductVo> getProductVo();

	
	@AutoFill(value = OperationType.UPDATE)
	void update(Product p);

	//查詢商品表(用動態sql, 可where proId)
	Product getById(Cart c);

	

}
