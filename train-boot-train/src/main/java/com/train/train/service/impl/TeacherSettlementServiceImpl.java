package com.train.train.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.train.framework.dict.utils.TransUtils;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.convert.TeacherSettlementConvert;
import com.train.train.entity.TeacherSettlementEntity;
import com.train.train.query.TeacherSettlementQuery;
import com.train.train.vo.TeacherSettlementVO;
import com.train.train.dao.TeacherSettlementDao;
import com.train.train.service.TeacherSettlementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 员工工资条
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class TeacherSettlementServiceImpl extends BaseServiceImpl<TeacherSettlementDao, TeacherSettlementEntity> implements TeacherSettlementService {

    @Override
    public PageResult<TeacherSettlementVO> page(TeacherSettlementQuery query) {
        IPage<TeacherSettlementEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        List<TeacherSettlementVO> voList = TeacherSettlementConvert.INSTANCE.convertList(page.getRecords());
        return new PageResult<>(TransUtils.transList(voList, TeacherSettlementVO.class), page.getTotal());
    }

    private LambdaQueryWrapper<TeacherSettlementEntity> getWrapper(TeacherSettlementQuery query) {
        LambdaQueryWrapper<TeacherSettlementEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotEmpty(query.getSettlementName()), TeacherSettlementEntity::getSettlementName, query.getSettlementName());
        wrapper.eq(query.getSettlementDate() != null, TeacherSettlementEntity::getSettlementDate, query.getSettlementDate());
        wrapper.eq(StringUtils.isNotEmpty(query.getTeacherCode()), TeacherSettlementEntity::getTeacherCode, query.getTeacherCode());
        wrapper.eq(query.getPayDate() != null, TeacherSettlementEntity::getPayDate, query.getPayDate());
        wrapper.eq(StringUtils.isNotEmpty(query.getProcessor()), TeacherSettlementEntity::getProcessor, query.getProcessor());
        return wrapper;
    }

    @Override
    public void save(TeacherSettlementVO vo) {
        TeacherSettlementEntity entity = TeacherSettlementConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(TeacherSettlementVO vo) {
        TeacherSettlementEntity entity = TeacherSettlementConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public TeacherSettlementVO calcPayroll(String teacherCode, String startDate, String endDate, String payDate) {
        return null;
    }

    @Override
    public void pay(TeacherSettlementVO vo) {

    }

}