package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

import lombok.experimental.Accessors;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 消课记录
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@Accessors(chain = true)
@TableName("tt_course_hour_deplete_log")
public class CourseHourDepleteLogEntity extends BaseEntity {

	/**
	* 学员ID
	*/
	private Long studentId;

	/**
	* 课程ID
	*/
	private Long lessonId;

	/**
	* 班级课程ID
	*/
	private Long classCourseId;

	/**
	* 班级编码
	*/
	private String classCode;

	/**
	* 课程编码
	*/
	private String courseCode;

	/**
	* 教室编码
	*/
	private String classroomCode;

	/**
	* 任课老师
	*/
	private String teacherCode;

	/**
	* 课程日期
	*/
	private LocalDateTime lessonDate;

	/**
	* 课程开始时间
	*/
	private LocalDateTime lessonBeginTime;

	/**
	* 课程结束时间
	*/
	private LocalDateTime lessonEndTime;

	/**
	* 到课状态
	*/
	private String arrivalStatus;

	/**
	* 扣课时数
	*/
	private Integer courseHour;

	/**
	* 消课时间
	*/
	private LocalDateTime depleteTime;

	/**
	* 消课前课时
	*/
	private Integer courseHourBefore;

	/**
	* 消课后课时
	*/
	private Integer courseHourAfter;

	/**
	* 备注
	*/
	private String remark;







	/**
	* 租户ID
	*/
	private Long tenantId;

}