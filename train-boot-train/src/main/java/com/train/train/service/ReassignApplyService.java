package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.ReassignApplyVO;
import com.train.train.query.ReassignApplyQuery;
import com.train.train.entity.ReassignApplyEntity;

import java.util.List;

/**
 * 调课申请
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface ReassignApplyService extends BaseService<ReassignApplyEntity> {

    PageResult<ReassignApplyVO> page(ReassignApplyQuery query);

    void save(ReassignApplyVO vo);

    void update(ReassignApplyVO vo);

    void delete(List<Long> idList);
}