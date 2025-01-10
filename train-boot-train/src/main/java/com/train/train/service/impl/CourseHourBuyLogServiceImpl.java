package com.train.train.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.convert.CourseHourBuyLogConvert;
import com.train.train.entity.CourseHourBuyLogEntity;
import com.train.train.query.CourseHourBuyLogQuery;
import com.train.train.vo.CourseHourBuyLogVO;
import com.train.train.dao.CourseHourBuyLogDao;
import com.train.train.service.CourseHourBuyLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课时购买记录
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class CourseHourBuyLogServiceImpl extends BaseServiceImpl<CourseHourBuyLogDao, CourseHourBuyLogEntity> implements CourseHourBuyLogService {

    @Override
    public PageResult<CourseHourBuyLogVO> page(CourseHourBuyLogQuery query) {
        IPage<CourseHourBuyLogEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(CourseHourBuyLogConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<CourseHourBuyLogEntity> getWrapper(CourseHourBuyLogQuery query){
        LambdaQueryWrapper<CourseHourBuyLogEntity> wrapper = Wrappers.lambdaQuery();
        return wrapper;
    }

    @Override
    public void save(CourseHourBuyLogVO vo) {
        CourseHourBuyLogEntity entity = CourseHourBuyLogConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(CourseHourBuyLogVO vo) {
        CourseHourBuyLogEntity entity = CourseHourBuyLogConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}