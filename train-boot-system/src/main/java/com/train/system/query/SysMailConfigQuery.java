package com.train.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;

/**
 * 邮件配置查询
 *

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "邮件配置查询")
public class SysMailConfigQuery extends Query {
    @Schema(description = "平台类型")
    private Integer platform;

}