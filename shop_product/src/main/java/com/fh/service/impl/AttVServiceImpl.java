package com.fh.service.impl;

import com.fh.mapper.AttVMapper;
import com.fh.model.po.AttValue;
import com.fh.model.vo.AttValueVo;
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
    public Map queryAttV(AttValueVo vo) {
        Map rs=new HashMap();
       Integer count= vMapper.queryCount(vo);
         rs.put("count",count);
        List<AttValue> list=vMapper.queryAttV(vo);
        rs.put("list",list);
        return rs;
    }
}
