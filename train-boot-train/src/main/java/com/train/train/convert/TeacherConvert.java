package com.train.train.convert;

import com.train.train.entity.TeacherEntity;
import com.train.train.vo.TeacherVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 教师
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface TeacherConvert {
    TeacherConvert INSTANCE = Mappers.getMapper(TeacherConvert.class);

    TeacherEntity convert(TeacherVO vo);

    TeacherVO convert(TeacherEntity entity);

    List<TeacherVO> convertList(List<TeacherEntity> list);

}