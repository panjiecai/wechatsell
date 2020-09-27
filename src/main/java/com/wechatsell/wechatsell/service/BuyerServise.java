package com.wechatsell.wechatsell.service;

import com.wechatsell.wechatsell.DTO.OrderDTO;

public interface BuyerServise {

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
