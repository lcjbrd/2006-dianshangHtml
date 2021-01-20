package com.fh.controller;

import com.fh.model.po.AttValue;
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
    public ResultData queryAttV(Integer id){

        Map list= vService.queryAttV(id);
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

    @GetMapping("type")
    public ResultData queryType(){
       Map list=  vService.queryType();
       return ResultData.success(list);
    }


    @GetMapping("getAttvById")
    public ResultData getAttvById(Integer id){
        return ResultData.success(vService.getAttvById(id));

    }

}
