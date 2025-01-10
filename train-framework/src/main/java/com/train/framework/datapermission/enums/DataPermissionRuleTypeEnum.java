package com.train.framework.datapermission.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限规则类型枚举
 */
@AllArgsConstructor
@Getter
public enum DataPermissionRuleTypeEnum {
    SQL_RULE("sql_rule", "自定义SQL片段"),
    COL_CAN_ONLY("col_can_only", "只允许展示列"),
    COL_BAN("col_ban", "不允许展示列");
    /**
     * 编码
     */
    private final String code;
    /**
     * 解释
     */
    private final String msg;

    /**
     * 获取枚举
     *
     * @param code 编码
     * @return 类型
     */
    public static DataPermissionRuleTypeEnum getEnum(String code) {
        for (DataPermissionRuleTypeEnum value : DataPermissionRuleTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
