package com.train.train.convert;

import com.train.train.entity.ClassStudentEntity;
import com.train.train.vo.ClassStudentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 班级学员
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface ClassStudentConvert {
    ClassStudentConvert INSTANCE = Mappers.getMapper(ClassStudentConvert.class);

    ClassStudentEntity convert(ClassStudentVO vo);

    ClassStudentVO convert(ClassStudentEntity entity);

    List<ClassStudentVO> convertList(List<ClassStudentEntity> list);

}