package com.fh.controller;

import com.fh.model.po.Product;
import com.fh.model.vo.ProductVo;
import com.fh.model.vo.ResultData;
import com.fh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/product/")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("add")
    public ResultData addProduct(Product product,String attr,String sku){
        productService.addProduct(product,attr,sku);
        return   ResultData.success("");
    }

    @GetMapping("getData")
    public  ResultData queryProduct(ProductVo vo){
        Map list=productService.queryProduct(vo);
        return ResultData.success(list);
    }

    @GetMapping("getById")
    public ResultData getProductById(Integer id){
        productService.getProductById(id);
        return ResultData.success("");
    }

    @PostMapping("update")
    public ResultData updateProduct(Product product){
        productService.updateProduct(product);
        return ResultData.success("");
    }

    @DeleteMapping("del")
    public ResultData deleteProduct(Integer id){
        productService.delProduct(id);
         return  ResultData.success("");
    }
}
