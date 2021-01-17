package com.fh.controller;

import com.fh.model.po.AttValue;
import com.fh.model.vo.AttValueVo;
import com.fh.model.vo.ResultData;
import com.fh.service.AttVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/value")
@CrossOrigin
public class AttVController {
    @Autowired
    private AttVService vService;

    @GetMapping("getData")
    public ResultData queryAttV(AttValueVo vo){
        if (vo.getCurrPage()==null){
            return ResultData.error(500,"参数错误");
        }
        if (vo.getSize()==null){
            return ResultData.error(500,"参数错误");
        }
        Map list= vService.queryAttV(vo);
        return ResultData.success(list);
    }

    @PostMapping("add")
    public ResultData addAttv(AttValue attValue){
        vService.addAttv(attValue);
        return ResultData.success("");
    }

    @PostMapping("update")
    public ResultData updateAttv(AttValue attValue){
        vService.updateAttv(attValue);
        return ResultData.success("");
    }

}
