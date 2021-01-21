package com.fh.mapper;

import com.fh.model.po.Product;
import com.fh.model.vo.ProductVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Insert("insert  into shop_product  (name,title,brandId,typeId,productdecs,price,imgPath,stocks,sortNum,createDate,updateDate,author,isDel) value " +
            "(#{name},#{title},#{brandId},#{typeId},#{productdecs},#{price},#{imgPath},#{stocks},#{sortNum},#{createDate},#{updateDate},#{author},#{isDel})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addProduct(Product product);

    Integer queryCount(ProductVo vo);

    List<Product> queryProduct(ProductVo vo);


    @Select("select * from shop_product where id=#{id}")
    void getProductById(Integer id);
   @Update("update shop_product set name=#{name},title=#{title},brandId=#{brandId},typeId=#{typeId},productdecs=#{productdecs},price=#{price},imgPath=#{imgPath},stocks=#{stocks},sortNum=#{sortNum},createDate=#{createDate},updateDate=#{updateDate},author=#{author},isDel=#{isDel} where id=#{id}")
    void updateProduct(Product product);
    @Update("update shop_product set isDel=1 where id=#{id}")
    void delProduct(Integer id);
}
