package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "上课点名")
public class LessonNamedVO {
    @Schema(description = "课程计划ID")
    private Integer lessonId;
    @Schema(description = "默认的到课状态")
    private String defaultArrivalStatus;
    /**
     * 单个学员状态
     */
    @Schema(description = "学员信息")
    List<LessonStudentVO> voList;
}
