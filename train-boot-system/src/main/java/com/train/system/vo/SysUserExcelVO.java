package com.train.system.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.train.framework.common.excel.LocalDateTimeConverter;
import com.train.framework.dict.annotations.Dict;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * excel用户表
 */
@Data
public class SysUserExcelVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 本属性对于导出无用，只是用于翻译
     */
    @ExcelIgnore
    private Long id;

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("姓名")
    private String realName;

    @ExcelIgnore
    @Dict(key = "user_gender", ref = "genderLabel")
    private Integer gender;

    @ExcelProperty(value = "性别")
    private String genderLabel;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("手机号")
    private String mobile;

    @ExcelProperty("机构ID")
    private Long orgId;

    @ExcelIgnore
    @Dict(key = "user_status", ref = "statusLabel")
    private Integer status;

    @ExcelProperty(value = "状态")
    private String statusLabel;

    @ExcelIgnore
    @Dict(key = "user_super_admin", ref = "superAdminLabel")
    private Integer superAdmin;

    @ExcelProperty(value = "超级管理员")
    private String superAdminLabel;

    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createTime;

}
