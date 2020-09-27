package com.wechatsell.wechatsell.repository;

import com.wechatsell.wechatsell.dataobject.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity,String> {

    Commodity findByCommodityId(String commodityId);
    List<Commodity> findByCommodityStatus(Integer commodityStatus);
}
