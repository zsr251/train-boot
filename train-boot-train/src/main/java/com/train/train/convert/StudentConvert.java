package com.train.train.convert;

import com.train.train.entity.StudentEntity;
import com.train.train.vo.StudentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 学员
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface StudentConvert {
    StudentConvert INSTANCE = Mappers.getMapper(StudentConvert.class);

    StudentEntity convert(StudentVO vo);

    StudentVO convert(StudentEntity entity);

    List<StudentVO> convertList(List<StudentEntity> list);

}