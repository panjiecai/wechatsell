package com.wechatsell.wechatsell.repository;

import com.wechatsell.wechatsell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("123456");
        orderDetail.setDetailId("12121");
        orderDetail.setCommodityQuantity(2);
        orderDetail.setCommodityPrice(new BigDecimal(3.2));
        orderDetail.setCommodityName("皮皮虾");
        orderDetail.setCommodityId("123457");
        orderDetail.setCommodityIcon("icon");

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOrderIdTest() throws Exception{
        List<OrderDetail> result = repository.findByOrderId("123456");
        Assert.assertNotEquals(0,result.size());
    }
}