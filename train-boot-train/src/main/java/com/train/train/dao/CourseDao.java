package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.CourseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* 课程
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface CourseDao extends BaseDao<CourseEntity> {

    /**
     * 根据课程编码查询
     * @param courseCode 课程编码
     * @return 课程
     */
    @Select("select * from tt_course where deleted = 0 and course_code = #{courseCode} limit 1")
    CourseEntity getByCourseCode(String courseCode);
}