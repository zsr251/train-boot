package com.train.train.convert;

import com.train.train.entity.TeacherSettlementEntity;
import com.train.train.vo.TeacherSettlementVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 员工工资条
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface TeacherSettlementConvert {
    TeacherSettlementConvert INSTANCE = Mappers.getMapper(TeacherSettlementConvert.class);

    TeacherSettlementEntity convert(TeacherSettlementVO vo);

    TeacherSettlementVO convert(TeacherSettlementEntity entity);

    List<TeacherSettlementVO> convertList(List<TeacherSettlementEntity> list);

}