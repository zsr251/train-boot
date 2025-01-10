package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.TeacherEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* 教师
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface TeacherDao extends BaseDao<TeacherEntity> {
    /**
     * 根据教师编码查询
     */
    @Select("select * from tt_teacher where deleted = 0 and teacher_code = #{teacherCode} limit 1")
    TeacherEntity getByTeacherCode(String teacherCode);
}