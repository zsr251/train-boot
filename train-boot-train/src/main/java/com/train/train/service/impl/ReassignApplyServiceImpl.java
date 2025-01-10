package com.train.train.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.convert.ReassignApplyConvert;
import com.train.train.entity.ReassignApplyEntity;
import com.train.train.query.ReassignApplyQuery;
import com.train.train.vo.ReassignApplyVO;
import com.train.train.dao.ReassignApplyDao;
import com.train.train.service.ReassignApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 调课申请
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class ReassignApplyServiceImpl extends BaseServiceImpl<ReassignApplyDao, ReassignApplyEntity> implements ReassignApplyService {

    @Override
    public PageResult<ReassignApplyVO> page(ReassignApplyQuery query) {
        IPage<ReassignApplyEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(ReassignApplyConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<ReassignApplyEntity> getWrapper(ReassignApplyQuery query){
        LambdaQueryWrapper<ReassignApplyEntity> wrapper = Wrappers.lambdaQuery();
        return wrapper;
    }

    @Override
    public void save(ReassignApplyVO vo) {
        ReassignApplyEntity entity = ReassignApplyConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(ReassignApplyVO vo) {
        ReassignApplyEntity entity = ReassignApplyConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}