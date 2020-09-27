package com.wechatsell.wechatsell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;

    //订单id
    private String orderId;

    //商品id
    private String commodityId;

    //商品名称
    private String commodityName;

    //商品价格
    private BigDecimal commodityPrice;

    //商品数量
    private Integer commodityQuantity;

    //商品图标
    private String commodityIcon;
}
