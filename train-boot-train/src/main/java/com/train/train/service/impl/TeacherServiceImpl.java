package com.train.train.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.convert.TeacherConvert;
import com.train.train.entity.TeacherEntity;
import com.train.train.query.TeacherQuery;
import com.train.train.vo.TeacherVO;
import com.train.train.dao.TeacherDao;
import com.train.train.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 教师
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class TeacherServiceImpl extends BaseServiceImpl<TeacherDao, TeacherEntity> implements TeacherService {

    @Override
    public PageResult<TeacherVO> page(TeacherQuery query) {
        IPage<TeacherEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(TeacherConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<TeacherEntity> getWrapper(TeacherQuery query){
        LambdaQueryWrapper<TeacherEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(query.getUserId()!=null, TeacherEntity::getUserId, query.getUserId());
        wrapper.eq(StringUtils.isNotEmpty(query.getTeacherCode()), TeacherEntity::getTeacherCode, query.getTeacherCode());
        wrapper.like(StringUtils.isNotEmpty(query.getTeacherName()), TeacherEntity::getTeacherName, query.getTeacherName());
        wrapper.like(StringUtils.isNotEmpty(query.getPhone()), TeacherEntity::getPhone, query.getPhone());
        wrapper.eq(StringUtils.isNotEmpty(query.getJobType()), TeacherEntity::getJobType, query.getJobType());
        wrapper.eq(StringUtils.isNotEmpty(query.getStatus()), TeacherEntity::getStatus, query.getStatus());
        return wrapper;
    }

    @Override
    public void save(TeacherVO vo) {
        TeacherEntity entity = TeacherConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(TeacherVO vo) {
        TeacherEntity entity = TeacherConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<String> getNameList(List<String> teacherCodeList) {
        if (teacherCodeList == null || teacherCodeList.isEmpty()) {
            return null;
        }
        LambdaQueryWrapper<TeacherEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.in(TeacherEntity::getTeacherCode, teacherCodeList);
        return baseMapper.selectList(wrapper).stream().map(TeacherEntity::getTeacherName).toList();
    }

}