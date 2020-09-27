package com.wechatsell.wechatsell.DTO;


import lombok.Data;

/**
 * 购物车
 */
@Data
public class CartDTO {

    //商品id
    private String commodityId;

    //商品数量
    private Integer commodityQuantiy;

    public CartDTO(String commodityId, Integer commodityQuantiy) {
        this.commodityId = commodityId;
        this.commodityQuantiy = commodityQuantiy;
    }
}
