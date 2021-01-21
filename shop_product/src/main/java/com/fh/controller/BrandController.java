package com.fh.controller;

import com.fh.model.po.Brand;
import com.fh.model.vo.BrandVo;
import com.fh.model.vo.ResultData;
import com.fh.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/brand/")
@CrossOrigin
public class BrandController {
   @Autowired
    private BrandService brandService;

    @PostMapping("getData")
    public ResultData queryBrand(BrandVo vo){
        if (vo.getSize()==null){
            ResultData.error(400,"数据不合法");
        }
        if (vo.getCurrPage()==null){
            ResultData.error(400,"数据不合法");
        }
       Map list =  brandService.queryBrand(vo);
         return ResultData.success(list);
   }

   @PostMapping("add")
    private ResultData addBrand(Brand brand){
        brandService.addBrand(brand);
       return ResultData.success("");
   }

    @GetMapping("getById")
    public ResultData gtiById(Integer id){
       brandService.gtiById(id);
        return  ResultData.success(id);
    }

    @PostMapping("update")
    public ResultData updateBrand(Brand brand){
        brandService.updateBrand(brand);
       return   ResultData.success("");
    }
    @DeleteMapping("del")
    public ResultData delBrand(Integer id){
        brandService.delBrand(id);
       return   ResultData.success("");
    }

}
