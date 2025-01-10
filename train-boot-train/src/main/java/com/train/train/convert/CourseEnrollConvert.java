package com.train.train.convert;

import com.train.train.entity.CourseEnrollEntity;
import com.train.train.vo.CourseEnrollVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 课程报名
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface CourseEnrollConvert {
    CourseEnrollConvert INSTANCE = Mappers.getMapper(CourseEnrollConvert.class);

    CourseEnrollEntity convert(CourseEnrollVO vo);

    CourseEnrollVO convert(CourseEnrollEntity entity);

    List<CourseEnrollVO> convertList(List<CourseEnrollEntity> list);

}