package com.train.train.convert;

import com.train.train.entity.StudentCourseHourEntity;
import com.train.train.vo.StudentCourseHourVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 学员课程课时
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface StudentCourseHourConvert {
    StudentCourseHourConvert INSTANCE = Mappers.getMapper(StudentCourseHourConvert.class);

    StudentCourseHourEntity convert(StudentCourseHourVO vo);

    StudentCourseHourVO convert(StudentCourseHourEntity entity);

    List<StudentCourseHourVO> convertList(List<StudentCourseHourEntity> list);

}