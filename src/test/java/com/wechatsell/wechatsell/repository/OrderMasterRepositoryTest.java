package com.wechatsell.wechatsell.repository;

import com.wechatsell.wechatsell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest() throws Exception{
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("潘杰才");
        orderMaster.setBuyerPhone("12345678901");
        orderMaster.setBuyerAddress("517");
        orderMaster.setBuyerOpenid("110110");
        orderMaster.setOrderAmount(new BigDecimal(12.5));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenidTest() throws Exception{
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderMaster> orderMasterPage = repository.findByBuyerOpenid("110110",pageRequest);
        System.out.println(orderMasterPage.getTotalElements());
        Assert.assertNotEquals(0,orderMasterPage.getTotalElements());
    }
}