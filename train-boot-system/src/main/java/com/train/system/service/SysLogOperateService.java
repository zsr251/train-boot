package com.train.system.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.system.entity.SysLogOperateEntity;
import com.train.system.query.SysLogOperateQuery;
import com.train.system.vo.SysLogOperateVO;

/**
 * 操作日志
 *
 */
public interface SysLogOperateService extends BaseService<SysLogOperateEntity> {

    PageResult<SysLogOperateVO> page(SysLogOperateQuery query);
}