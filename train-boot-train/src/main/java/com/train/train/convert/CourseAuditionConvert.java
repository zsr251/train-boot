package com.train.train.convert;

import com.train.train.entity.CourseAuditionEntity;
import com.train.train.vo.CourseAuditionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 课程试听
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface CourseAuditionConvert {
    CourseAuditionConvert INSTANCE = Mappers.getMapper(CourseAuditionConvert.class);

    CourseAuditionEntity convert(CourseAuditionVO vo);

    CourseAuditionVO convert(CourseAuditionEntity entity);

    List<CourseAuditionVO> convertList(List<CourseAuditionEntity> list);

}