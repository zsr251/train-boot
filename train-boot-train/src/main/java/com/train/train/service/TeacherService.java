package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.TeacherVO;
import com.train.train.query.TeacherQuery;
import com.train.train.entity.TeacherEntity;

import java.util.List;

/**
 * 教师
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface TeacherService extends BaseService<TeacherEntity> {

    PageResult<TeacherVO> page(TeacherQuery query);

    void save(TeacherVO vo);

    void update(TeacherVO vo);

    void delete(List<Long> idList);

    List<String> getNameList(List<String> teacherCodeList);

}