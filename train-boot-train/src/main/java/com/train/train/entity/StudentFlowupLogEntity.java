package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 跟进记录
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_student_flowup_log")
public class StudentFlowupLogEntity extends BaseEntity {

	/**
	* 学员ID
	*/
	private Long studentId;

	/**
	* 跟进人
	*/
	private String flowupPerson;

	/**
	* 跟进类型
	*/
	private String flowupType;

	/**
	* 跟进内容
	*/
	private String flowupDesc;

	/**
	* 下次跟进日期
	*/
	private LocalDateTime nextDate;

	/**
	* 附件
	*/
	private String fileIds;







	/**
	* 租户ID
	*/
	private Long tenantId;

}