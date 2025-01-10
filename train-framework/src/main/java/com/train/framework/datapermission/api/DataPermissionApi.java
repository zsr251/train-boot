package com.train.framework.datapermission.api;

import com.train.framework.datapermission.dto.DataPermissionDTO;

import java.util.List;

/**
 * 权限 API 接口
 */
public interface DataPermissionApi {

    /**
     * 获取用户指定的数据权限
     * 有缓存
     * @param userId     用户ID
     * @param components 组件
     * @return
     */
    List<DataPermissionDTO> getDataPermissionList(Long userId, String[] components);

}
