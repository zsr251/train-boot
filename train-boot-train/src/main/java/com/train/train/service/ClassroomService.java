package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.ClassroomVO;
import com.train.train.query.ClassroomQuery;
import com.train.train.entity.ClassroomEntity;

import java.util.List;

/**
 * 教室
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface ClassroomService extends BaseService<ClassroomEntity> {

    PageResult<ClassroomVO> page(ClassroomQuery query);

    void save(ClassroomVO vo);

    void update(ClassroomVO vo);

    void delete(List<Long> idList);

    List<String> getNameList(List<String> classroomCodeList);
}