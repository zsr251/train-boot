package com.train.train.convert;

import com.train.train.entity.ClassInfoEntity;
import com.train.train.vo.ClassInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 班级
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface ClassInfoConvert {
    ClassInfoConvert INSTANCE = Mappers.getMapper(ClassInfoConvert.class);

    ClassInfoEntity convert(ClassInfoVO vo);

    ClassInfoVO convert(ClassInfoEntity entity);

    List<ClassInfoVO> convertList(List<ClassInfoEntity> list);

}