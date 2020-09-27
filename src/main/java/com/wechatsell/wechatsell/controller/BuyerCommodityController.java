package com.wechatsell.wechatsell.controller;

import com.wechatsell.wechatsell.dataobject.Commodity;
import com.wechatsell.wechatsell.dataobject.Sort;
import com.wechatsell.wechatsell.service.CommodityRepositoryService;
import com.wechatsell.wechatsell.service.SortRepositoryService;
import com.wechatsell.wechatsell.utils.ResultVOUtil;
import com.wechatsell.wechatsell.viewobject.CommodityVO;
import com.wechatsell.wechatsell.viewobject.ProductVO;
import com.wechatsell.wechatsell.viewobject.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 *
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerCommodityController {

    @Autowired
    private CommodityRepositoryService commodityRepositoryService;

    @Autowired
    private SortRepositoryService sortRepositoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //1，查询所有商品
        List<Commodity> commodityList = commodityRepositoryService.findUpAll();

        //2，查询类目（一次性查询）
        List<Integer> sortTypelist = new ArrayList<>();
        //lambda表达式（java8） 相当于遍历commodityList
        sortTypelist = commodityList.stream()
                .map(e -> e.getSortType())
                .collect(Collectors.toList());
        List<Sort> sortList = sortRepositoryService.findBySortTypeIn(sortTypelist);

        //3，数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for( Sort sort: sortList){
            List<CommodityVO> commodityVOList = new ArrayList<>();
            for(Commodity commodity: commodityList){
                if(commodity.getSortType().equals(sort.getSortType())){
                    CommodityVO commodityVO = new CommodityVO();
                    BeanUtils.copyProperties(commodity,commodityVO);//BeanUtail 拷贝相同字段
                    commodityVOList.add(commodityVO);
                }
            }
            ProductVO productVO = new ProductVO(sort.getSortName(),sort.getSortType(),commodityVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
