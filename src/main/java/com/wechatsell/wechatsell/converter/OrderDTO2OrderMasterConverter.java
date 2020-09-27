package com.wechatsell.wechatsell.converter;

import com.wechatsell.wechatsell.DTO.OrderDTO;
import com.wechatsell.wechatsell.dataobject.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

//转换器
public class OrderDTO2OrderMasterConverter {

    public static OrderMaster convert(OrderDTO orderDTO){
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        return orderMaster;
    }

    public static List<OrderMaster> convert(List<OrderDTO> orderDTOList){
        //lambda表达式
        //return orderDTOList.stream().map(e -> convert(e)).collect(Collectors.toList());
        return orderDTOList.stream().map(OrderDTO2OrderMasterConverter::convert).collect(Collectors.toList());
    }
}
