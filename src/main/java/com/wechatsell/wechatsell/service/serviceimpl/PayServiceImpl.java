package com.wechatsell.wechatsell.service.serviceimpl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.wechatsell.wechatsell.DTO.OrderDTO;
import com.wechatsell.wechatsell.enums.ResultEnum;
import com.wechatsell.wechatsell.exception.SellException;
import com.wechatsell.wechatsell.service.PayService;
import com.wechatsell.wechatsell.utils.JsonUtil;
import com.wechatsell.wechatsell.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO){

        PayRequest payRequest = new PayRequest();

        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信支付】 request={}", JsonUtil.toJson(payRequest));

        PayResponse response = bestPayService.pay(payRequest);

        log.info("【微信支付】 response={}",JsonUtil.toJson(response));
        return response;
    }

    @Override
    public PayResponse notify(String nodifyData){
        //1.验证签名
        //2.支付状态
        //3.支付金额
        //4.支付人和下单人是否相同，取决于业务需求
        PayResponse response = bestPayService.asyncNotify(nodifyData);
        log.info("【微信支付】 异步通知, payResponse={}",response);

        //查询订单
        OrderDTO orderDTO = orderService.findOne(response.getOrderId());

        //判断订单是否存在
        if(orderDTO == null){
            log.error("【微信支付】 异步通知，订单不存在,orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断金额是否一致(0.10 0.1)
        if(!MathUtil.equals(response.getOrderAmount(), orderDTO.getOrderAmount().doubleValue())){
            log.error("【微信支付】 异步通知，订单金额不一致,orderId={},微信通知金额={},系统金额={}",
                    response.getOrderId(),
                    response.getOrderAmount(),
                    orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改订单支付状态
        orderService.paid(orderDTO);

        return response;
    }

    /**
     *
     * 退款
     * @param orderDTO
     * @return
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO){
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信退款】 request={}", JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】 response={}", JsonUtil.toJson(refundResponse));

        return refundResponse;
    }
}
