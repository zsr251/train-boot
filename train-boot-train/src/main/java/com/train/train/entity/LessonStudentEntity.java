package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

import lombok.experimental.Accessors;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 课程计划学员
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@Accessors(chain = true)
@TableName("tt_lesson_student")
public class LessonStudentEntity extends BaseEntity {

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
	* 学员ID
	*/
	private Long studentId;

	/**
	* 成员类型
	*/
	private String memberType;

	/**
	* 到课状态
	*/
	private String arrivalStatus;

	/**
	* 消课时间
	*/
	private LocalDateTime depleteTime;

	/**
	* 扣课时数
	*/
	private Integer courseHour;

	/**
	* 备注
	*/
	private String remark;







	/**
	* 租户ID
	*/
	private Long tenantId;

}