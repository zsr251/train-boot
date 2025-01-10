package com.train.train.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.train.framework.common.excel.DictConverter;
import com.train.framework.common.excel.LocalDateTimeConverter;
import com.train.framework.common.utils.DateUtils;
import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.DictNeed;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* 学员
*
* @author jasonzhu 
* @since 1.0.0 2024-11-15
*/
@DictNeed
@Data
@Accessors(chain = true)
@Schema(description = "学员")
public class StudentVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ExcelProperty("学员ID")
	@Schema(description = "主键id")
	private Long id;

	@ExcelProperty("姓名")
	@Schema(description = "姓名")
	private String studentName;

	@ExcelProperty("手机号")
	@Schema(description = "手机号")
	private String phone;

	@ExcelProperty("手机号归属")
	@Schema(description = "手机号归属")
	private String phoneBelong;

	@ExcelProperty(value = "性别",converter = DictConverter.class)
	@Dict(key = "user_gender")
	@Schema(description = "性别")
	private String gender;

	@ExcelProperty(value = "生日",converter = LocalDateTimeConverter.class)
	@JsonFormat(pattern = DateUtils.DATE_PATTERN)
	@DateTimeFormat(pattern = DateUtils.DATE_PATTERN)
	@Schema(description = "生日")
	private LocalDateTime birthday;

	@ExcelProperty(value = "学员类型",converter = DictConverter.class)
	@Dict(key = "student_type",ref = "studentTypeLabel")
	@Schema(description = "学员类型")
	private String studentType;

	@ExcelIgnore
	@Schema(description = "学员类型标签")
	private String studentTypeLabel;

	@ExcelProperty(value = "学员来源类型",converter = DictConverter.class)
	@Dict(key = "student_source_type")
	@Schema(description = "学员来源类型")
	private String sourceType;

	@ExcelProperty("推荐人姓名")
	@Schema(description = "推荐人姓名")
	private String referrer;

	@ExcelProperty("在读学校名称")
	@Schema(description = "在读学校名称")
	private String school;

	@ExcelProperty("在读年级")
	@Schema(description = "在读年级")
	private String grade;

	@ExcelIgnore
	@Schema(description = "学员头像URL或路径")
	private String avatar;

	@ExcelProperty(value = "学员状态",converter = DictConverter.class)
	@Dict(key = "student_status")
	@Schema(description = "状态normal/block")
	private String status;

	@ExcelIgnore
	@Schema(description = "学员登录密码")
	private String password;

	@ExcelProperty("备用手机号")
	@Schema(description = "备用手机号")
	private String alternatePhone;

	@ExcelProperty("备用手机号归属")
	@Schema(description = "备用手机号归属")
	private String alternatePhoneBelong;

	@ExcelProperty("家庭地址")
	@Schema(description = "家庭地址")
	private String address;

	@ExcelProperty("学员身份证号")
	@Schema(description = "学员身份证号")
	private String idCard;

	@ExcelProperty(value = "意向程度",converter = DictConverter.class)
	@Dict(key = "student_intention_level")
	@Schema(description = "意向级别或程度")
	private String intentionLevel;

	@ExcelProperty(value = "跟进状态",converter = DictConverter.class)
	@Dict(key = "student_followup_status")
	@Schema(description = "跟进状态")
	private String followupStatus;

	@ExcelProperty("跟进人")
	@Schema(description = "跟进人")
	private String followupPerson;

	@ExcelProperty("学管师姓名")
	@Schema(description = "学管师姓名")
	private String academicAdvisor;

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