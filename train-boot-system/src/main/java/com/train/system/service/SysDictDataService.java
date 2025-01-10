package com.train.system.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.system.entity.SysDictDataEntity;
import com.train.system.query.SysDictDataQuery;
import com.train.system.vo.SysDictDataVO;

import java.util.List;

/**
 * 数据字典
 *
 */
public interface SysDictDataService extends BaseService<SysDictDataEntity> {

    PageResult<SysDictDataVO> page(SysDictDataQuery query);

    void save(SysDictDataVO vo);

    void update(SysDictDataVO vo);

    void delete(List<Long> idList);

}