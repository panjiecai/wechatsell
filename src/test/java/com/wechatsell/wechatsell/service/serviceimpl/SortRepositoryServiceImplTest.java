package com.wechatsell.wechatsell.service.serviceimpl;

import com.wechatsell.wechatsell.dataobject.Sort;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SortRepositoryServiceImplTest {

    @Autowired SortRepositoryServiceImpl sortRepositoryService;

    @Test
    public void findOne() throws Exception{
        Sort sort = sortRepositoryService.findOne(1);
        Assert.assertEquals(new Integer(1),sort.getSortId());
        System.out.println(sort.toString());
    }

    @Test
    public void findAll() throws Exception {
        List<Sort> list = sortRepositoryService.findAll();
        System.out.println(list.get(0).toString()+list.get(1).toString()+list.get(2).toString());
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findBySortTypeIn() throws Exception {
        List<Sort> list = sortRepositoryService.findBySortTypeIn(Arrays.asList(3,5,9));
        System.out.println(list.get(0).toString()+list.get(1).toString()+list.get(2).toString());
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void save() throws Exception {
        Sort sort = new Sort("18上",2);
        Sort result = sortRepositoryService.save(sort);
        Assert.assertNotNull(result);
    }
}