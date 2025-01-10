package com.train.train.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;
import com.train.framework.mybatis.entity.BaseEntity;

/**
 * 学员
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tt_student")
public class StudentEntity extends BaseEntity {

	/**
	* 姓名
	*/
	private String studentName;

	/**
	* 手机号
	*/
	private String phone;

	/**
	* 手机号归属
	*/
	private String phoneBelong;

	/**
	* 性别
	*/
	private String gender;

	/**
	* 生日
	*/
	private LocalDateTime birthday;
	/**
	 * 学员类型
	 */
	private String studentType;
	/**
	* 学员来源类型
	*/
	private String sourceType;

	/**
	* 推荐人姓名
	*/
	private String referrer;

	/**
	* 在读学校名称
	*/
	private String school;

	/**
	* 在读年级
	*/
	private String grade;

	/**
	* 学员头像URL或路径
	*/
	private String avatar;

	/**
	* 状态normal/block
	*/
	private String status;

	/**
	* 学员登录密码
	*/
	private String password;

	/**
	* 备用手机号
	*/
	private String alternatePhone;

	/**
	* 备用手机号归属
	*/
	private String alternatePhoneBelong;

	/**
	* 家庭地址
	*/
	private String address;

	/**
	* 学员身份证号
	*/
	private String idCard;

	/**
	* 意向级别或程度
	*/
	private String intentionLevel;

	/**
	* 跟进状态
	*/
	private String followupStatus;

	/**
	* 跟进人
	*/
	private String followupPerson;

	/**
	* 学管师姓名
	*/
	private String academicAdvisor;

	/**
	* 备注
	*/
	private String remark;
	/**
	 * 登录token
	 */
	private String token;







	/**
	* 租户ID
	*/
	private Long tenantId;

}