package com.train.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import com.train.framework.common.exception.ServerException;
import com.train.framework.common.utils.JsonUtils;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.system.cache.SysParamsCache;
import com.train.system.convert.SysParamsConvert;
import com.train.system.dao.SysParamsDao;
import com.train.system.entity.SysParamsEntity;
import com.train.system.query.SysParamsQuery;
import com.train.system.service.SysParamsService;
import com.train.system.vo.SysParamsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数管理
 *

 */
@Service
@AllArgsConstructor
public class SysParamsServiceImpl extends BaseServiceImpl<SysParamsDao, SysParamsEntity> implements SysParamsService {
    private final SysParamsCache sysParamsCache;

    @Override
    public PageResult<SysParamsVO> page(SysParamsQuery query) {
        IPage<SysParamsEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SysParamsConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<SysParamsEntity> getWrapper(SysParamsQuery query) {
        LambdaQueryWrapper<SysParamsEntity> wrapper = Wrappers.lambdaQuery();

        wrapper.like(StrUtil.isNotBlank(query.getParamKey()), SysParamsEntity::getParamKey, query.getParamKey());
        wrapper.eq(StrUtil.isNotBlank(query.getParamValue()), SysParamsEntity::getParamValue, query.getParamValue());
        wrapper.eq(query.getParamType() != null, SysParamsEntity::getParamType, query.getParamType());
        wrapper.orderByDesc(SysParamsEntity::getId);

        return wrapper;
    }

    @Override
    public void save(SysParamsVO vo) {
        // 判断 参数键 是否存在
        boolean exist = baseMapper.isExist(vo.getParamKey());
        if (exist) {
            throw new ServerException("参数键已存在");
        }

        SysParamsEntity entity = SysParamsConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);

        // 保存到缓存
        sysParamsCache.save(entity.getParamKey(), entity.getParamValue());
    }

    @Override
    public void update(SysParamsVO vo) {
        SysParamsEntity entity = baseMapper.selectById(vo.getId());

        // 如果 参数键 修改过
        if (!StrUtil.equalsIgnoreCase(entity.getParamKey(), vo.getParamKey())) {
            // 判断 新参数键 是否存在
            boolean exist = baseMapper.isExist(vo.getParamKey());
            if (exist) {
                throw new ServerException("参数键已存在");
            }

            // 删除修改前的缓存
            sysParamsCache.del(entity.getParamKey());
        }

        // 修改数据
        updateById(SysParamsConvert.INSTANCE.convert(vo));

        // 保存到缓存
        sysParamsCache.save(vo.getParamKey(), vo.getParamValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        // 查询列表
        List<SysParamsEntity> list = baseMapper.selectBatchIds(idList);

        // 删除数据
        removeByIds(idList);

        // 删除缓存
        String[] keys = list.stream().map(SysParamsEntity::getParamKey).toArray(String[]::new);
        sysParamsCache.del(keys);
    }

    @Override
    public String getString(String paramKey) {
        String value = sysParamsCache.get(paramKey);
        if (StrUtil.isNotBlank(value)) {
            return value;
        }

        // 如果缓存没有，则查询数据库
        SysParamsEntity entity = baseMapper.get(paramKey);
        if (entity == null) {
            throw new ServerException("参数值不存在，paramKey：" + paramKey);
        }

        // 保存到缓存
        sysParamsCache.save(entity.getParamKey(), entity.getParamValue());

        return entity.getParamValue();
    }

    @Override
    public void refreshCache(String paramKey) {
        if (StrUtil.isBlank(paramKey)){
            return;
        }
        // 删除修改前的缓存
        sysParamsCache.del(paramKey);
    }

    @Override
    public int getInt(String paramKey) {
        String value = getString(paramKey);

        return Integer.parseInt(value);
    }

    @Override
    public boolean getBoolean(String paramKey) {
        String value = getString(paramKey);

        return Boolean.parseBoolean(value);
    }

    @Override
    public <T> T getJSONObject(String paramKey, Class<T> valueType) {
        String value = getString(paramKey);

        return JsonUtils.parseObject(value, valueType);
    }

    @Override
    public List<String> getHolidayList() {
        String val = getString("holiday");
        if (StrUtil.isNotBlank(val)){
            val = StrUtil.replace(val, "，", ",");
            return StrUtil.splitTrim(val, ",");
        }
        return new ArrayList<>();
    }

}