package com.train.system.query;

import com.train.framework.common.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "数据权限查询")
public class SysMenuDataPermissionQuery extends Query {
}
