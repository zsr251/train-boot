package com.train.system.service;

import com.train.email.config.EmailConfig;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.system.entity.SysMailConfigEntity;
import com.train.system.query.SysMailConfigQuery;
import com.train.system.vo.SysMailConfigVO;

import java.util.List;

/**
 * 邮件平台
 *
 */
public interface SysMailConfigService extends BaseService<SysMailConfigEntity> {

    PageResult<SysMailConfigVO> page(SysMailConfigQuery query);

    List<SysMailConfigVO> list(Integer platform);

    /**
     * 启用的邮件平台列表
     */
    List<EmailConfig> listByEnable();

    void save(SysMailConfigVO vo);

    void update(SysMailConfigVO vo);

    void delete(List<Long> idList);
}