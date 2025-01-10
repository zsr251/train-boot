package com.train.system.entity;

import com.train.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;

/**
 * 数据权限
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-12-06
 */

@Data
@TableName("sys_menu_data_permission")
public class SysMenuDataPermissionEntity extends BaseEntity {

	/**
	* 关联前端组件
	*/
	private String component;

	/**
	* 规则名称
	*/
	private String ruleName;

	/**
	* 规则类型
	*/
	private String ruleType;

	/**
	* 规则标识
	*/
	private String ruleFlag;

	/**
	* 规则内容
	*/
	private String ruleValue;
}