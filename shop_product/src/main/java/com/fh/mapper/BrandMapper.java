package com.fh.mapper;

import com.fh.model.po.Brand;
import com.fh.model.vo.BrandVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BrandMapper {
    Integer queryCount(BrandVo vo);

    List<Brand> queryBrand(BrandVo vo);

    void addBrand(Brand brand);

    void gtiById(Integer id);

    void updateBrand(Brand brand);
    @Update("update shop_brand set isDel=1 where id=#{id}")
    void delBrand(Integer id);
}
