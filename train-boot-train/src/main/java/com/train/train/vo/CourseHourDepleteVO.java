package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 消课记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@Accessors(chain = true)
@Schema(description = "消课记录")
public class CourseHourDepleteVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "学员ID")
	private Long studentId;

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

	@Schema(description = "任课老师")
	private String teacherCode;

	@Schema(description = "课程日期")
	private LocalDateTime lessonDate;

	@Schema(description = "课程开始时间")
	private LocalDateTime lessonBeginTime;

	@Schema(description = "课程结束时间")
	private LocalDateTime lessonEndTime;

	@Schema(description = "到课状态")
	private String arrivalStatus;

	@Schema(description = "扣课时数")
	private Integer courseHour;

	@Schema(description = "消课时间")
	private LocalDateTime depleteTime;

	@Schema(description = "消课前课时")
	private Integer courseHourBefore;

	@Schema(description = "消课后课时")
	private Integer courseHourAfter;

	@Schema(description = "备注")
	private String remark;


}