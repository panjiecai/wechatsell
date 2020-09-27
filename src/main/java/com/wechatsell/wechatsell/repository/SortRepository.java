package com.wechatsell.wechatsell.repository;

import com.wechatsell.wechatsell.dataobject.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SortRepository extends JpaRepository<Sort,Integer> {

    public Sort findById(int sortId);
    public List<Sort> findBySortTypeIn(List<Integer> sortTypeList);
}
