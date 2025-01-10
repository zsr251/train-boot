package com.train.system.dao;

import com.train.framework.mybatis.dao.BaseDao;
import com.train.system.entity.SysDictDataEntity;
import com.train.system.vo.SysDictVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 字典数据
 *

 */
@Mapper
public interface SysDictDataDao extends BaseDao<SysDictDataEntity> {

    @Select("${sql}")
    List<SysDictVO.DictData> getListForSql(@Param("sql") String sql);

    @Select("${sql}")
    List<Map<String,Object>> getListMapForSql(@Param("sql") String sql);
}
