package com.train.student.vo;

import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.DictNeed;
import com.train.framework.dict.annotations.TransTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@DictNeed
@Data
@Accessors(chain = true)
@Schema(description = "学员课程课时")
public class StudentCourseInfoVO {

    @Schema(description = "学员ID")
    private Long studentId;

    @Dict(key = "course_code", ref = "courseName")
    @Schema(description = "课程编码")
    private String courseCode;

    @Schema(description = "课程名称")
    private String courseName;

    @Schema(description = "总消耗课时")
    private Integer courseHourDeplete;

    @Schema(description = "剩余课时")
    private Integer courseHourLeft;

    @Schema(description = "备注")
    private String remark;
}
