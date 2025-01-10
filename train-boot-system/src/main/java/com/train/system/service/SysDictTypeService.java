package com.train.system.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.system.entity.SysDictTypeEntity;
import com.train.system.query.SysDictTypeQuery;
import com.train.system.vo.SysDictTypeVO;
import com.train.system.vo.SysDictVO;

import java.util.List;

/**
 * 数据字典
 *
 */
public interface SysDictTypeService extends BaseService<SysDictTypeEntity> {

    PageResult<SysDictTypeVO> page(SysDictTypeQuery query);

    List<SysDictTypeVO> list(Long pid);

    void save(SysDictTypeVO vo);

    void update(SysDictTypeVO vo);

    void delete(List<Long> idList);

    /**
     * 获取动态SQL数据
     */
    List<SysDictVO.DictData> getDictSql(Long id);

    /**
     * 获取全部字典列表
     */
    List<SysDictVO> getDictList();

    /**
     * 获取指定字典类型字典列表
     * @param dictType 字典类型
     * @return
     */
    SysDictVO getDictVO(String dictType);

    /**
     * 刷新字典redis缓存
     * @param dictType
     */
    void refreshRedisCache(String dictType);

    /**
     * 刷新字典本地缓存
     */
    void refreshLocalCache();
}