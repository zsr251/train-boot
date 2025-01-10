package com.train.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.train.framework.common.exception.ServerException;
import com.train.framework.common.utils.PageResult;
import com.train.framework.dict.utils.DictUtils;
import com.train.framework.mq.refresh.LocalCacheRefresh;
import com.train.framework.mq.refresh.LocalCacheRefreshMessage;
import com.train.framework.mq.refresh.LocalCacheRefreshProducer;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.system.convert.SysDictTypeConvert;
import com.train.system.dao.SysDictDataDao;
import com.train.system.dao.SysDictTypeDao;
import com.train.system.entity.SysDictDataEntity;
import com.train.system.entity.SysDictTypeEntity;
import com.train.system.enums.DictSourceEnum;
import com.train.system.query.SysDictTypeQuery;
import com.train.system.service.SysDictTypeService;
import com.train.system.vo.SysDictTypeVO;
import com.train.system.vo.SysDictVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典类型
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeDao, SysDictTypeEntity> implements SysDictTypeService, LocalCacheRefresh {
    private final SysDictDataDao sysDictDataDao;

    @Override
    public PageResult<SysDictTypeVO> page(SysDictTypeQuery query) {
        IPage<SysDictTypeEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<>(SysDictTypeConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    @Override
    public List<SysDictTypeVO> list(Long pid) {
        LambdaQueryWrapper<SysDictTypeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(pid != null, SysDictTypeEntity::getPid, pid);

        List<SysDictTypeEntity> list = baseMapper.selectList(wrapper);
        return SysDictTypeConvert.INSTANCE.convertList(list);
    }

    private Wrapper<SysDictTypeEntity> getWrapper(SysDictTypeQuery query) {
        LambdaQueryWrapper<SysDictTypeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(query.getDictType()), SysDictTypeEntity::getDictType, query.getDictType());
        wrapper.like(StrUtil.isNotBlank(query.getDictName()), SysDictTypeEntity::getDictName, query.getDictName());
        wrapper.isNull(SysDictTypeEntity::getPid);
        wrapper.orderByAsc(SysDictTypeEntity::getSort);

        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictTypeVO vo) {
        SysDictTypeEntity entity = SysDictTypeConvert.INSTANCE.convert(vo);

        // 更新上级，有子节点
        if (vo.getPid() != null) {
            this.updateHasChild(vo.getPid(), 1);
        }

        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictTypeVO vo) {
        SysDictTypeEntity entity = SysDictTypeConvert.INSTANCE.convert(vo);

        // 更新上级，有子节点
        if (vo.getPid() != null) {
            this.updateHasChild(vo.getPid(), 1);
        }

        // 获取原先的上级ID
        Long oldPid = baseMapper.selectById(vo.getId()).getPid();

        // 更新
        updateById(entity);

        // 上级已修改
        if (!ObjectUtil.equals(oldPid, vo.getPid())) {
            long count = baseMapper.selectCount(new LambdaQueryWrapper<SysDictTypeEntity>().eq(SysDictTypeEntity::getPid, oldPid));
            if (count == 0) {
                // 更新原先的上级，没有子节点
                this.updateHasChild(oldPid, 0);
            }
        }

    }

    /**
     * 更新上级，有子节点
     *
     * @param pid      上级ID
     * @param hasChild 是否有子节点
     */
    private void updateHasChild(Long pid, Integer hasChild) {
        SysDictTypeEntity entity = new SysDictTypeEntity();
        entity.setId(pid);
        entity.setHasChild(hasChild);
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<SysDictVO.DictData> getDictSql(Long id) {
        SysDictTypeEntity entity = this.getById(id);
        try {
            return sysDictDataDao.getListForSql(entity.getDictSql());
        } catch (Exception e) {
            throw new ServerException("动态SQL执行失败，请检查SQL是否正确！");
        }
    }

    @Override
    public List<SysDictVO> getDictList() {
        // 全部字典类型列表
        List<SysDictTypeEntity> typeList = this.list(Wrappers.emptyWrapper());

        // 全部字典数据列表
        QueryWrapper<SysDictDataEntity> query = new QueryWrapper<SysDictDataEntity>().orderByAsc("sort");
        List<SysDictDataEntity> dataList = sysDictDataDao.selectList(query);

        // 全部字典列表
        List<SysDictVO> dictList = new ArrayList<>(typeList.size());
        for (SysDictTypeEntity type : typeList) {
            SysDictVO dict = new SysDictVO();
            dict.setDictType(type.getDictType());

            for (SysDictDataEntity data : dataList) {
                if (type.getId().equals(data.getDictTypeId())) {
                    dict.getDataList().add(new SysDictVO.DictData(data.getDictLabel(), data.getDictValue(), data.getLabelClass(), null));
                }
            }

            // 数据来源动态SQL
            if (type.getDictSource() == DictSourceEnum.SQL.getValue()) {
                // 增加动态列表
                String sql = type.getDictSql();
                try {
                    dict.setDataList(sysDictDataDao.getListForSql(sql));
                } catch (Exception e) {
                    log.error("增加动态字典异常: type=" + type, e);
                }
            }

            dictList.add(dict);
        }

        return dictList;
    }

    @Override
    public SysDictVO getDictVO(String dictType) {
        if (StrUtil.isBlank(dictType)) return null;
        SysDictTypeEntity entity = baseMapper.selectOne(new LambdaQueryWrapper<SysDictTypeEntity>().eq(SysDictTypeEntity::getDictType, dictType), false);
        if (entity == null) {
            return null;
        }
        SysDictVO dict = new SysDictVO();
        dict.setDictType(dictType);
        if (entity.getDictSource() == DictSourceEnum.SQL.getValue()) {
            // 增加动态列表
            String sql = entity.getDictSql();
            try {
                dict.setDataList(sysDictDataDao.getListForSql(sql));
            } catch (Exception e) {
                log.error("增加动态字典异常: type=" + dictType, e);
                dict.setDataList(new ArrayList<>());
            }
        } else {
            QueryWrapper<SysDictDataEntity> query = new QueryWrapper<SysDictDataEntity>().orderByAsc("sort");
            List<SysDictDataEntity> dataList = sysDictDataDao.selectList(new LambdaQueryWrapper<SysDictDataEntity>()
                    .eq(SysDictDataEntity::getDictTypeId, entity.getId()));
            dict.setDataList(dataList.stream().map(data -> new SysDictVO.DictData(data.getDictLabel(), data.getDictValue(), data.getLabelClass(), null))
                    .collect(Collectors.toList()));
        }
        return dict;
    }

    @Override
    public void refreshRedisCache(String dictType) {
        DictUtils.refreshRedisCache(dictType);
        LocalCacheRefreshProducer.sendRefreshMessage(getLocalCacheKey());
        log.info("清除redis缓存【{}】并发送清除字典本地缓存消息成功", dictType);
    }

    @Override
    public void refreshLocalCache() {
        LocalCacheRefreshProducer.sendRefreshMessage(getLocalCacheKey());
        log.info("发送清除字典本地缓存消息成功");
    }

    @Override
    public String getLocalCacheKey() {
        return "dict";
    }

    @Override
    public void initLocalCache(LocalCacheRefreshMessage message) {
        DictUtils.refreshLocalCache();
        log.info("字典本地缓存清除成功");
    }
}
