package com.wechatsell.wechatsell.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductVO {

    @JsonProperty("name")//返回前端时的名字
    private String sortName;

    @JsonProperty("type")
    private Integer sortType;

    @JsonProperty("foods")
    private List<CommodityVO> commodityVOList;

    public ProductVO(){}

    public ProductVO(String sortName, Integer sortType, List<CommodityVO> commodityVOList) {
        this.sortName = sortName;
        this.sortType = sortType;
        this.commodityVOList = commodityVOList;
    }
}
