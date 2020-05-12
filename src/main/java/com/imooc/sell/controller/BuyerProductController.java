package com.imooc.sell.controller;

import com.imooc.sell.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @GetMapping("/list")
    public ResultVO list() {
        ResultVO<Object> objectResultVO = new ResultVO<>();
        objectResultVO.setCode(0);
        objectResultVO.setMsg("成功");
        return objectResultVO;
    }

}
