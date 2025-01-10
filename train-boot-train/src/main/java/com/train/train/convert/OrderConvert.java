package com.train.train.convert;

import com.train.train.entity.OrderEntity;
import com.train.train.vo.OrderVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderConvert {
    OrderConvert INSTANCE = Mappers.getMapper(OrderConvert.class);

    OrderEntity convert(OrderVO vo);

    OrderVO convert(OrderEntity vo);
}
