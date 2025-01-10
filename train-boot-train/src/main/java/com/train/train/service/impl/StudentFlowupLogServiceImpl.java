package com.train.train.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.convert.StudentFlowupLogConvert;
import com.train.train.entity.StudentFlowupLogEntity;
import com.train.train.query.StudentFlowupLogQuery;
import com.train.train.vo.StudentFlowupLogVO;
import com.train.train.dao.StudentFlowupLogDao;
import com.train.train.service.StudentFlowupLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 跟进记录
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class StudentFlowupLogServiceImpl extends BaseServiceImpl<StudentFlowupLogDao, StudentFlowupLogEntity> implements StudentFlowupLogService {

    @Override
    public PageResult<StudentFlowupLogVO> page(StudentFlowupLogQuery query) {
        IPage<StudentFlowupLogEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(StudentFlowupLogConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<StudentFlowupLogEntity> getWrapper(StudentFlowupLogQuery query){
        LambdaQueryWrapper<StudentFlowupLogEntity> wrapper = Wrappers.lambdaQuery();
        return wrapper;
    }

    @Override
    public void save(StudentFlowupLogVO vo) {
        StudentFlowupLogEntity entity = StudentFlowupLogConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(StudentFlowupLogVO vo) {
        StudentFlowupLogEntity entity = StudentFlowupLogConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}