package com.train.train.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.convert.LeaveApplyConvert;
import com.train.train.entity.LeaveApplyEntity;
import com.train.train.query.LeaveApplyQuery;
import com.train.train.vo.LeaveApplyVO;
import com.train.train.dao.LeaveApplyDao;
import com.train.train.service.LeaveApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 请假申请
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class LeaveApplyServiceImpl extends BaseServiceImpl<LeaveApplyDao, LeaveApplyEntity> implements LeaveApplyService {

    @Override
    public PageResult<LeaveApplyVO> page(LeaveApplyQuery query) {
        IPage<LeaveApplyEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(LeaveApplyConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<LeaveApplyEntity> getWrapper(LeaveApplyQuery query){
        LambdaQueryWrapper<LeaveApplyEntity> wrapper = Wrappers.lambdaQuery();
        return wrapper;
    }

    @Override
    public void save(LeaveApplyVO vo) {
        LeaveApplyEntity entity = LeaveApplyConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(LeaveApplyVO vo) {
        LeaveApplyEntity entity = LeaveApplyConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}