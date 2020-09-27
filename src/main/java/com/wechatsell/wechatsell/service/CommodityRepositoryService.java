package com.wechatsell.wechatsell.service;

import com.wechatsell.wechatsell.DTO.CartDTO;
import com.wechatsell.wechatsell.dataobject.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 */
public interface CommodityRepositoryService {

    //按id查询
    Commodity findOne(String commodityId);

    //查询所有在卖商品
    List<Commodity> findUpAll();

    //管理端
    Page<Commodity> findAll(Pageable pageable);

    //存储
    Commodity save(Commodity commodity);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

}
