package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Schema(description = "课程计划")
public class LessonTimetableAppointShowVO {
    @Schema(description = "第几周")
    private Integer week;
    @Schema(description = "今天")
    private String today;
    @Schema(description = "周一日期")
    private String monDay;
    @Schema(description = "周二日期")
    private String tueDay;
    @Schema(description = "周三日期")
    private String wedDay;
    @Schema(description = "周四日期")
    private String thuDay;
    @Schema(description = "周五日期")
    private String friDay;
    @Schema(description = "周六日期")
    private String satDay;
    @Schema(description = "周日日期")
    private String sunDay;
    @Schema(description = "周一课程总数")
    private Integer monSum;
    @Schema(description = "周二课程总数")
    private Integer tueSum;
    @Schema(description = "周三课程总数")
    private Integer wedSum;
    @Schema(description = "周四课程总数")
    private Integer thuSum;
    @Schema(description = "周五课程总数")
    private Integer friSum;
    @Schema(description = "周六课程总数")
    private Integer satSum;
    @Schema(description = "周日课程总数")
    private Integer sunSum;
    @Schema(description = "课程计划")
    List<LessonTimetableAppointVO> voList;

}
