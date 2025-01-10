package com.train.framework.datapermission.dto;

import com.train.framework.datapermission.enums.DataPermissionRuleTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 具体数据规则
 * @author zhushengran
 */
@Data
@Accessors(chain = true)
public class DataPermissionDTO {
    /**
     * 数据权限ID
     */
    private Long id;
    /**
     * 数据权限关联菜单
     */
    private Long menuId;
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 数据权限规则类型 SQL/数据字段过滤  {@link DataPermissionRuleTypeEnum}
     */
    private String ruleType;
    /**
     * 权限标识
     */
    private String ruleFlag;
    /**
     * 规则内容
     */
    private String ruleValue;
}
