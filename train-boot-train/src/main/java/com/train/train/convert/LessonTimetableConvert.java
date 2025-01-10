package com.train.train.convert;

import com.train.train.entity.LessonTimetableEntity;
import com.train.train.vo.LessonTimetableVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 课程计划
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface LessonTimetableConvert {
    LessonTimetableConvert INSTANCE = Mappers.getMapper(LessonTimetableConvert.class);

    LessonTimetableEntity convert(LessonTimetableVO vo);

    LessonTimetableVO convert(LessonTimetableEntity entity);

    List<LessonTimetableVO> convertList(List<LessonTimetableEntity> list);

}