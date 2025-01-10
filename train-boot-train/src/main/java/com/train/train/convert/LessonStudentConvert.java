package com.train.train.convert;

import com.train.train.entity.LessonStudentEntity;
import com.train.train.vo.LessonStudentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 课程计划学员
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface LessonStudentConvert {
    LessonStudentConvert INSTANCE = Mappers.getMapper(LessonStudentConvert.class);

    LessonStudentEntity convert(LessonStudentVO vo);

    LessonStudentVO convert(LessonStudentEntity entity);

    List<LessonStudentVO> convertList(List<LessonStudentEntity> list);

}