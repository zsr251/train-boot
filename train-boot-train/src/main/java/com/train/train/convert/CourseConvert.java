package com.train.train.convert;

import com.train.train.entity.CourseEntity;
import com.train.train.vo.CourseVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 课程
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface CourseConvert {
    CourseConvert INSTANCE = Mappers.getMapper(CourseConvert.class);

    CourseEntity convert(CourseVO vo);

    CourseVO convert(CourseEntity entity);

    List<CourseVO> convertList(List<CourseEntity> list);

}