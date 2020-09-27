package com.wechatsell.wechatsell.repository;

import com.wechatsell.wechatsell.dataobject.Commodity;
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
public class CommodityRepositoryTest {

    @Autowired
    private CommodityRepository commodityRepository;

    @Test
    public void saveTest() throws Exception{
        Commodity commodity = new Commodity("123456",
                "皮蛋粥",
                new BigDecimal(3.2),
                10,
                "粥里面有皮蛋",
                "http://xxxx.jpg",
                0,
                2);
        Commodity result = commodityRepository.save(commodity);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCommodityStatusTest() throws Exception{
        List<Commodity> list = commodityRepository.findByCommodityStatus(0);
        Assert.assertNotEquals(0,list.size());
    }
}