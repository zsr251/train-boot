package com.train.train.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.convert.CourseAuditionConvert;
import com.train.train.entity.CourseAuditionEntity;
import com.train.train.query.CourseAuditionQuery;
import com.train.train.vo.CourseAuditionVO;
import com.train.train.dao.CourseAuditionDao;
import com.train.train.service.CourseAuditionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程试听
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class CourseAuditionServiceImpl extends BaseServiceImpl<CourseAuditionDao, CourseAuditionEntity> implements CourseAuditionService {

    @Override
    public PageResult<CourseAuditionVO> page(CourseAuditionQuery query) {
        IPage<CourseAuditionEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(CourseAuditionConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<CourseAuditionEntity> getWrapper(CourseAuditionQuery query){
        LambdaQueryWrapper<CourseAuditionEntity> wrapper = Wrappers.lambdaQuery();
        return wrapper;
    }

    @Override
    public void save(CourseAuditionVO vo) {
        CourseAuditionEntity entity = CourseAuditionConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(CourseAuditionVO vo) {
        CourseAuditionEntity entity = CourseAuditionConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}