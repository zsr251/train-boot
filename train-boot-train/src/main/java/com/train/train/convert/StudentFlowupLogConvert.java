package com.train.train.convert;

import com.train.train.entity.StudentFlowupLogEntity;
import com.train.train.vo.StudentFlowupLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 跟进记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface StudentFlowupLogConvert {
    StudentFlowupLogConvert INSTANCE = Mappers.getMapper(StudentFlowupLogConvert.class);

    StudentFlowupLogEntity convert(StudentFlowupLogVO vo);

    StudentFlowupLogVO convert(StudentFlowupLogEntity entity);

    List<StudentFlowupLogVO> convertList(List<StudentFlowupLogEntity> list);

}