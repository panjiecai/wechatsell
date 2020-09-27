package com.wechatsell.wechatsell.repository;

import com.wechatsell.wechatsell.dataobject.Sort;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SortRepositoryTest {

    @Autowired
    private SortRepository repository;

    @Test
    public void findOneTest() throws Exception{
        Sort sort = repository.findById(1);
        System.out.println(sort.toString());
    }

    @Test
    public void saveTest() throws Exception{
        Sort sort = new Sort();
        sort.setSortId(2);
        sort.setSortName("男生最爱");
        sort.setSortType(3);
        repository.save(sort);
    }

    @Test
    public void save2Test() throws Exception{
        Sort sort = repository.findById(1);
        sort.setSortType(9);
        repository.save(sort);
    }

    @Test
    @Transactional //清除
    public void save3Test() throws Exception{
        Sort sort = new Sort("女生最爱",5);
        Sort result = repository.save(sort);

        Assert.assertNotNull(result);
//        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findBySortTypeTest() throws Exception{
        List<Integer> list = Arrays.asList(3,4,9);
        List<Sort> result = repository.findBySortTypeIn(list);

        System.out.println(result.get(0).toString());
        Assert.assertNotEquals(0,result.size());
    }
}