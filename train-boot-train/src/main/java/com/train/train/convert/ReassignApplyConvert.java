package com.train.train.convert;

import com.train.train.entity.ReassignApplyEntity;
import com.train.train.vo.ReassignApplyVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 调课申请
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface ReassignApplyConvert {
    ReassignApplyConvert INSTANCE = Mappers.getMapper(ReassignApplyConvert.class);

    ReassignApplyEntity convert(ReassignApplyVO vo);

    ReassignApplyVO convert(ReassignApplyEntity entity);

    List<ReassignApplyVO> convertList(List<ReassignApplyEntity> list);

}