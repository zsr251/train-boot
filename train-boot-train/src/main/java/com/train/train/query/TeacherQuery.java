package com.train.train.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.train.framework.common.query.Query;

/**
* 教师查询
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "教师查询")
public class TeacherQuery extends Query {
    @Schema(description = "登录名")
    private String teacherCode;

    @Schema(description = "用户名")
    private Long userId;

    @Schema(description = "老师名称")
    private String teacherName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "工作类型")
    private String jobType;

    @Schema(description = "状态")
    private String status;
}