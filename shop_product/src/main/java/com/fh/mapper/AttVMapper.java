package com.fh.mapper;

import com.fh.model.po.AttValue;
import com.fh.model.vo.AttValueVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttVMapper {

    List<AttValue> queryAttV(Integer vo);

    void addAttv(AttValue attValue);

    void updateAttv(AttValue attValue);
}
