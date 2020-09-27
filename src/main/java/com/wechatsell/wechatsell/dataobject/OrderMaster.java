package com.wechatsell.wechatsell.dataobject;

import com.wechatsell.wechatsell.enums.OrderStatusEnum;
import com.wechatsell.wechatsell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate //自动更新时间
public class OrderMaster {
    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    //支付状态 默认为未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    //创建时间
    private Date createTime;

    //更改时间
    private Date updateTime;
}
