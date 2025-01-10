package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.ClassStudentVO;
import com.train.train.query.ClassStudentQuery;
import com.train.train.entity.ClassStudentEntity;

import java.util.List;

/**
 * 班级学员
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface ClassStudentService extends BaseService<ClassStudentEntity> {

    PageResult<ClassStudentVO> page(ClassStudentQuery query);

    void save(ClassStudentVO vo);

    void update(ClassStudentVO vo);

    void delete(List<Long> idList);
}