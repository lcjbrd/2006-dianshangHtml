package com.fh.service.impl;

import com.fh.mapper.ProductAttrDatasMapper;
import com.fh.mapper.ProductMapper;
import com.fh.model.po.Product;
import com.fh.model.po.ProductAttrData;
import com.fh.model.vo.ProductVo;
import com.fh.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductAttrDatasMapper ProductAttrDataMapper;



    @Override
    @Transactional  //加上事务   什么是事务
    public void addProduct(Product product,String attr,String sku) {
        product.setUpdateDate(new Date());
        product.setUpdateDate(new Date());
        product.setAuthor("admin");
        //返回主键
        productMapper.addProduct(product);
        // 声明属性数据的对象
        List<ProductAttrData> adList=new ArrayList<>();
        //批量新增
        //将attr的json数组字符串 转为json数组对象
        JSONArray objects = JSONObject.parseArray(attr);
        for (int i = 0; i <objects.size() ; i++) {
            //构建属性数据对象
            ProductAttrData temp=new ProductAttrData();
            //设置对应的商品id
            temp.setProId(product.getId());
            temp.setAttrData(objects.get(i).toString());
            //放入集合
            adList.add(temp);
        }

        //将sku的json数组字符串 转为json数组对象
        JSONArray objectssku = JSONObject.parseArray(sku);
        for (int i = 0; i <objectssku.size() ; i++) {
            //得到具体一个json对象
            JSONObject dataJs= (JSONObject) objectssku.get(i);
            //构建属性数据对象
            ProductAttrData temp=new ProductAttrData();
            //设置对应的商品id
            temp.setProId(product.getId());
            temp.setPrice(dataJs.getDouble("pricess"));
            temp.setStorcks(dataJs.getInteger("storcks"));
            dataJs.remove("price");
            dataJs.remove("storck");
            temp.setAttrData(objectssku.get(i).toString());
            //放入集合
            adList.add(temp);
        }
        //批量新增
        ProductAttrDataMapper.adds(adList);
    }

    @Override
    public Map queryProduct(ProductVo vo) {
        Map rs=new HashMap();
        Integer count = productMapper.queryCount(vo);
        rs.put("count",count);
      List<Product> list =  productMapper.queryProduct(vo);
      rs.put("list",list);
        return rs;
    }

    @Override
    public void getProductById(Integer id) {
        productMapper.getProductById(id);
    }

    @Override
    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public void delProduct(Integer id) {
        productMapper.delProduct(id);


    }
}
