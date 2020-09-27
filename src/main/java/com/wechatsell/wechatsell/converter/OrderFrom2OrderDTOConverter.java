package com.wechatsell.wechatsell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wechatsell.wechatsell.DTO.OrderDTO;
import com.wechatsell.wechatsell.dataobject.OrderDetail;
import com.wechatsell.wechatsell.enums.ResultEnum;
import com.wechatsell.wechatsell.exception.SellException;
import com.wechatsell.wechatsell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class OrderFrom2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO =new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try{
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}
                    .getType());
        }catch (Exception e){
            log.error("【对象转换】 错误, string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
