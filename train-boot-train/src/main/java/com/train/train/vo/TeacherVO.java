package com.train.train.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.train.framework.common.excel.DictConverter;
import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.DictNeed;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 教师
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@DictNeed
@Data
@Accessors(chain = true)
@Schema(description = "教师")
public class TeacherVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ExcelIgnore
    @Schema(description = "id")
    private Long id;

    @ExcelProperty("老师编码")
    @Schema(description = "登录名")
    private String teacherCode;

    @ExcelIgnore
    @Schema(description = "用户名")
    private Long userId;

    @ExcelProperty("老师名称")
    @Schema(description = "老师名称")
    private String teacherName;

    @ExcelProperty("手机号")
    @Schema(description = "手机号")
    private String phone;

    @ExcelProperty(value = "工作类型", converter = DictConverter.class)
    @Dict(key = "teacher_job_type")
    @Schema(description = "工作类型 兼职全职")
    private String jobType;

    @ExcelProperty("资格证")
    @Schema(description = "资格证")
    private String certification;

    @ExcelIgnore
    @Schema(description = "工资方案")
    private String wageRule;

    @ExcelProperty("备注")
    @Schema(description = "备注")
    private String remark;

    @ExcelProperty(value = "老师状态", converter = DictConverter.class)
    @Dict(key = "teacher_status")
    @Schema(description = "状态 离职 在职")
    private String status;

    @ExcelIgnore
    @Schema(description = "版本号")
    private Integer version;
    @ExcelIgnore
    @Schema(description = "删除标识  0：正常   1：已删除")
    private Integer deleted;
    @ExcelIgnore
    @Schema(description = "创建者")
    private Long creator;
    @ExcelIgnore
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @ExcelIgnore
    @Schema(description = "更新者")
    private Long updater;
    @ExcelIgnore
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    @ExcelIgnore
    @Schema(description = "租户ID")
    private Long tenantId;

}