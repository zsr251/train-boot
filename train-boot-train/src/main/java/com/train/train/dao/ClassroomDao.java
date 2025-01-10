package com.train.train.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.train.entity.ClassroomEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* 教室
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Mapper
public interface ClassroomDao extends BaseDao<ClassroomEntity> {
    /**
     * 根据教室编码查询
     * @param classroomCode 教室编码
     * @return 教室
     */
    @Select("select * from tt_classroom where deleted = 0 and classroom_code = #{classroomCode} limit 1")
    ClassroomEntity getByClassroomCode(String classroomCode);

    /**
     * 跟进班级容量查询可用教室列表
     * @param capacity 最大容量
     * @return
     */
    @Select("select * from tt_classroom where deleted = 0 and classroom_status = 'normal' and capacity >= #{capacity}")
    List<ClassroomEntity> getByCapacity(Integer capacity);
}