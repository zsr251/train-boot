package com.train.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.io.Serializable;
import com.train.framework.common.utils.DateUtils;
import java.time.LocalDateTime;

/**
* 数据权限
*
* @author jasonzhu 
* @since 1.0.0 2024-12-06
*/
@Data
@Schema(description = "数据权限")
public class SysMenuDataPermissionVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "关联前端组件")
	private String component;

	@Schema(description = "规则名称")
	private String ruleName;

	@Schema(description = "规则类型")
	private String ruleType;

	@Schema(description = "规则标识")
	private String ruleFlag;

	@Schema(description = "规则内容")
	private String ruleValue;

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

}