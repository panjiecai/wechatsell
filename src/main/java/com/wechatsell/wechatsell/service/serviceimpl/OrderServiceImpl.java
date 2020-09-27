package com.wechatsell.wechatsell.service.serviceimpl;

import com.wechatsell.wechatsell.DTO.CartDTO;
import com.wechatsell.wechatsell.DTO.OrderDTO;
import com.wechatsell.wechatsell.converter.OrderDTO2OrderMasterConverter;
import com.wechatsell.wechatsell.converter.OrderMaster2OrderDTOConverter;
import com.wechatsell.wechatsell.dataobject.Commodity;
import com.wechatsell.wechatsell.dataobject.OrderDetail;
import com.wechatsell.wechatsell.dataobject.OrderMaster;
import com.wechatsell.wechatsell.enums.OrderStatusEnum;
import com.wechatsell.wechatsell.enums.PayStatusEnum;
import com.wechatsell.wechatsell.enums.ResultEnum;
import com.wechatsell.wechatsell.exception.SellException;
import com.wechatsell.wechatsell.repository.OrderDetailRepository;
import com.wechatsell.wechatsell.repository.OrderMasterRepository;
import com.wechatsell.wechatsell.service.CommodityRepositoryService;
import com.wechatsell.wechatsell.service.OrderService;
import com.wechatsell.wechatsell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CommodityRepositoryService commodityRepositoryService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayServiceImpl payService;

    @Override
    @Transactional //事务 库存不足时回滚
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();//随机id
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);//总价初始值为0

        //1.查询商品：库存，价格
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            Commodity commodity = commodityRepositoryService.findOne(orderDetail.getCommodityId());
            if(commodity == null) {
                throw new SellException(ResultEnum.COMMODITY_NOT_EXIST);
            }
            //2.计算订单总价
            orderAmount = commodity.getCommodityPrice()
                    .multiply(new BigDecimal(orderDetail.getCommodityQuantity()))
                    .add(orderAmount);
            //订单详情入库
            BeanUtils.copyProperties(commodity, orderDetail);//相同字段拷贝
            orderDetail.setDetailId(KeyUtil.genUniqueKey());//随机id
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }

        //3.写入数据库：orderMaster和orderDerail
        orderDTO.setOrderId(orderId);
        OrderMaster orderMaster = OrderDTO2OrderMasterConverter.convert(orderDTO);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4.扣库存
        List<CartDTO> cartDTOList = new ArrayList<>();
            //lambda表达式 取id和数量
        cartDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(e -> new CartDTO(e.getCommodityId(), e.getCommodityQuantity()))
                .collect(Collectors.toList());
        commodityRepositoryService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.findByOrderId(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = OrderMaster2OrderDTOConverter.convert(orderMaster);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getNumberOfElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确，orderId={},status={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = OrderDTO2OrderMasterConverter.convert(orderDTO);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(!updateResult.equals(orderMaster)){
            log.error("【取消订单】 更新失败：orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】 订单在无商品详情， orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        //lambda表达式
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(e -> new CartDTO(e.getCommodityId(),e.getCommodityQuantity()))
                .collect(Collectors.toList());
        commodityRepositoryService.increaseStock(cartDTOList);

        //已支付则退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderDTO);
        }

     return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {


        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】 订单状态不正确，orderId={},status={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【完结订单】 订单支付状态不正确，orderId={}",orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_PAY_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = OrderDTO2OrderMasterConverter.convert(orderDTO);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(!updateResult.equals(orderMaster)){
            log.error("【完结订单】 更新失败：orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单支付】 订单状态不正确，orderId={},status={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付】 订单支付状态不正确，orderId={}",orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_PAY_ERROR);
        }
        //修改订单的支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = OrderDTO2OrderMasterConverter.convert(orderDTO);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(!updateResult.equals(orderMaster)){
            log.error("【订单支付】 更新失败：orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}
