package com.wechatsell.wechatsell.converter;

import com.wechatsell.wechatsell.DTO.OrderDTO;
import com.wechatsell.wechatsell.dataobject.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

//转换器
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        //lambda表达式
        //return orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
        return orderMasterList.stream().map(OrderMaster2OrderDTOConverter::convert).collect(Collectors.toList());
    }
}
