package com.train.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.train.framework.datapermission.dto.DataPermissionDTO;
import com.train.system.dao.SysMenuDao;
import com.train.system.dao.SysRoleDataPermissionDao;
import com.train.system.entity.SysMenuEntity;
import com.train.system.entity.SysRoleDataPermissionEntity;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.system.convert.SysMenuDataPermissionConvert;
import com.train.system.entity.SysMenuDataPermissionEntity;
import com.train.system.query.SysMenuDataPermissionQuery;
import com.train.system.vo.SysMenuDataPermissionVO;
import com.train.system.dao.SysMenuDataPermissionDao;
import com.train.system.service.SysMenuDataPermissionService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据权限
 *
 * @author jasonzhu
 * @since 1.0.0 2024-12-06
 */
@Service
@AllArgsConstructor
public class SysMenuDataPermissionServiceImpl extends BaseServiceImpl<SysMenuDataPermissionDao, SysMenuDataPermissionEntity> implements SysMenuDataPermissionService {
    private final SysMenuDao sysMenuDao;
    private final SysRoleDataPermissionDao sysRoleDataPermissionDao;

    @Override
    public PageResult<SysMenuDataPermissionVO> page(SysMenuDataPermissionQuery query) {
        IPage<SysMenuDataPermissionEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SysMenuDataPermissionConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<SysMenuDataPermissionEntity> getWrapper(SysMenuDataPermissionQuery query) {
        LambdaQueryWrapper<SysMenuDataPermissionEntity> wrapper = Wrappers.lambdaQuery();
        return wrapper;
    }

    @Override
    public void save(SysMenuDataPermissionVO vo) {
        vo.setId(null);
        Validator.validateNotEmpty(vo.getComponent(), "关联前端组件不能为空");
        Validator.validateNotEmpty(vo.getRuleFlag(), "规则标识不能为空");
        Validator.validateNotEmpty(vo.getRuleName(), "规则名称不能为空");
        Validator.validateNotEmpty(vo.getRuleType(), "规则类型不能为空");
        SysMenuDataPermissionEntity entity = SysMenuDataPermissionConvert.INSTANCE.convert(vo);
        baseMapper.insert(entity);
        SysMenuEntity menuEntity = new SysMenuEntity();
        menuEntity.setHasDataPermission(1);
        // 更新该菜单的数据权限状态为有
        sysMenuDao.update(null, new LambdaUpdateWrapper<SysMenuEntity>()
                .eq(SysMenuEntity::getUrl, vo.getComponent()).eq(SysMenuEntity::getHasDataPermission, 0)
                .set(SysMenuEntity::getHasDataPermission, 1));
    }

    @Override
    public void update(SysMenuDataPermissionVO vo) {
        Validator.validateNotNull(vo.getId(), "ID不能为空");
        SysMenuDataPermissionEntity entity = SysMenuDataPermissionConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<SysMenuDataPermissionVO> getDataPermissionList() {
        List<SysMenuDataPermissionEntity> l = baseMapper.selectList(new LambdaQueryWrapper<SysMenuDataPermissionEntity>()
                .eq(SysMenuDataPermissionEntity::getDeleted, 0));
        if (CollUtil.isNotEmpty(l)) {
            return SysMenuDataPermissionConvert.INSTANCE.convertList(l);
        }
        return new ArrayList<>();
    }

    @Override
    public List<DataPermissionDTO> getListByUserId(Long userId, List<String> componentList) {
        if (userId == null || ArrayUtils.isEmpty(componentList.toArray())) {
            return new ArrayList<>();
        }
        List<SysMenuDataPermissionEntity> list = baseMapper.getListByUserId(userId, componentList);
        if (CollUtil.isNotEmpty(list)) {
            return SysMenuDataPermissionConvert.INSTANCE.convertList2(list);
        }
        return new ArrayList<>();
    }

    @Override
    public List<SysMenuDataPermissionVO> getListByRoleId(Long roleId, String component) {
        List<SysMenuDataPermissionEntity> list = baseMapper.getListByRoleId(roleId, component);
        if (CollUtil.isNotEmpty(list)) {
            return SysMenuDataPermissionConvert.INSTANCE.convertList(list);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Long> getIdListByRoleId(Long roleId) {
        return sysRoleDataPermissionDao.selectList(new LambdaQueryWrapper<SysRoleDataPermissionEntity>()
                        .eq(SysRoleDataPermissionEntity::getRoleId, roleId).select(SysRoleDataPermissionEntity::getDataPermissionId))
                .stream().map(SysRoleDataPermissionEntity::getDataPermissionId).collect(Collectors.toList());
    }

    @Override
    public List<Long> getIdListByRoleId(Long roleId, String component) {
        List<SysMenuDataPermissionEntity> list = baseMapper.getListByRoleId(roleId, component);
        if (CollUtil.isNotEmpty(list)) {
            return list.stream().map(SysMenuDataPermissionEntity::getId).toList();
        }
        return new ArrayList<>();
    }

    @Override
    public List<SysMenuDataPermissionVO> getListByComponent(String component) {
        List<SysMenuDataPermissionEntity> list = baseMapper.getListByComponent(component);
        if (CollUtil.isNotEmpty(list)) {
            return SysMenuDataPermissionConvert.INSTANCE.convertList(list);
        }
        return new ArrayList<>();
    }

    @Override
    public void saveOrUpdateRoleDataPermission(Long roleId, List<Long> dataPermissionIdList) {
        dataPermissionIdList = dataPermissionIdList == null ? new ArrayList<>() : dataPermissionIdList;
        // 传入的
        Set<Long> sendIdSet = new HashSet<>(dataPermissionIdList);
        List<SysRoleDataPermissionEntity> existList = sysRoleDataPermissionDao.selectList(
                new LambdaQueryWrapper<SysRoleDataPermissionEntity>().eq(SysRoleDataPermissionEntity::getRoleId, roleId)
        );
        // 已有的
        Set<Long> roleIdSet = existList.stream().map(SysRoleDataPermissionEntity::getDataPermissionId).collect(Collectors.toSet());
        // 需要删除的ID
        Set<Long> deleteIdSet = new HashSet<>(roleIdSet);
        deleteIdSet.removeAll(sendIdSet);

        // 需要增加的ID
        Set<Long> addIdSet = new HashSet<>(sendIdSet);
        addIdSet.removeAll(roleIdSet);
        // 删除应该删的
        if (CollUtil.isNotEmpty(deleteIdSet)) {
            sysRoleDataPermissionDao.delete(new LambdaQueryWrapper<SysRoleDataPermissionEntity>()
                    .eq(SysRoleDataPermissionEntity::getRoleId, roleId).in(SysRoleDataPermissionEntity::getDataPermissionId, deleteIdSet));
        }
        // 添加新增的
        if (CollUtil.isNotEmpty(addIdSet)) {
            List<SysRoleDataPermissionEntity> addList = addIdSet.stream().map(dataPermissionId -> {
                SysRoleDataPermissionEntity entity = new SysRoleDataPermissionEntity();
                entity.setRoleId(roleId);
                entity.setDataPermissionId(dataPermissionId);
                return entity;
            }).collect(Collectors.toList());
            // 批量新增
            sysRoleDataPermissionDao.insert(addList);
        }
    }

}