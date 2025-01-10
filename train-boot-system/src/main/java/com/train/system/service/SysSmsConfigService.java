package com.train.system.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.sms.config.SmsConfig;
import com.train.system.entity.SysSmsConfigEntity;
import com.train.system.query.SysSmsConfigQuery;
import com.train.system.vo.SysSmsConfigVO;

import java.util.List;

/**
 * 短信配置
 *
 */
public interface SysSmsConfigService extends BaseService<SysSmsConfigEntity> {

    PageResult<SysSmsConfigVO> page(SysSmsConfigQuery query);

    List<SysSmsConfigVO> list(Integer platform);

    /**
     * 启用的短信平台列表
     */
    List<SmsConfig> listByEnable();

    void save(SysSmsConfigVO vo);

    void update(SysSmsConfigVO vo);

    void delete(List<Long> idList);

}