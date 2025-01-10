package com.train.train.convert;

import com.train.train.entity.ClassroomEntity;
import com.train.train.vo.ClassroomVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 教室
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface ClassroomConvert {
    ClassroomConvert INSTANCE = Mappers.getMapper(ClassroomConvert.class);

    ClassroomEntity convert(ClassroomVO vo);

    ClassroomVO convert(ClassroomEntity entity);

    List<ClassroomVO> convertList(List<ClassroomEntity> list);

}