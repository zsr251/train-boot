package com.train.train.convert;

import com.train.train.entity.LeaveApplyEntity;
import com.train.train.vo.LeaveApplyVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 请假申请
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface LeaveApplyConvert {
    LeaveApplyConvert INSTANCE = Mappers.getMapper(LeaveApplyConvert.class);

    LeaveApplyEntity convert(LeaveApplyVO vo);

    LeaveApplyVO convert(LeaveApplyEntity entity);

    List<LeaveApplyVO> convertList(List<LeaveApplyEntity> list);

}