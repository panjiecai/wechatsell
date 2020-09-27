package com.wechatsell.wechatsell.service.serviceimpl;

import com.wechatsell.wechatsell.DTO.CartDTO;
import com.wechatsell.wechatsell.dataobject.Commodity;
import com.wechatsell.wechatsell.enums.CommodityStatusEnum;
import com.wechatsell.wechatsell.enums.ResultEnum;
import com.wechatsell.wechatsell.exception.SellException;
import com.wechatsell.wechatsell.repository.CommodityRepository;
import com.wechatsell.wechatsell.service.CommodityRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommodityRepositoryServiceImpl implements CommodityRepositoryService {

    @Autowired
    private CommodityRepository repository;

    @Override
    public Commodity findOne(String commodityId) {
        return repository.findByCommodityId(commodityId);
    }

    @Override
    public List<Commodity> findUpAll() {
        return repository.findByCommodityStatus(CommodityStatusEnum.UP.getCode());
    }

    @Override
    public Page<Commodity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Commodity save(Commodity commodity) {
        return repository.save(commodity);
    }

    @Override
    @Transactional//加库存事务
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList){
            Commodity commodity = repository.findByCommodityId(cartDTO.getCommodityId());
            if(commodity == null){
                throw new SellException(ResultEnum.COMMODITY_NOT_EXIST);
            }
            Integer stockSum = commodity.getCommodityStock() + cartDTO.getCommodityQuantiy();
            commodity.setCommodityStock(stockSum);

            repository.save(commodity);
        }
    }

    @Override
    @Transactional //减库存事务
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO: cartDTOList){
            Commodity commodity = repository.findByCommodityId(cartDTO.getCommodityId());
            if(commodity == null){
                throw new SellException(ResultEnum.COMMODITY_NOT_EXIST);
            }

            Integer result = commodity.getCommodityStock() - cartDTO.getCommodityQuantiy();
            if(result < 0){
                throw new SellException(ResultEnum.COMMODITY_STOCK_ERROR);
            }
            commodity.setCommodityStock(result);
            repository.save(commodity);
        }
    }
}
