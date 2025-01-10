package com.train.train.convert;

import com.train.train.entity.CourseHourDepleteLogEntity;
import com.train.train.vo.CourseHourDepleteLogVO;
import com.train.train.vo.CourseHourDepleteVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 消课记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface CourseHourDepleteLogConvert {
    CourseHourDepleteLogConvert INSTANCE = Mappers.getMapper(CourseHourDepleteLogConvert.class);

    CourseHourDepleteLogEntity convert(CourseHourDepleteLogVO vo);

    CourseHourDepleteLogEntity convert(CourseHourDepleteVO vo);

    CourseHourDepleteLogVO convert(CourseHourDepleteLogEntity entity);

    List<CourseHourDepleteLogVO> convertList(List<CourseHourDepleteLogEntity> list);

}