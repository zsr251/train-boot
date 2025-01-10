package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 班级课程
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Data
@Accessors(chain = true)
@Schema(description = "班级课程")
public class ClassCourseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "班级编码")
    private String classCode;

    @Schema(description = "课程编码")
    private String courseCode;

    @Schema(description = "老师编码")
    private String teacherCode;

    @Schema(description = "教室编码")
    private String classroomCode;

    @Schema(description = "计划课时数")
    private Integer planHours;

    @Schema(description = "单次课时数")
    private Integer courseHourOnce;

    @Schema(description = "已授课时")
    private Integer completedHours;

    @Schema(description = "已上课次数")
    private Integer completedTimes;

    @Schema(description = "已排课时数")
    private Integer scheduleHours;

    @Schema(description = "排课规则")
    private String scheduleRule;

    @Schema(description = "排课规则VO")
    private ScheduleRuleVO scheduleRuleVO;

    @Schema(description = "排课状态")
    private String scheduleStatus;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "删除标识  0：正常   1：已删除")
    private Integer deleted;

    @Schema(description = "创建者")
    private Long creator;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新者")
    private Long updater;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "租户ID")
    private Long tenantId;

}