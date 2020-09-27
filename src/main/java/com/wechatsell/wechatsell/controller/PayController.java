package com.wechatsell.wechatsell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.wechatsell.wechatsell.DTO.OrderDTO;
import com.wechatsell.wechatsell.enums.ResultEnum;
import com.wechatsell.wechatsell.exception.SellException;
import com.wechatsell.wechatsell.service.OrderService;
import com.wechatsell.wechatsell.service.PayService;
import com.wechatsell.wechatsell.service.serviceimpl.OrderServiceImpl;
import com.wechatsell.wechatsell.service.serviceimpl.PayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private PayServiceImpl payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                       @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map){
        //1.查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //2.发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);

        return new ModelAndView("pay/create",map);
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String nodifyData){

        payService.notify(nodifyData);

        //返回给微信处理结果
        return new ModelAndView("/pay/seccess");
    }
}
