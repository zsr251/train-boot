package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

import lombok.experimental.Accessors;
import com.train.framework.common.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 课程计划学员
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Data
@Accessors(chain = true)
@Schema(description = "课程计划学员")
public class LessonStudentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "课程ID")
    private Long lessonId;

    @Schema(description = "班级课程ID")
    private Long classCourseId;

    @Schema(description = "班级编码")
    private String classCode;

    @Schema(description = "课程编码")
    private String courseCode;

    @Schema(description = "教室编码")
    private String classroomCode;

    @Schema(description = "学员ID")
    private Long studentId;

    @Schema(description = "学员姓名")
    private String studentName;

    @Schema(description = "成员类型")
    private String memberType;

    @Schema(description = "成员类型")
    private String memberTypeLabel;

    @Schema(description = "到课状态")
    private String arrivalStatus;

    @Schema(description = "到课状态")
    private String arrivalStatusLabel;

    @Schema(description = "消课时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    @DateTimeFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private LocalDateTime depleteTime;

    @Schema(description = "扣课时数")
    private Integer courseHour;

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