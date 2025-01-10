package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.TeacherSettlementVO;
import com.train.train.query.TeacherSettlementQuery;
import com.train.train.entity.TeacherSettlementEntity;

import java.util.List;

/**
 * 员工工资条
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
public interface TeacherSettlementService extends BaseService<TeacherSettlementEntity> {

    PageResult<TeacherSettlementVO> page(TeacherSettlementQuery query);

    void save(TeacherSettlementVO vo);

    void update(TeacherSettlementVO vo);

    void delete(List<Long> idList);

    TeacherSettlementVO calcPayroll(String teacherCode, String startDate, String endDate, String payDate);

    void pay(TeacherSettlementVO vo);
}