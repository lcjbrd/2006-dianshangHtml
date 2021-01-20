package com.fh.service;

import com.fh.model.po.AttValue;

import java.util.List;
import java.util.Map;

public interface AttVService {
    Map queryAttV(Integer id);

    void addAttv(AttValue attValue);

    void updateAttv(AttValue attValue);

    Map queryType();

    List<AttValue> getAttvById(Integer id);
}
