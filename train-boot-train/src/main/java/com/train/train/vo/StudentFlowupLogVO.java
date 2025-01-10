package com.train.train.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
* 跟进记录
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@Data
@Accessors(chain = true)
@Schema(description = "跟进记录")
public class StudentFlowupLogVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "学员ID")
	private Long studentId;

	@Schema(description = "跟进人")
	private String flowupPerson;

	@Schema(description = "跟进类型")
	private String flowupType;

	@Schema(description = "跟进内容")
	private String flowupDesc;

	@Schema(description = "下次跟进日期")
	private LocalDateTime nextDate;

	@Schema(description = "附件")
	private String fileIds;

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