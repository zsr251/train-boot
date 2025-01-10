package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.LeaveApplyVO;
import com.train.train.query.LeaveApplyQuery;
import com.train.train.entity.LeaveApplyEntity;

import java.util.List;

/**
 * 请假申请
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface LeaveApplyService extends BaseService<LeaveApplyEntity> {

    PageResult<LeaveApplyVO> page(LeaveApplyQuery query);

    void save(LeaveApplyVO vo);

    void update(LeaveApplyVO vo);

    void delete(List<Long> idList);
}