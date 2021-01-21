package com.fh.mapper;

import com.fh.model.po.ProductAttrData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ProductAttrDatasMapper {
    @Insert("<script>insert into shop_product_value (proId,attrData,storcks,price) " +
            "values <foreach collection='list' item='a' separator=','> ( #{a.proId},#{a.attrData},#{a.storcks},#{a.price} )  </foreach> </script>")
    void adds(List<ProductAttrData> adList);
}
