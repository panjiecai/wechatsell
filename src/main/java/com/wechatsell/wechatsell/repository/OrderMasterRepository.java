package com.wechatsell.wechatsell.repository;

import com.wechatsell.wechatsell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String > {

    OrderMaster findByOrderId(String orderId);
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
