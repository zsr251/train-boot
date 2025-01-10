package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.ClassInfoVO;
import com.train.train.query.ClassInfoQuery;
import com.train.train.entity.ClassInfoEntity;

import java.util.List;

/**
 * 班级
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface ClassInfoService extends BaseService<ClassInfoEntity> {

    PageResult<ClassInfoVO> page(ClassInfoQuery query);

    void save(ClassInfoVO vo);

    void update(ClassInfoVO vo);

    void delete(List<Long> idList);

    List<String> getNameList(List<String> classCodeList);

}