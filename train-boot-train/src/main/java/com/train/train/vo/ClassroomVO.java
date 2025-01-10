package com.train.train.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.train.framework.common.excel.DictConverter;
import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.DictNeed;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
* 教室
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@DictNeed
@Data
@Accessors(chain = true)
@Schema(description = "教室")
public class ClassroomVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ExcelIgnore
	@Schema(description = "id")
	private Long id;

	@ExcelProperty("教室编码")
	@Schema(description = "教室编码")
	private String classroomCode;

	@ExcelProperty("教室名称")
	@Schema(description = "教室名称")
	private String classroomName;

	@ExcelProperty("教室容量")
	@Schema(description = "教室容量")
	private Integer capacity;

	@ExcelProperty("教室地址")
	@Schema(description = "教室地址")
	private String address;

	@ExcelProperty(value = "教室状态",converter = DictConverter.class)
	@Dict(key = "classroom_status")
	@Schema(description = "教室状态")
	private String classroomStatus;

	@ExcelProperty(value = "忽略排课冲突",converter = DictConverter.class)
	@Dict(key = "ignore_conflict")
	private Integer ignoreConflict;

	@ExcelProperty("备注")
	@Schema(description = "备注")
	private String remark;

	@ExcelIgnore
	@Schema(description = "版本号")
	private Integer version;
	@ExcelIgnore
	@Schema(description = "删除标识  0：正常   1：已删除")
	private Integer deleted;
	@ExcelIgnore
	@Schema(description = "创建者")
	private Long creator;
	@ExcelIgnore
	@Schema(description = "创建时间")
	private LocalDateTime createTime;
	@ExcelIgnore
	@Schema(description = "更新者")
	private Long updater;
	@ExcelIgnore
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;
	@ExcelIgnore
	@Schema(description = "租户ID")
	private Long tenantId;


}