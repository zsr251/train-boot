package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "课程计划")
public class LessonTimetableGroupVO {
    @Schema(description = "类型编码")
    private String groupCode;
    @Schema(description = "类型名称")
    private String groupName;
    @Schema(description = "周一课程计划")
    private List<LessonTimetableVO> mon;
    @Schema(description = "周二日课程计划")
    private List<LessonTimetableVO> tue;
    @Schema(description = "周三课程计划")
    private List<LessonTimetableVO> wed;
    @Schema(description = "周四课程计划")
    private List<LessonTimetableVO> thu;
    @Schema(description = "周五课程计划")
    private List<LessonTimetableVO> fri;
    @Schema(description = "周六课程计划")
    private List<LessonTimetableVO> sat;
    @Schema(description = "周日课程计划")
    private List<LessonTimetableVO> sun;
}
