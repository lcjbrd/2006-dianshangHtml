package com.fh.service.impl;

import com.fh.mapper.AttVMapper;
import com.fh.model.po.AttValue;
import com.fh.model.po.ProductType;
import com.fh.service.AttVService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttVServiceImpl implements AttVService {
    @Resource
    private AttVMapper vMapper;

    @Override
    public Map queryAttV(Integer id) {
        Map rs=new HashMap();

        List<AttValue> list=vMapper.queryAttV(id);
        rs.put("list",list);
        return rs;
    }

    @Override
    public void addAttv(AttValue attValue) {
        vMapper.addAttv(attValue);
    }

    @Override
    public void updateAttv(AttValue attValue) {
        vMapper.updateAttv(attValue);
    }

    @Override
    public Map queryType() {
        Map rs=new HashMap();
        List<ProductType> list=vMapper.queryType();
        rs.put("list",list);
        return rs;
    }

    @Override
    public List<AttValue> getAttvById(Integer id) {

        return vMapper.getAttvById(id);
    }


}
