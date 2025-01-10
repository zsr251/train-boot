package com.train.train.convert;

import com.train.train.entity.CourseHourBuyLogEntity;
import com.train.train.vo.CourseHourBuyLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 课时购买记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface CourseHourBuyLogConvert {
    CourseHourBuyLogConvert INSTANCE = Mappers.getMapper(CourseHourBuyLogConvert.class);

    CourseHourBuyLogEntity convert(CourseHourBuyLogVO vo);

    CourseHourBuyLogVO convert(CourseHourBuyLogEntity entity);

    List<CourseHourBuyLogVO> convertList(List<CourseHourBuyLogEntity> list);

}