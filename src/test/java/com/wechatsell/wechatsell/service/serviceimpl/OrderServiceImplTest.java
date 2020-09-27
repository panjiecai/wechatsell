package com.wechatsell.wechatsell.service.serviceimpl;

import com.wechatsell.wechatsell.DTO.OrderDTO;
import com.wechatsell.wechatsell.dataobject.OrderDetail;
import com.wechatsell.wechatsell.dataobject.OrderMaster;
import com.wechatsell.wechatsell.enums.OrderStatusEnum;
import com.wechatsell.wechatsell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private static String OPENID = "110110";
    private static String ORDER_ID = "160050718335767825";

    @Test
    public void createTest() throws Exception{

        OrderDTO orderDTO =new OrderDTO();
        orderDTO.setBuyerAddress("517");
        orderDTO.setBuyerName("潘杰才");
        orderDTO.setBuyerOpenid(OPENID);
        orderDTO.setBuyerPhone("12345678901");

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setCommodityId("123457");
        o1.setCommodityQuantity(2);
        orderDetailList.add(o1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】 result={}", result);
    }

    @Test
    public void findOneTest() throws Exception{
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("【查询订单】 result={}",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    public void findListTest() throws Exception{
        PageRequest pageRequest =PageRequest.of(0,2);
        Page<OrderDTO> result = orderService.findList(OPENID,pageRequest);
        Assert.assertNotEquals(0,result);
        log.info("【订单列表】 result={}",result.getContent());
    }

    @Test
    public void cancelTest() throws Exception{
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finishTest() throws Exception{
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paidTest() throws Exception{
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }
}