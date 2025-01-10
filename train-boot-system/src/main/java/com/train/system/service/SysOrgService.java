package com.train.system.service;

import com.train.framework.mybatis.service.BaseService;
import com.train.system.entity.SysOrgEntity;
import com.train.system.vo.SysOrgVO;

import java.util.List;

/**
 * 机构管理
 *
 */
public interface SysOrgService extends BaseService<SysOrgEntity> {

    List<SysOrgVO> getList();

    void save(SysOrgVO vo);

    void update(SysOrgVO vo);

    void delete(Long id);

    /**
     * 根据机构ID，获取子机构ID列表(包含本机构ID)
     *
     * @param id 机构ID
     */
    List<Long> getSubOrgIdList(Long id);

    /**
     * 根据机构ID列表，获取机构名称列表
     *
     * @param idList 机构ID列表
     */
    List<String> getNameList(List<Long> idList);
}