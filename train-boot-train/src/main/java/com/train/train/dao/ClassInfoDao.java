package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.ClassInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* 班级
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface ClassInfoDao extends BaseDao<ClassInfoEntity> {
    @Select("select * from tt_class_info where deleted = 0 and class_code = #{classCode} limit 1")
	ClassInfoEntity getByClassCode(String classCode);
}