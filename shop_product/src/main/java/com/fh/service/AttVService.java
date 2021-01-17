package com.fh.service;

import com.fh.model.po.AttValue;
import com.fh.model.vo.AttValueVo;

import java.util.Map;

public interface AttVService {
    Map queryAttV(AttValueVo vo);

    void addAttv(AttValue attValue);

    void updateAttv(AttValue attValue);
}
