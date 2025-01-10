package com.train.train.convert;

import com.train.train.entity.ClassCourseEntity;
import com.train.train.vo.ClassCourseVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 班级课程
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface ClassCourseConvert {
    ClassCourseConvert INSTANCE = Mappers.getMapper(ClassCourseConvert.class);

    ClassCourseEntity convert(ClassCourseVO vo);

    ClassCourseVO convert(ClassCourseEntity entity);

    List<ClassCourseVO> convertList(List<ClassCourseEntity> list);

}