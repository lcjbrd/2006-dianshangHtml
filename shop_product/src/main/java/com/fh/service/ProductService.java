package com.fh.service;

import com.fh.model.po.Product;
import com.fh.model.vo.ProductVo;

import java.util.Map;

public interface ProductService {
    void addProduct(Product product ,String attr,String sku);

    Map queryProduct(ProductVo vo);

    void getProductById(Integer id);

    void updateProduct(Product product);

    void delProduct(Integer id);
}
