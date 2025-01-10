package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.StudentFlowupLogVO;
import com.train.train.query.StudentFlowupLogQuery;
import com.train.train.entity.StudentFlowupLogEntity;

import java.util.List;

/**
 * 跟进记录
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface StudentFlowupLogService extends BaseService<StudentFlowupLogEntity> {

    PageResult<StudentFlowupLogVO> page(StudentFlowupLogQuery query);

    void save(StudentFlowupLogVO vo);

    void update(StudentFlowupLogVO vo);

    void delete(List<Long> idList);
}