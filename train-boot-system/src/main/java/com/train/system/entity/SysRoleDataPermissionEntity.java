package com.train.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.train.framework.mybatis.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据权限
 *
 * @author jasonzhu
 * @since 1.0.0 2024-12-06
 */

@Data
@TableName("sys_role_data_permission")
public class SysRoleDataPermissionEntity  extends BaseEntity {
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 数据权限ID
     */
    private Long dataPermissionId;

}