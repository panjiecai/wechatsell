package com.wechatsell.wechatsell.service;

import com.wechatsell.wechatsell.dataobject.Sort;

import java.util.List;
/**
 *类型表
 */
public interface SortRepositoryService {

    //通过id寻找
    Sort findOne(int sortId);

    //全部
    List<Sort> findAll();

    //通过编号查询
    List<Sort> findBySortTypeIn(List<Integer> sortTypeList);

    //存储
    Sort save(Sort sort);
}
