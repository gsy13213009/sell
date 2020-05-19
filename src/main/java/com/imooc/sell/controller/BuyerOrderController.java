package com.imooc.sell.controller;

import com.imooc.sell.converter.OrderForm2OrderDTOConvert;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.impl.BuyServiceImpl;
import com.imooc.sell.utils.ResultVOUtil;
import com.imooc.sell.vo.ResultVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyServiceImpl buyService;

    // 创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm = {}", orderForm);
            throw new SellException(-1, bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConvert.convert(orderForm);
        if (orderDTO.getOrderDetails().isEmpty()) {
            throw new SellException(-1, "购物车不能为空");
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultVOUtil.success(map);
    }

    // 订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size) {

        if (StringUtils.isEmpty(openid)) {
            throw new SellException(-1, "参数错误");
        }
        PageRequest request = PageRequest.of(page, size);
        Page<OrderDTO> list = orderService.findList(openid, request);
        return ResultVOUtil.success(list.getContent());
    }

    // 订单详情
    @PostMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openId") String openId, @RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            throw new SellException(-1, "参数错误");
        }
        // 使用service处理，简洁
        OrderDTO orderOne = buyService.findOrderOne(openId, orderId);
        return ResultVOUtil.success(orderOne);
    }

    // 取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openId") String openId, @RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(orderId)) {
            throw new SellException(-1, "参数错误");
        }
        buyService.cancelOrder(openId, orderId);
        return ResultVOUtil.success();
    }

}
