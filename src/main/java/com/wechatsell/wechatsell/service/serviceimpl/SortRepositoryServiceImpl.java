package com.wechatsell.wechatsell.service.serviceimpl;

import com.wechatsell.wechatsell.dataobject.Sort;
import com.wechatsell.wechatsell.repository.SortRepository;
import com.wechatsell.wechatsell.service.SortRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类型
 */
@Service
public class SortRepositoryServiceImpl implements SortRepositoryService {

    @Autowired
    private SortRepository repository;

    @Override
    public Sort findOne(int sortId) {
        return repository.findById(sortId);
    }

    @Override
    public List<Sort> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Sort> findBySortTypeIn(List<Integer> sortTypeList) {
        return repository.findBySortTypeIn(sortTypeList);
    }

    @Override
    public Sort save(Sort sort) {
        return repository.save(sort);
    }
}
