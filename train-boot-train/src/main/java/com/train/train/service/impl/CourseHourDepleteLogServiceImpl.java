package com.train.train.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.train.framework.dict.utils.TransUtils;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.convert.CourseHourDepleteLogConvert;
import com.train.train.entity.CourseHourDepleteLogEntity;
import com.train.train.query.CourseHourDepleteLogQuery;
import com.train.train.vo.CourseHourDepleteLogVO;
import com.train.train.dao.CourseHourDepleteLogDao;
import com.train.train.service.CourseHourDepleteLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消课记录
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class CourseHourDepleteLogServiceImpl extends BaseServiceImpl<CourseHourDepleteLogDao, CourseHourDepleteLogEntity> implements CourseHourDepleteLogService {

    @Override
    public PageResult<CourseHourDepleteLogVO> page(CourseHourDepleteLogQuery query) {
        IPage<CourseHourDepleteLogEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        List<CourseHourDepleteLogVO> voList = CourseHourDepleteLogConvert.INSTANCE.convertList(page.getRecords());
        return new PageResult<>(TransUtils.transList(voList,CourseHourDepleteLogVO.class), page.getTotal());
    }

    private LambdaQueryWrapper<CourseHourDepleteLogEntity> getWrapper(CourseHourDepleteLogQuery query) {
        LambdaQueryWrapper<CourseHourDepleteLogEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(query.getStudentId() != null && query.getStudentId() != 0, CourseHourDepleteLogEntity::getStudentId, query.getStudentId());
        wrapper.eq(StrUtil.isNotBlank(query.getCourseCode()), CourseHourDepleteLogEntity::getCourseCode, query.getCourseCode());
        wrapper.eq(StrUtil.isNotBlank(query.getTeacherCode()), CourseHourDepleteLogEntity::getTeacherCode, query.getTeacherCode());
        wrapper.ge(query.getDepleteTimeBegin() != null, CourseHourDepleteLogEntity::getDepleteTime, query.getDepleteTimeBegin());
        wrapper.le(query.getDepleteTimeEnd() != null, CourseHourDepleteLogEntity::getDepleteTime, query.getDepleteTimeEnd());
        wrapper.orderByDesc(CourseHourDepleteLogEntity::getId);
        return wrapper;
    }

    @Override
    public void save(CourseHourDepleteLogVO vo) {
        CourseHourDepleteLogEntity entity = CourseHourDepleteLogConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(CourseHourDepleteLogVO vo) {
        CourseHourDepleteLogEntity entity = CourseHourDepleteLogConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}