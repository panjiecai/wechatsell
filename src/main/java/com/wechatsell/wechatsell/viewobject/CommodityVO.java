package com.wechatsell.wechatsell.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CommodityVO {

    @JsonProperty("id")
    private String commodityId;

    @JsonProperty("name")
    private String commodityName;

    @JsonProperty("price")
    private BigDecimal commodityPrice;

    @JsonProperty("description")
    private String commodityDescription;

    @JsonProperty("icon")
    private String commodityIcon;
}
