package com.train.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "添加角色数据权限")
public class AddRoleDataPermissionVO {
    @Schema(description = "角色ID")
    private Long roleId;
    @Schema(description = "前端组件")
    private String component;
    @Schema(description = "数据权限ID")
    private List<Long> dataPermissionIds;
}
