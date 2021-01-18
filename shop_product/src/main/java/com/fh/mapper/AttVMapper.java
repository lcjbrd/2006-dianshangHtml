package com.fh.mapper;

import com.fh.model.po.AttValue;
import com.fh.model.po.ProductType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AttVMapper {

    List<AttValue> queryAttV(Integer vo);

    void addAttv(AttValue attValue);

    void updateAttv(AttValue attValue);
    @Select("select * from shop_type where isDel=0")
    List<ProductType> queryType();


}
