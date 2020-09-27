package com.wechatsell.wechatsell.service.serviceimpl;

import com.wechatsell.wechatsell.dataobject.Commodity;
import com.wechatsell.wechatsell.enums.CommodityStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommodityRepositoryServiceImplTest {

    @Autowired
    private CommodityRepositoryServiceImpl commodityRepositoryService;

    @Test
    public void findOne() throws Exception{
        Commodity commodity = commodityRepositoryService.findOne("123456");
        Assert.assertEquals("123456",commodity.getCommodityId());
    }

    @Test
    public void findAll() throws Exception{
        List<Commodity> list = commodityRepositoryService.findUpAll();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void testFindAll() throws Exception{
//        Sort sort = Sort.by(Sort.Direction.DESC,"commodity_id");
        //分页
        PageRequest request = PageRequest.of(0,2);
        Page<Commodity> page = commodityRepositoryService.findAll(request);
//        System.out.println(page.getTotalElements());
        Assert.assertNotEquals(0,page.getTotalElements());
    }

    @Test
    public void save() throws Exception{
        Commodity commodity = new Commodity("123457",
                "皮皮虾",
                new BigDecimal(3.2),
                10,
                "皮虾",
                "http://xxxx.jpg",
                CommodityStatusEnum.DOWN.getCode(),
                2);
        Commodity result = commodityRepositoryService.save(commodity);
        Assert.assertNotNull(result);
    }
}