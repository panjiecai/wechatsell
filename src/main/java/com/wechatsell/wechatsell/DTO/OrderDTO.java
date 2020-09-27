package com.wechatsell.wechatsell.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wechatsell.wechatsell.dataobject.OrderDetail;
import com.wechatsell.wechatsell.enums.OrderStatusEnum;
import com.wechatsell.wechatsell.enums.PayStatusEnum;
import com.wechatsell.wechatsell.serializer.Data2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//订单  数据传输对象
@Data
//@JsonSerialize
//@JsonInclude(JsonInclude.Include.NON_NULL) //不返回不必须的字段
public class OrderDTO {

    private String orderId;

    //买家名称
    private String buyerName;

    //买家电话
    private String buyerPhone;

    //卖家地址
    private String buyerAddress;

    //买家微信
    private String buyerOpenid;

    //订单总金额
    private BigDecimal orderAmount;

    //订单状态 默认为新下单
    private Integer orderStatus;

    //支付状态 默认为未支付
    private Integer payStatus;

    //创建时间  毫秒精度改为秒精度
    @JsonSerialize(using = Data2LongSerializer.class)
    private Date createTime;

    //更改时间
    @JsonSerialize(using = Data2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
    //private List<OrderDetail> orderDetailList = new ArrayList<>();
}
