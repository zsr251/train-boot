package com.train.system.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.datapermission.dto.DataPermissionDTO;
import com.train.framework.mybatis.service.BaseService;
import com.train.system.vo.SysMenuDataPermissionVO;
import com.train.system.query.SysMenuDataPermissionQuery;
import com.train.system.entity.SysMenuDataPermissionEntity;

import java.util.List;

/**
 * 数据权限
 *
 * @author jasonzhu
 * @since 1.0.0 2024-12-06
 */
public interface SysMenuDataPermissionService extends BaseService<SysMenuDataPermissionEntity> {

    PageResult<SysMenuDataPermissionVO> page(SysMenuDataPermissionQuery query);

    void save(SysMenuDataPermissionVO vo);

    void update(SysMenuDataPermissionVO vo);

    void delete(List<Long> idList);

    /**
     * 获取所有的数据权限
     *
     * @return
     */
    List<SysMenuDataPermissionVO> getDataPermissionList();

    /**
     * 根据用户id和前端组件名称查询数据权限
     *
     * @param userId        用户ID
     * @param componentList 组件列表
     * @return
     */
    List<DataPermissionDTO> getListByUserId(Long userId, List<String> componentList);

    /**
     * 根据角色ID和前端组件名称查询数据权限
     *
     * @param roleId    角色ID
     * @param component 组件
     * @return
     */
    List<SysMenuDataPermissionVO> getListByRoleId(Long roleId, String component);

    /**
     * 根据角色ID和前端组件名称查询数据权限
     *
     * @param roleId 角色ID
     * @return
     */
    List<Long> getIdListByRoleId(Long roleId);

    /**
     * 根据角色ID和前端组件名称查询数据权限
     *
     * @param roleId    角色ID
     * @param component 组件
     * @return
     */
    List<Long> getIdListByRoleId(Long roleId, String component);

    /**
     * 根据前端组件名称查询数据权限
     *
     * @param component 前端组件名称
     * @return
     */
    List<SysMenuDataPermissionVO> getListByComponent(String component);

    /**
     * 保存或修改角色的数据权限
     *
     * @param roleId               角色ID
     * @param dataPermissionIdList 数据权限ID列表
     */
    void saveOrUpdateRoleDataPermission(Long roleId, List<Long> dataPermissionIdList);
}