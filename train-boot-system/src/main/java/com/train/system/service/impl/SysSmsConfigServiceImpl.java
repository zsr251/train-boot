package com.train.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import com.train.framework.common.constant.Constant;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.sms.config.SmsConfig;
import com.train.system.cache.SmsConfigCache;
import com.train.system.convert.SysSmsConfigConvert;
import com.train.system.dao.SysSmsConfigDao;
import com.train.system.entity.SysSmsConfigEntity;
import com.train.system.query.SysSmsConfigQuery;
import com.train.system.service.SysSmsConfigService;
import com.train.system.vo.SysSmsConfigVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 短信配置
 *

 */
@Service
@AllArgsConstructor
public class SysSmsConfigServiceImpl extends BaseServiceImpl<SysSmsConfigDao, SysSmsConfigEntity> implements SysSmsConfigService {
    private final SmsConfigCache smsConfigCache;

    @Override
    public PageResult<SysSmsConfigVO> page(SysSmsConfigQuery query) {
        IPage<SysSmsConfigEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SysSmsConfigConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    @Override
    public List<SysSmsConfigVO> list(Integer platform) {
        LambdaQueryWrapper<SysSmsConfigEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(platform != null, SysSmsConfigEntity::getPlatform, platform);

        List<SysSmsConfigEntity> list = baseMapper.selectList(wrapper);
        return SysSmsConfigConvert.INSTANCE.convertList(list);
    }

    private LambdaQueryWrapper<SysSmsConfigEntity> getWrapper(SysSmsConfigQuery query) {
        LambdaQueryWrapper<SysSmsConfigEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(query.getPlatform() != null, SysSmsConfigEntity::getPlatform, query.getPlatform());
        wrapper.like(StrUtil.isNotBlank(query.getGroupName()), SysSmsConfigEntity::getGroupName, query.getGroupName());
        return wrapper;
    }

    @Override
    public List<SmsConfig> listByEnable() {
        // 从缓存读取
        List<SmsConfig> cacheList = smsConfigCache.list();

        // 如果缓存没有，则从DB读取，然后保存到缓存里
        if (cacheList == null) {
            List<SysSmsConfigEntity> list = this.list(new LambdaQueryWrapper<SysSmsConfigEntity>().in(SysSmsConfigEntity::getStatus, Constant.ENABLE));

            cacheList = SysSmsConfigConvert.INSTANCE.convertList2(list);
            smsConfigCache.save(cacheList);
        }

        return cacheList;
    }

    @Override
    public void save(SysSmsConfigVO vo) {
        SysSmsConfigEntity entity = SysSmsConfigConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);

        smsConfigCache.delete();
    }

    @Override
    public void update(SysSmsConfigVO vo) {
        SysSmsConfigEntity entity = SysSmsConfigConvert.INSTANCE.convert(vo);

        updateById(entity);

        smsConfigCache.delete();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);

        smsConfigCache.delete();
    }

}