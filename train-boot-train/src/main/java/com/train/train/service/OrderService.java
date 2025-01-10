package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.entity.OrderEntity;
import com.train.train.query.OrderPriceQuery;
import com.train.train.query.OrderQuery;
import com.train.train.vo.OrderVO;

import java.math.BigDecimal;

public interface OrderService extends BaseService<OrderEntity> {
    PageResult<OrderVO> page(OrderQuery query);

    void save(OrderVO vo);

    void update(OrderVO vo);

    OrderVO getVOById(Long orderId);

    OrderPriceQuery calcOrderPrice(OrderPriceQuery query);

    OrderVO createOrder(OrderVO order);

    void pay(Long orderId, BigDecimal serviceFee);

    void cancel(Long orderId);
}
