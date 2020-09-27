package com.wechatsell.wechatsell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品
 */
@Entity
@Data
public class Commodity {

    @Id
    //id
    private String commodityId;

    //商品名称
    private String commodityName;

    //商品价格
    private BigDecimal commodityPrice;

    //商品库存
    private Integer commodityStock;

    //商品描述
    private String commodityDescription;

    //商品图标
    private String commodityIcon;

    //商品状态 0正常 1下架
    private Integer commodityStatus;

    //类型编号
    private Integer sortType;

    public Commodity(){}

    public Commodity(String commodityId,
                     String commodityName,
                     BigDecimal commodityPrice,
                     Integer commodityStock,
                     String commodityDescription,
                     String commodityIcon,
                     Integer commodityStatus,
                     Integer sortType) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.commodityPrice = commodityPrice;
        this.commodityStock = commodityStock;
        this.commodityDescription = commodityDescription;
        this.commodityIcon = commodityIcon;
        this.commodityStatus = commodityStatus;
        this.sortType = sortType;
    }
}
