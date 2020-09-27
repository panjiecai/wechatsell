package com.wechatsell.wechatsell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.wechatsell.wechatsell.DTO.OrderDTO;

/**
 * 支付
 */
public interface PayService {

    PayResponse create(OrderDTO orderDTO);
    PayResponse notify(String nodifyData);
    RefundResponse refund(OrderDTO orderDTO);
}
