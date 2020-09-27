package com.wechatsell.wechatsell.enums;

import lombok.Getter;

/*
商品状态 枚举
 */
@Getter
public enum CommodityStatusEnum {

    UP(0,"上架"),
    DOWN(1,"下架")
    ;

    private Integer code;

    private String message;

    CommodityStatusEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }

}
