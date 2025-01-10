SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Blob类型的触发器表';

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日历信息表';

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Cron类型的触发器表';

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint(13) NOT NULL COMMENT '触发的时间',
  `sched_time` bigint(13) NOT NULL COMMENT '定时器制定的时间',
  `priority` int(11) NOT NULL COMMENT '优先级',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='已触发的触发器表';

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) NOT NULL COMMENT '任务组名',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务详细信息表';

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存储的悲观锁信息表';

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='暂停的触发器表';

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint(13) NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint(13) NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='调度器状态表';

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint(7) NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint(12) NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint(10) NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='简单触发器的信息表';

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int(11) DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int(11) DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint(20) DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint(20) DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='同步机制的行锁表';

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint(13) DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint(13) DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) NOT NULL COMMENT '触发器的类型',
  `start_time` bigint(13) NOT NULL COMMENT '开始时间',
  `end_time` bigint(13) DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint(2) DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='触发器详细信息表';

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `job_name` varchar(200) DEFAULT NULL COMMENT '名称',
  `job_group` varchar(100) DEFAULT NULL COMMENT '分组',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method` varchar(100) DEFAULT NULL COMMENT '执行方法',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(3) unsigned DEFAULT NULL COMMENT '状态  0：暂停  1：正常',
  `concurrent` tinyint(3) unsigned DEFAULT NULL COMMENT '是否并发  0：禁止  1：允许',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
BEGIN;
INSERT INTO `schedule_job` (`id`, `job_name`, `job_group`, `bean_name`, `method`, `params`, `cron_expression`, `status`, `concurrent`, `remark`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, '测试任务', 'system', 'testTask', 'run', '123', '0 * * * * ? *', 0, 0, '', 14, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
COMMIT;

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(100) DEFAULT NULL COMMENT '任务组名',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method` varchar(100) DEFAULT NULL COMMENT '执行方法',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(3) unsigned NOT NULL COMMENT '任务状态    0：失败    1：成功',
  `error` varchar(2000) DEFAULT NULL COMMENT '异常信息',
  `times` bigint(20) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '附件名称',
  `url` varchar(255) NOT NULL COMMENT '附件地址',
  `size` bigint(20) DEFAULT NULL COMMENT '附件大小',
  `platform` varchar(50) DEFAULT NULL COMMENT '存储平台',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='附件管理';

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------
BEGIN;
INSERT INTO `sys_attachment` (`id`, `name`, `url`, `size`, `platform`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 'login.png', 'http://127.0.0.1/api/upload/20241218/login_84064.png', 61022, 'LOCAL', NULL, 0, 0, 10000, '2024-12-18 23:21:04', 10000, '2024-12-18 23:21:04');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_type_id` bigint(20) NOT NULL COMMENT '字典类型ID',
  `dict_label` varchar(255) NOT NULL COMMENT '字典标签',
  `dict_value` varchar(255) DEFAULT NULL COMMENT '字典值',
  `label_class` varchar(100) DEFAULT NULL COMMENT '标签样式',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8mb4 COMMENT='字典数据';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 1, '停用', '0', 'danger', '', 1, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 1, '正常', '1', 'primary', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 2, '男', '1', 'primary', '', 1, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-19 15:36:50');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 2, '女', '0', 'success', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-19 15:36:44');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 2, '未知', '2', 'warning', '', 2, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (6, 3, '正常', '1', 'primary', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 3, '停用', '0', 'danger', '', 1, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (8, 4, '全部数据', '0', '', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (9, 4, '本机构及子机构数据', '1', '', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10, 4, '本机构数据', '2', '', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (11, 4, '本人数据', '3', '', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (12, 4, '自定义数据', '4', '', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (13, 5, '禁用', '0', 'danger', '', 1, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (14, 5, '启用', '1', 'primary', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (15, 6, '失败', '0', 'danger', '', 1, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (16, 6, '成功', '1', 'primary', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (17, 7, '登录成功', '0', 'primary', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (18, 7, '退出成功', '1', 'warning', '', 1, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (19, 7, '验证码错误', '2', 'danger', '', 2, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (20, 7, '账号密码错误', '3', 'danger', '', 3, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (21, 8, '否', '0', 'primary', '', 1, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (22, 8, '是', '1', 'danger', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (23, 9, '是', '1', 'danger', '', 1, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (24, 9, '否', '0', 'primary', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (25, 10, '其它', '0', 'info', '', 10, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (26, 10, '查询', '1', 'primary', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (27, 10, '新增', '2', 'success', '', 1, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (28, 10, '修改', '3', 'warning', '', 2, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (29, 10, '删除', '4', 'danger', '', 3, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (30, 10, '导出', '5', 'info', '', 4, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (31, 10, '导入', '6', 'info', '', 5, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (32, 11, '阿里云', '0', '', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (33, 11, '腾讯云', '1', '', '', 1, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (34, 11, '七牛云', '2', '', '', 2, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (35, 11, '华为云', '3', '', '', 3, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (36, 12, '本地', '-1', '', '', 0, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (37, 12, '阿里云', '0', '', '', 1, 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (38, 13, '手持设备', '1', 'primary', '', 0, 10000, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (39, 13, '柜体', '2', 'primary', '', 1, 10000, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (40, 13, '传感设备', '3', 'primary', '', 2, 10000, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (41, 14, '离线状态', '0', 'danger', NULL, 0, 10000, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (42, 14, '在线状态', '1', 'success', NULL, 1, 10000, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (43, 15, '远程锁定', 'LOCK', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (44, 15, '远程解锁', 'UNLOCK', NULL, NULL, 1, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (45, 15, '登录', 'SIGN_ON', NULL, NULL, 2, NULL, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (46, 15, '登出', 'SIGN_OFF', NULL, NULL, 3, NULL, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (47, 15, 'OTA升级', 'OTA_UPGRADE', NULL, NULL, 4, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (48, 16, 'MQTT', 'MQTT', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (49, 16, 'TCP', 'TCP', NULL, NULL, 1, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (50, 16, 'UDP', 'UDP', NULL, NULL, 2, NULL, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (51, 16, 'BLE', 'BLE', NULL, NULL, 3, NULL, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (52, 16, 'CoAP', 'CoAP', NULL, NULL, 4, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (53, 16, 'LwM2M', 'LwM2M', NULL, NULL, 4, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (54, 16, 'Modbus', 'Modbus', NULL, NULL, 4, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (55, 17, '运行状态', 'RUNNING_STATUS', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (56, 17, 'APP版本', 'APP_VERSION', NULL, NULL, 1, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (57, 17, '电池电量百分比', 'BATTERY_PERCENT', NULL, NULL, 2, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (58, 17, '温度', 'TEMPERATURE', NULL, NULL, 3, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (59, 18, '下线', 'OFFLINE', 'danger', NULL, 1, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (60, 18, '上线', 'ONLINE', 'primary', NULL, 2, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (61, 18, '登录', 'SIGN_ON', 'primary', NULL, 3, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (62, 18, '退出登录', 'SIGN_OFF', 'danger', NULL, 4, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (63, 18, 'OTA升级', 'OTA_UPGRADE', 'primary', NULL, 5, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (64, 18, '设备远程锁定', 'LOCK', 'primary', NULL, 6, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (65, 18, '设备远程解锁', 'UNLOCK', 'primary', NULL, 7, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (66, 18, 'APP版本信息', 'APP_VERSION_REPORT', 'primary', NULL, 8, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (67, 18, '电池电量', 'BATTERY_PERCENT_REPORT', 'primary', NULL, 9, NULL, 0, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (68, 18, '温度', 'TEMPERATURE_REPORT', 'primary', NULL, 0, NULL, 10, 0, 10000, '2024-11-12 07:36:11', 10000, '2024-11-12 07:36:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (69, 19, '默认', 'default', '', '', 0, 10000, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (70, 19, '系统', 'system', '', '', 1, 10000, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (71, 20, '暂停', '0', 'danger', '', 0, 10000, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (72, 20, '正常', '1', 'primary', '', 1, 10000, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (73, 21, '无意向', '0', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:15:22', 10000, '2024-11-19 16:15:22');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (74, 21, '轻微意向', '1', NULL, NULL, 1, NULL, 0, 0, 10000, '2024-11-19 16:15:34', 10000, '2024-11-19 16:15:34');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (75, 21, '一般意向', '2', NULL, NULL, 2, NULL, 0, 0, 10000, '2024-11-19 16:15:49', 10000, '2024-11-19 16:15:55');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (76, 21, '较强意向', '4', NULL, NULL, 3, NULL, 0, 0, 10000, '2024-11-19 16:16:20', 10000, '2024-11-19 16:16:20');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (77, 21, '最强意向', '5', NULL, NULL, 4, NULL, 0, 0, 10000, '2024-11-19 16:16:55', 10000, '2024-11-19 16:16:55');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (78, 22, '未跟进', '0', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:18:30', 10000, '2024-11-19 16:18:30');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (79, 22, '跟进中', '1', NULL, NULL, 1, NULL, 0, 0, 10000, '2024-11-19 16:18:41', 10000, '2024-11-19 16:18:41');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (80, 22, '停止跟进', '2', NULL, NULL, 2, NULL, 0, 0, 10000, '2024-11-19 16:18:51', 10000, '2024-11-19 16:18:57');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (81, 23, '潜在学员', 'wait', 'primary', NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:27:35', 10000, '2024-12-05 00:02:25');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (82, 23, '在读学员', 'ing', 'success', NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:28:22', 10000, '2024-12-05 00:02:30');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (83, 23, '历史学员', 'history', 'warning', NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:29:20', 10000, '2024-12-05 00:02:41');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (84, 24, '自然获客', 'natural', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:36:28', 10000, '2024-11-19 16:36:28');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (85, 24, '转介绍', 'referral', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:37:36', 10000, '2024-11-19 16:37:36');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (86, 24, '业务员', 'salesman', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:38:36', 10000, '2024-11-19 16:38:36');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (87, 25, '正常', 'normal', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:40:28', 10000, '2024-11-19 16:40:28');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (88, 25, '冻结', 'block', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:40:41', 10000, '2024-11-19 16:40:41');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (89, 26, '全职', 'full', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-20 23:04:38', 10000, '2024-11-20 23:04:38');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (90, 26, '兼职', 'part', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-20 23:04:54', 10000, '2024-11-20 23:04:54');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (91, 27, '未入职', '0', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-20 23:06:11', 10000, '2024-11-20 23:06:11');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (92, 27, '在职', '1', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-20 23:06:18', 10000, '2024-11-20 23:06:18');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (93, 27, '离职', '2', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-20 23:06:29', 10000, '2024-11-20 23:06:29');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (94, 29, '未开始', 'wait', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 14:30:51', 10000, '2024-11-21 23:01:08');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (95, 29, '正常', 'normal', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 14:31:10', 10000, '2024-11-21 23:01:19');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (96, 29, '已结束', 'finish', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 14:31:56', 10000, '2024-11-21 23:01:48');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (97, 30, '一对一', '1', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 14:33:05', 10000, '2024-11-21 14:33:05');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (98, 30, '一对多', 'n', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 14:33:19', 10000, '2024-11-21 14:34:52');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (99, 31, '正常', 'normal', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 23:04:56', 10000, '2024-11-21 23:04:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (100, 31, '取消', 'cancel', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 23:05:06', 10000, '2024-11-21 23:05:06');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (101, 31, '已分班', 'assign', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 23:05:18', 10000, '2024-11-21 23:05:18');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (102, 33, '正常', 'normal', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-22 16:23:18', 10000, '2024-11-22 16:23:18');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (103, 33, '禁用', 'disable', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-22 16:23:42', 10000, '2024-11-24 22:37:55');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (104, 34, '正常', 'normal', 'success', NULL, 0, NULL, 0, 0, 10000, '2024-11-23 16:13:31', 10000, '2024-12-13 00:03:37');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (105, 34, '结班', 'finish', 'info', NULL, 0, NULL, 0, 0, 10000, '2024-11-23 16:13:49', 10000, '2024-12-13 00:04:00');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (106, 34, '未开始', 'wait', 'warning', NULL, 0, NULL, 0, 0, 10000, '2024-11-23 16:14:06', 10000, '2024-12-13 00:03:54');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (107, 36, '正常', 'normal', 'success', NULL, 0, NULL, 0, 0, 10000, '2024-11-24 22:55:18', 10000, '2024-12-05 00:01:38');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (108, 36, '已退班', 'quit', 'danger', NULL, 0, NULL, 0, 0, 10000, '2024-11-24 22:55:32', 10000, '2024-12-05 00:01:43');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (109, 36, '已退班', 'quit', NULL, NULL, 0, NULL, 0, 1, 10000, '2024-11-24 22:55:32', 10000, '2024-11-24 22:56:02');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (110, 35, '未排课', 'wait', 'primary', NULL, 0, NULL, 0, 0, 10000, '2024-11-25 15:09:32', 10000, '2024-12-13 00:04:09');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (111, 35, '已排课', 'done', 'success', NULL, 0, NULL, 0, 0, 10000, '2024-11-25 15:09:44', 10000, '2024-12-13 00:04:16');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (112, 35, '排课中', 'ing', 'danger', NULL, 0, NULL, 0, 0, 10000, '2024-11-26 09:36:09', 10000, '2024-12-13 00:04:22');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (113, 37, '未开始', 'wait', 'primary', NULL, 0, NULL, 0, 0, 10000, '2024-11-26 10:21:28', 10000, '2024-12-05 00:51:03');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (114, 37, '已取消', 'cancel', 'danger', NULL, 0, NULL, 0, 0, 10000, '2024-11-26 10:22:19', 10000, '2024-12-05 00:51:09');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (115, 37, '已调课', 'reassign', 'warning', NULL, 0, NULL, 0, 0, 10000, '2024-11-26 10:26:00', 10000, '2024-12-13 10:56:56');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (116, 37, '已完成', 'done', 'success', NULL, 0, NULL, 0, 0, 10000, '2024-11-26 10:28:57', 10000, '2024-12-05 00:51:21');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (117, 38, '固定频率', 'frequency', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 11:17:15', 10000, '2024-11-26 11:17:15');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (118, 38, '指定日期', 'appoint', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 11:17:30', 10000, '2024-11-26 11:17:30');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (119, 39, '每日', 'daily', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 11:18:27', 10000, '2024-11-26 11:18:27');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (120, 39, '每周', 'weekly', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 11:18:54', 10000, '2024-11-26 11:18:54');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (121, 39, '每月', 'monthly', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 11:19:15', 10000, '2024-11-26 11:19:15');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (122, 40, '不忽略', '0', 'success', NULL, 0, NULL, 0, 0, 10000, '2024-11-26 16:44:44', 10000, '2024-11-26 16:44:44');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (123, 40, '忽略', '1', 'danger', NULL, 0, NULL, 0, 0, 10000, '2024-11-26 16:44:57', 10000, '2024-11-26 16:45:05');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (124, 41, '不排除', 'no', NULL, NULL, 0, NULL, 0, 1, 10000, '2024-11-26 22:42:44', 10000, '2024-11-26 22:51:01');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (125, 41, '不排除节假日', 'appoint', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 22:43:02', 10000, '2024-11-27 16:14:14');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (126, 41, '排除节假日', 'holiday', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 22:44:03', 10000, '2024-11-27 16:14:25');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (127, 42, '等待', 'wait', 'primary', NULL, 0, NULL, 0, 0, 10000, '2024-12-02 16:28:45', 10000, '2024-12-05 00:08:24');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (128, 42, '已到', 'arrived', 'success', NULL, 0, NULL, 0, 0, 10000, '2024-12-02 16:28:57', 10000, '2024-12-05 00:08:29');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (129, 42, '迟到', 'delay', 'warning', NULL, 0, NULL, 0, 0, 10000, '2024-12-02 16:29:11', 10000, '2024-12-05 00:08:51');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (130, 42, '缺席', 'absent', 'danger', NULL, 0, NULL, 0, 0, 10000, '2024-12-02 16:29:22', 10000, '2024-12-05 00:08:44');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (131, 42, '请假', 'leave', 'info', NULL, 0, NULL, 0, 0, 10000, '2024-12-02 16:29:34', 10000, '2024-12-05 00:08:57');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (132, 43, '正式学员', 'student', 'success', NULL, 0, NULL, 0, 0, 10000, '2024-12-05 00:05:25', 10000, '2024-12-05 00:07:29');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (133, 43, '试听学员', 'audition', 'danger', NULL, 0, NULL, 0, 0, 10000, '2024-12-05 00:05:43', 10000, '2024-12-05 00:07:34');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (134, 44, '自定义SQL片段', 'sql_rule', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-12-05 23:47:00', 10000, '2024-12-05 23:47:00');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (135, 44, '只允许展示列', 'col_can_only', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-12-05 23:47:10', 10000, '2024-12-05 23:47:10');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (136, 44, '不允许展示列', 'col_ban', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-12-05 23:47:21', 10000, '2024-12-05 23:47:21');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (137, 45, '待支付', 'wait', 'primary', NULL, 0, NULL, 0, 0, 10000, '2024-12-06 23:32:33', 10000, '2024-12-06 23:32:33');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (138, 45, '已支付', 'paid', 'success', NULL, 0, NULL, 0, 0, 10000, '2024-12-06 23:32:46', 10000, '2024-12-06 23:32:46');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (139, 45, '已取消', 'cancel', 'danger', NULL, 0, NULL, 0, 0, 10000, '2024-12-06 23:32:59', 10000, '2024-12-06 23:32:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (140, 46, '未结算', 'wait', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-12-12 15:55:38', 10000, '2024-12-12 15:55:38');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (141, 46, '已结算', 'done', NULL, NULL, 0, NULL, 0, 0, 10000, '2024-12-12 15:55:48', 10000, '2024-12-12 15:55:48');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `dict_name` varchar(255) NOT NULL COMMENT '字典名称',
  `dict_source` tinyint(4) DEFAULT '0' COMMENT '来源  0：字典数据  1：动态SQL',
  `dict_sql` varchar(500) DEFAULT NULL COMMENT '动态SQL',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级节点',
  `has_child` tinyint(4) DEFAULT '0' COMMENT '是否有子节点',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COMMENT='字典类型';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 'post_status', '状态', 0, NULL, '岗位管理', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 'user_gender', '性别', 0, NULL, '用户管理', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 'user_status', '状态', 0, NULL, '用户管理', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 'role_data_scope', '数据范围', 0, NULL, '角色管理', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 'enable_disable', '状态', 0, NULL, '功能状态：启用 | 禁用 ', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (6, 'success_fail', '状态', 0, NULL, '操作状态：成功 | 失败', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 'login_operation', '操作信息', 0, NULL, '登录管理', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (8, 'params_type', '系统参数', 0, NULL, '参数管理', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (9, 'user_super_admin', '用户是否是超管', 0, NULL, '用户是否是超管', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10, 'log_operate_type', '操作类型', 0, NULL, '操作日志', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (11, 'sms_platform', '短信平台类型', 0, NULL, '短信管理', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (12, 'mail_platform', '邮件平台类型', 0, NULL, '邮件管理', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (13, 'device_type', '设备类型', 0, NULL, '设备类型', 0, NULL, 0, 10000, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-18 23:16:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (14, 'device_running_status', '设备运行状态', 0, NULL, '设备运行状态：离线|在线|待机|使用中|OTA升级中', 0, NULL, 0, 10000, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-18 23:17:03');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (15, 'device_command', '设备指令', 0, NULL, '设备服务具备的功能', 0, NULL, 0, 10000, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-18 23:17:08');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (16, 'device_protocol_type', '设备协议类型', 0, NULL, '设备协议类型', 0, NULL, 0, 10000, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-18 23:17:12');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (17, 'device_property', '设备属性', 0, NULL, '设备通用属性：运行状态|APP版本|电池电量百分比|温度', 0, NULL, 0, 10000, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-18 23:17:17');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (18, 'device_event_type', '事件类型', 0, NULL, '事件日志类型', 0, NULL, 0, 10000, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-18 23:17:22');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (19, 'schedule_group', '任务组名', 0, NULL, '定时任务', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (20, 'schedule_status', '状态', 0, NULL, '定时任务', 0, NULL, 0, 10000, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (21, 'student_intention_level', '学员意向程度', 0, NULL, NULL, 0, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:14:52', 10000, '2024-11-19 16:14:52');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (22, 'student_followup_status', '学员跟进状态', 0, NULL, NULL, 1, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:17:57', 10000, '2024-11-19 16:17:57');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (23, 'student_type', '学员类型', 0, NULL, NULL, 1, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:20:10', 10000, '2024-11-19 16:20:10');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (24, 'student_source_type', '学员来源类型', 0, NULL, NULL, 1, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:20:48', 10000, '2024-11-19 16:21:04');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (25, 'student_status', '学员账户状态', 0, NULL, NULL, 1, NULL, 0, NULL, 0, 0, 10000, '2024-11-19 16:39:48', 10000, '2024-11-19 16:40:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (26, 'teacher_job_type', '老师工作类型', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-20 23:04:11', 10000, '2024-11-20 23:04:23');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (27, 'teacher_status', '老师状态', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-20 23:05:29', 10000, '2024-11-20 23:05:42');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (28, 'teacher_code', '老师编码', 1, 'select teacher_code as dictValue,teacher_name as dictLabel from tt_teacher where deleted = 0', NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 12:01:23', 10000, '2024-11-21 12:01:23');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (29, 'course_status', '课程状态', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 14:29:55', 10000, '2024-11-21 14:29:55');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (30, 'class_type', '班级类型', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 14:32:47', 10000, '2024-11-21 14:32:47');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (31, 'course_enroll_status', '课程报名状态', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-21 23:04:40', 10000, '2024-11-21 23:04:40');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (32, 'course_code', '课程编码', 1, 'select course_code as dictValue,course_name as dictLabel from tt_course where deleted = 0', NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-22 11:31:48', 10000, '2024-11-22 11:31:48');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (33, 'classroom_status', '教室状态', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-22 16:22:50', 10000, '2024-11-22 16:22:50');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (34, 'class_status', '班级状态', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-23 16:13:07', 10000, '2024-11-23 16:13:07');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (35, 'course_schedule_status', '排课状态', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-24 22:39:38', 10000, '2024-11-25 15:07:09');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (36, 'class_student_status', '班级学员状态', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-24 22:55:02', 10000, '2024-11-24 22:55:02');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (37, 'lesson_status', '课程计划状态', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 10:21:02', 10000, '2024-11-26 10:21:02');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (38, 'schedule_type', '排课规则类型', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 11:16:47', 10000, '2024-11-26 11:16:47');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (39, 'frequency_type', '排课固定频率类型', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 11:18:09', 10000, '2024-11-26 11:18:09');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (40, 'ignore_conflict', '忽略排课冲突', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 16:44:23', 10000, '2024-11-26 16:44:23');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (41, 'schedule_day_exclude_type', '排课排除日期类型', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-11-26 22:42:26', 10000, '2024-11-26 22:42:26');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (42, 'lesson_arrival_status', '到课状态', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-12-02 16:27:59', 10000, '2024-12-02 16:27:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (43, 'lesson_member_type', '上课成员类型', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-12-05 00:05:08', 10000, '2024-12-05 00:05:08');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (44, 'rule_type', '数据权限类型', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-12-05 23:46:39', 10000, '2024-12-05 23:46:39');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (45, 'order_status', '订单状态', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-12-06 23:31:58', 10000, '2024-12-06 23:31:58');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `pid`, `has_child`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (46, 'settlement_status', '结算状态', 0, NULL, NULL, 2, NULL, 0, NULL, 0, 0, 10000, '2024-12-12 15:55:08', 10000, '2024-12-12 15:55:08');
COMMIT;

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `ip` varchar(32) DEFAULT NULL COMMENT '登录IP',
  `address` varchar(32) DEFAULT NULL COMMENT '登录地点',
  `user_agent` varchar(500) DEFAULT NULL COMMENT 'User Agent',
  `status` tinyint(4) DEFAULT NULL COMMENT '登录状态  0：失败   1：成功',
  `operation` tinyint(3) unsigned DEFAULT NULL COMMENT '操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=utf8mb4 COMMENT='登录日志';

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for sys_log_operate
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_operate`;
CREATE TABLE `sys_log_operate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `module` varchar(100) DEFAULT NULL COMMENT '模块名',
  `name` varchar(100) DEFAULT NULL COMMENT '操作名',
  `req_uri` varchar(200) DEFAULT NULL COMMENT '请求URI',
  `req_method` varchar(20) DEFAULT NULL COMMENT '请求方法',
  `req_params` text COMMENT '请求参数',
  `ip` varchar(32) DEFAULT NULL COMMENT '操作IP',
  `address` varchar(32) DEFAULT NULL COMMENT '登录地点',
  `user_agent` varchar(500) DEFAULT NULL COMMENT 'User Agent',
  `operate_type` tinyint(4) DEFAULT NULL COMMENT '操作类型',
  `duration` int(11) NOT NULL COMMENT '执行时长',
  `status` tinyint(4) DEFAULT NULL COMMENT '操作状态  0：失败   1：成功',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `real_name` varchar(50) DEFAULT NULL COMMENT '操作人',
  `result_msg` varchar(500) DEFAULT NULL COMMENT '返回消息',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=388 DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';

-- ----------------------------
-- Records of sys_log_operate
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for sys_mail_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_mail_config`;
CREATE TABLE `sys_mail_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `platform` tinyint(4) DEFAULT NULL COMMENT '平台类型  -1：本地   0：阿里云',
  `group_name` varchar(100) DEFAULT NULL COMMENT '分组名称，发送邮件时，可指定分组',
  `mail_host` varchar(100) DEFAULT NULL COMMENT 'SMTP服务器',
  `mail_port` int(11) DEFAULT NULL COMMENT 'SMTP端口',
  `mail_from` varchar(100) DEFAULT NULL COMMENT '发件人邮箱',
  `mail_pass` varchar(100) DEFAULT NULL COMMENT '发件人密码',
  `region_id` varchar(100) DEFAULT NULL COMMENT 'regionId',
  `endpoint` varchar(100) DEFAULT NULL COMMENT '阿里云 endpoint',
  `access_key` varchar(100) DEFAULT NULL COMMENT 'AccessKey',
  `secret_key` varchar(100) DEFAULT NULL COMMENT 'SecretKey',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：启用',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='邮件平台';

-- ----------------------------
-- Records of sys_mail_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_mail_config` (`id`, `platform`, `group_name`, `mail_host`, `mail_port`, `mail_from`, `mail_pass`, `region_id`, `endpoint`, `access_key`, `secret_key`, `status`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, -1, 'test', NULL, NULL, 'baba_tv@163.com', 'TZNVURLYVBNJUNBB', '', '', NULL, NULL, 1, 1, 1, 10000, '2024-11-12 06:56:56', 10000, '2024-12-18 23:18:26');
COMMIT;

-- ----------------------------
-- Table structure for sys_mail_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_mail_log`;
CREATE TABLE `sys_mail_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `platform_id` bigint(20) DEFAULT NULL COMMENT '平台ID',
  `platform` tinyint(4) DEFAULT NULL COMMENT '平台类型',
  `mail_from` varchar(100) DEFAULT NULL COMMENT '发件人邮箱',
  `mail_tos` varchar(1000) DEFAULT NULL COMMENT '接受人邮箱',
  `subject` varchar(200) DEFAULT NULL COMMENT '邮件主题',
  `content` text COMMENT '邮件内容',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：失败   1：成功',
  `error` varchar(2000) DEFAULT NULL COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件日志';

-- ----------------------------
-- Records of sys_mail_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级ID',
  `name` varchar(200) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `authority` varchar(500) DEFAULT NULL COMMENT '授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型   0：菜单   1：按钮   2：接口',
  `open_style` tinyint(4) DEFAULT NULL COMMENT '打开方式   0：内部   1：外部',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `has_data_permission` tinyint(4) DEFAULT '0' COMMENT '是否有数据权限',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, NULL, '系统设置', NULL, NULL, 0, 0, 'icon-setting', 0, 11, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-18 14:24:12');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 1, '菜单管理', 'sys/menu/index', NULL, 0, 0, 'icon-menu', 0, 0, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-12 06:56:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 2, '查看', '', 'sys:menu:list', 1, 0, '', 0, 0, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-12 06:56:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 2, '新增', '', 'sys:menu:save', 1, 0, '', 0, 1, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-12 06:56:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 2, '修改', '', 'sys:menu:update,sys:menu:info', 1, 0, '', 0, 2, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-12 06:56:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (6, 2, '删除', '', 'sys:menu:delete', 1, 0, '', 0, 3, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-12 06:56:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 1, '数据字典', 'sys/dict/type', '', 0, 0, 'icon-insertrowabove', 0, 1, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-12 06:56:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (8, 7, '查询', '', 'sys:dict:page', 1, 0, '', 0, 0, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-12 06:56:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (9, 7, '新增', '', 'sys:dict:save', 1, 0, '', 0, 2, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-12 06:56:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10, 7, '修改', '', 'sys:dict:update,sys:dict:info', 1, 0, '', 0, 1, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-12 06:56:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (11, 7, '删除', '', 'sys:dict:delete', 1, 0, '', 0, 3, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-12 06:56:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (12, NULL, '权限管理', '', '', 0, 0, 'icon-safetycertificate', 0, 10, 0, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-11-18 14:24:07');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (13, 12, '岗位管理', 'sys/post/index', '', 0, 0, 'icon-solution', 0, 2, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (14, 13, '查询', '', 'sys:post:page', 1, 0, '', 0, 0, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (15, 13, '新增', '', 'sys:post:save', 1, 0, '', 0, 1, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (16, 13, '修改', '', 'sys:post:update,sys:post:info', 1, 0, '', 0, 2, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (17, 13, '删除', '', 'sys:post:delete', 1, 0, '', 0, 3, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (18, 12, '机构管理', 'sys/org/index', '', 0, 0, 'icon-cluster', 0, 1, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (19, 18, '查询', '', 'sys:org:list', 1, 0, '', 0, 0, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (20, 18, '新增', '', 'sys:org:save', 1, 0, '', 0, 1, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (21, 18, '修改', '', 'sys:org:update,sys:org:info', 1, 0, '', 0, 2, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (22, 18, '删除', '', 'sys:org:delete', 1, 0, '', 0, 3, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (23, 12, '角色管理', 'sys/role/index', '', 0, 0, 'icon-team', 0, 3, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (24, 23, '查询', '', 'sys:role:page', 1, 0, '', 0, 0, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (25, 23, '新增', '', 'sys:role:save,sys:role:menu,sys:org:list', 1, 0, '', 0, 1, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (26, 23, '修改', '', 'sys:role:update,sys:role:info,sys:role:menu,sys:org:list,sys:user:page', 1, 0, '', 0, 2, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (27, 23, '删除', '', 'sys:role:delete', 1, 0, '', 0, 3, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (28, 12, '用户管理', 'sys/user/index', '', 0, 0, 'icon-user', 0, 0, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (29, 28, '查询', '', 'sys:user:page', 1, 0, '', 0, 0, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (30, 28, '新增', '', 'sys:user:save,sys:role:list', 1, 0, '', 0, 1, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (31, 28, '修改', '', 'sys:user:update,sys:user:info,sys:role:list', 1, 0, '', 0, 2, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (32, 28, '删除', '', 'sys:user:delete', 1, 0, '', 0, 3, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (33, NULL, '应用管理', '', '', 0, 0, 'icon-appstore', 0, 12, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-18 14:24:19');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (34, NULL, '日志管理', '', '', 0, 0, 'icon-filedone', 0, 13, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-18 14:24:27');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (35, 34, '登录日志', 'sys/log/login', 'sys:log:login', 0, 0, 'icon-solution', 0, 0, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (36, 28, '导入', '', 'sys:user:import', 1, 0, '', 0, 5, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (37, 28, '导出', '', 'sys:user:export', 1, 0, '', 0, 6, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (38, 1, '参数管理', 'sys/params/index', 'sys:params:all', 0, 0, 'icon-filedone', 0, 2, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (39, 1, '接口文档', '{{apiUrl}}/doc.html', NULL, 0, 1, 'icon-file-text-fill', 0, 10, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (40, 34, '操作日志', 'sys/log/operate', 'sys:operate:all', 0, 0, 'icon-file-text', 0, 1, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (41, 1, '系统配置', 'sys/config/index', NULL, 0, 0, 'icon-safetycertificate', 0, 4, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (42, 41, '短信配置', '', 'sys:sms:config', 1, 0, '', 0, 0, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (43, 41, '邮件配置', '', 'sys:mail:config', 1, 0, '', 0, 1, 0, 0, 10000, '2024-11-12 06:56:54', 10000, '2024-11-12 06:56:54');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (44, 41, '第三方登录', '', 'sys:third:config', 1, 0, '', 0, 2, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (45, NULL, '基础工具', '', '', 0, 0, 'icon-wrench-fill', 0, 15, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-18 14:24:36');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (46, 45, '短信发送', 'sys/tool/sms/index', 'sys:sms:log', 0, 0, 'icon-message', 0, 1, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (47, 45, '邮件发送', 'sys/tool/mail/index', 'sys:mail:log', 0, 0, 'icon-mail', 0, 2, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (48, 45, '附件管理', 'sys/attachment/index', NULL, 0, 0, 'icon-folder-fill', 0, 3, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (49, 48, '查看', '', 'sys:attachment:page', 1, 0, '', 0, 0, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (50, 48, '上传', '', 'sys:attachment:save', 1, 0, '', 0, 1, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (51, 48, '删除', '', 'sys:attachment:delete', 1, 0, '', 0, 1, 0, 0, 10000, '2024-11-12 06:56:55', 10000, '2024-11-12 06:56:55');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (54, NULL, 'IOT', NULL, NULL, 0, 0, NULL, 0, 16, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-10 10:38:11');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (55, 54, '设备列表', 'iot/device/index', NULL, 0, 0, 'icon-menu', 0, 0, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-10 10:38:09');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (56, 55, '查看', '', 'iot:device:page', 1, 0, '', 0, 0, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-10 10:37:45');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (57, 55, '新增', '', 'iot:device:save', 1, 0, '', 0, 1, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-10 10:37:49');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (58, 55, '修改', '', 'iot:device:update,iot:device:info', 1, 0, '', 0, 2, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-10 10:37:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (59, 55, '删除', '', 'iot:device:delete', 1, 0, '', 0, 3, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-10 10:37:56');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (60, 55, '下发指令', '', 'iot:device:send', 1, 0, '', 0, 4, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-10 10:37:59');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (61, 55, '上报数据', '', 'iot:device:report', 1, 0, '', 0, 5, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-10 10:38:01');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (62, 55, '设备事件日志', '', 'iot:device_event_log:page', 1, 0, '', 0, 5, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-10 10:38:03');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (63, 55, '设备服务日志', '', 'iot:device_service_log:page', 1, 0, '', 0, 5, 0, 1, 10000, '2024-11-12 07:36:11', 10000, '2024-12-10 10:38:06');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (64, 33, '系统监控', '', '', 0, 0, 'icon-Report', 0, 10, 0, 0, 10000, '2024-11-12 07:36:39', 10000, '2024-11-12 07:36:39');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (65, 64, '服务监控', 'monitor/server/index', 'monitor:server:all', 0, 0, 'icon-sever', 0, 0, 0, 0, 10000, '2024-11-12 07:36:39', 10000, '2024-11-12 07:36:39');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (66, 64, '缓存监控', 'monitor/cache/index', 'monitor:cache:all', 0, 0, 'icon-fund-fill', 0, 2, 0, 0, 10000, '2024-11-12 07:36:39', 10000, '2024-11-12 07:36:39');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (67, 64, '在线用户', 'monitor/user/index', 'monitor:user:all', 0, 0, 'icon-user', 0, 3, 0, 0, 10000, '2024-11-12 07:36:39', 10000, '2024-11-12 07:36:39');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (68, 1, '定时任务', 'quartz/schedule/index', NULL, 0, 0, 'icon-reloadtime', 0, 0, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (69, 68, '查看', '', 'schedule:page', 1, 0, '', 0, 0, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (70, 68, '新增', '', 'schedule:save', 1, 0, '', 0, 1, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (71, 68, '修改', '', 'schedule:update,schedule:info', 1, 0, '', 0, 2, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (72, 68, '删除', '', 'schedule:delete', 1, 0, '', 0, 3, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (73, 68, '立即运行', '', 'schedule:run', 1, 0, '', 0, 2, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (74, 68, '日志', '', 'schedule:log', 1, 0, '', 0, 4, 0, 0, 10000, '2024-11-12 07:36:49', 10000, '2024-11-12 07:36:49');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (75, 1, '请假申请', 'train/leave_apply/index', NULL, 0, 0, 'icon-menu', 0, 0, 0, 1, 10000, '2024-11-18 04:02:24', 10000, '2024-11-18 14:05:20');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (77, NULL, '教务中心', NULL, NULL, 0, 0, 'icon-laptop', 0, 1, 0, 0, 10000, '2024-11-18 14:13:50', 10000, '2024-12-12 23:58:10');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (78, 77, '学员管理', 'train/student/index', NULL, 0, 0, 'icon-user', 1, 0, 0, 0, 10000, '2024-11-18 14:16:55', 10000, '2024-12-12 23:49:43');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (79, 77, '课表管理', 'train/timetable/index', NULL, 0, 0, 'icon-time', 0, 1, 0, 0, 10000, '2024-11-18 14:17:40', 10000, '2024-12-12 23:50:19');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (80, 77, '课程管理', 'train/course/index', NULL, 0, 0, 'icon-book', 0, 3, 0, 0, 10000, '2024-11-18 14:18:35', 10000, '2024-12-18 23:15:58');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (81, 77, '班级管理', 'train/class_info/index', NULL, 0, 0, 'icon-deploymentunit', 0, 2, 0, 0, 10000, '2024-11-18 14:19:10', 10000, '2024-12-18 23:15:51');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (82, 77, '老师管理', 'train/teacher/index', NULL, 0, 0, 'icon-crown', 0, 4, 0, 0, 10000, '2024-11-18 14:20:44', 10000, '2024-12-12 23:52:59');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (83, 77, '上课记录', NULL, NULL, 0, 0, NULL, 0, 5, 0, 1, 10000, '2024-11-18 14:21:30', 10000, '2024-12-02 16:45:27');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (84, NULL, '财务中心', NULL, NULL, 0, 0, 'icon-moneycollect', 0, 1, 0, 0, 10000, '2024-11-18 14:23:09', 10000, '2024-12-12 23:54:02');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (85, NULL, '机构设置', NULL, NULL, 0, 0, NULL, 0, 3, 0, 1, 10000, '2024-11-18 14:23:27', 10000, '2024-12-13 00:00:05');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (86, NULL, '数据中心', NULL, NULL, 0, 0, 'icon-Report', 0, 2, 0, 0, 10000, '2024-11-18 14:30:11', 10000, '2024-12-12 23:59:51');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (87, NULL, '业务中心', NULL, NULL, 0, 0, 'icon-build', 0, 0, 0, 0, 10000, '2024-11-18 15:04:18', 10000, '2024-12-12 23:59:24');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (88, 87, '学员档案', NULL, NULL, 0, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-18 15:04:44', 10000, '2024-11-18 15:04:44');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (89, 87, '跟进记录', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, 10000, '2024-11-18 15:04:57', 10000, '2024-11-18 15:04:57');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (90, 87, '试听记录', NULL, NULL, 0, 0, NULL, 0, 2, 0, 0, 10000, '2024-11-18 15:05:08', 10000, '2024-11-18 15:05:08');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (91, 87, '预警提醒', NULL, NULL, 0, 0, NULL, 0, 3, 0, 0, 10000, '2024-11-18 15:05:23', 10000, '2024-11-18 15:05:23');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (92, 84, '订单管理', 'train/order/index', 'train:order:page,train:order:save,train:order:update,train:order:info', 0, 0, 'icon-shopping', 0, 0, 0, 0, 10000, '2024-11-18 15:09:50', 10000, '2024-12-12 23:57:09');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (93, 84, '收支明细', NULL, NULL, 0, 0, NULL, 0, 0, 0, 1, 10000, '2024-11-18 15:10:10', 10000, '2024-12-07 23:41:09');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (94, 84, '课消记录', 'train/course_hour_deplete_log/index', NULL, 0, 0, 'icon-item', 0, 0, 0, 0, 10000, '2024-11-18 15:10:30', 10000, '2024-12-12 23:55:58');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (95, 84, '工资结算', 'train/teacher_settlement/index', 'train:teacher_settlement:page,train:teacher_settlement:info,train:teacher_settlement:save,train:teacher_settlement:update,hasAuthority(\'train:teacher_settlement:delete', 0, 0, 'icon-wallet', 0, 3, 0, 0, 10000, '2024-11-18 15:10:47', 10000, '2024-12-12 23:56:10');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (96, 86, '经营汇总', NULL, NULL, 0, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-18 15:11:43', 10000, '2024-11-18 15:11:43');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (97, 86, '招生数据', NULL, NULL, 0, 0, NULL, 0, 1, 0, 0, 10000, '2024-11-18 15:11:58', 10000, '2024-11-18 15:11:58');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (98, 86, '教务数据', NULL, NULL, 0, 0, NULL, 0, 2, 0, 0, 10000, '2024-11-18 15:12:10', 10000, '2024-11-18 15:12:10');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (99, 86, '财务数据', NULL, NULL, 0, 0, NULL, 0, 3, 0, 0, 10000, '2024-11-18 15:12:21', 10000, '2024-11-18 15:12:21');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (100, 78, '新增修改', NULL, 'train:student:info,train:student:save,train:student:update', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-18 15:46:57', 10000, '2024-11-19 22:18:31');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (101, 78, '删除', NULL, 'train:student:delete', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-18 15:47:22', 10000, '2024-11-18 15:47:22');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (102, 78, '查看', NULL, 'train:student:page', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-18 15:49:10', 10000, '2024-11-18 15:49:10');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (103, 77, '教室管理', 'train/classroom/index', NULL, 0, 0, 'icon-grid', 0, 6, 0, 0, 10000, '2024-11-20 21:24:43', 10000, '2024-12-12 23:53:35');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (104, 82, '查看', NULL, 'train:teacher:page', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-20 21:26:15', 10000, '2024-11-20 21:26:15');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (105, 82, '新增修改', NULL, 'train:teacher:save,train:teacher:update,train:teacher:info', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-20 21:26:34', 10000, '2024-11-20 21:27:19');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (106, 82, '删除', NULL, 'train:teacher:delete', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-20 21:27:37', 10000, '2024-11-20 21:27:37');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (107, 80, '查看', NULL, 'train:course:page', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-21 15:01:15', 10000, '2024-11-21 15:01:15');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (108, 80, '新增修改', NULL, 'train:course:save,train:course:update,train:course:info', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-21 15:01:49', 10000, '2024-11-21 15:01:49');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (109, 80, '删除', NULL, 'train:course:delete', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-21 15:02:15', 10000, '2024-11-21 15:02:15');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (110, 81, '查看', NULL, 'train:class_info:page', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-22 16:13:20', 10000, '2024-11-22 16:13:20');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (111, 81, '新增修改', NULL, 'train:class_info:save,train:class_info:update,train:class_info:info', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-22 16:14:09', 10000, '2024-11-22 16:14:09');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (112, 81, '删除', NULL, 'train:class_info:delete', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-22 16:14:36', 10000, '2024-11-22 16:14:36');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (113, 103, '查看', NULL, 'train:classroom:page', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-22 16:16:11', 10000, '2024-11-22 16:16:11');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (114, 103, '新增修改', NULL, 'train:classroom:save,train:classroom:update,train:classroom:info', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-22 16:16:39', 10000, '2024-11-22 16:16:39');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (115, 103, '删除', NULL, 'train:classroom:delete', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-22 16:17:10', 10000, '2024-11-22 16:17:10');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (116, 77, '上课管理', 'train/lesson/index', NULL, 0, 0, 'icon-insertrowbelow', 1, 1, 0, 0, 10000, '2024-11-29 21:35:54', 10000, '2024-12-12 23:51:06');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (117, 116, '查看', NULL, 'train:lesson_timetable:page', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-29 21:37:19', 10000, '2024-11-29 21:37:19');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (118, 116, '新增修改', NULL, 'train:lesson_timetable:save,train:lesson_timetable:update,train:lesson_timetable:info', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-29 21:37:55', 10000, '2024-11-29 21:37:55');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (119, 116, '删除', NULL, 'train:lesson_timetable:delete', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-11-29 21:38:18', 10000, '2024-11-29 21:38:18');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (120, 94, '查看', NULL, 'train:course_hour_deplete_log:page', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-12-02 16:51:05', 10000, '2024-12-02 16:51:05');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (121, 94, '新增修改', NULL, 'train:course_hour_deplete_log:save,train:course_hour_deplete_log:update,train:course_hour_deplete_log:info', 0, 0, NULL, 0, 0, 0, 1, 10000, '2024-12-02 16:51:36', 10000, '2024-12-02 16:52:32');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (122, 94, '删除', NULL, 'train:course_hour_deplete_log:delete', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-12-02 16:51:53', 10000, '2024-12-02 16:51:53');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `authority`, `type`, `open_style`, `icon`, `has_data_permission`, `sort`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (123, 94, '新增修改', NULL, 'train:course_hour_deplete_log:save,train:course_hour_deplete_log:update,train:course_hour_deplete_log:info', 1, 0, NULL, 0, 0, 0, 0, 10000, '2024-12-02 16:52:44', 10000, '2024-12-02 16:52:44');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_data_permission`;
CREATE TABLE `sys_menu_data_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `component` varchar(200) NOT NULL COMMENT '关联组件名',
  `rule_name` varchar(200) DEFAULT NULL COMMENT '规则名称',
  `rule_type` varchar(200) DEFAULT NULL COMMENT '规则类型',
  `rule_flag` varchar(200) DEFAULT NULL COMMENT '规则标识',
  `rule_value` varchar(1000) DEFAULT NULL COMMENT '规则内容',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_component` (`component`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='数据权限';

-- ----------------------------
-- Records of sys_menu_data_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu_data_permission` (`id`, `component`, `rule_name`, `rule_type`, `rule_flag`, `rule_value`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 'train/lesson/index', '测试用数据权限', 'sql_rule', 'abce_flag_123kkkkls', 'a.fkkd = fdsl and bfreldf in {USER_ID} jklfa.fkkd = fdsl and bfreldf in {USER_ID} jkl', NULL, 0, NULL, NULL, 10000, '2024-12-08 16:05:05');
INSERT INTO `sys_menu_data_permission` (`id`, `component`, `rule_name`, `rule_type`, `rule_flag`, `rule_value`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 'train/lesson/index', '放款多少', 'sql_rule', '324235', 'fsdfsfdsfdsfdsfdsfdsfsdfd', NULL, 1, NULL, NULL, 10000, '2024-12-17 21:58:16');
INSERT INTO `sys_menu_data_permission` (`id`, `component`, `rule_name`, `rule_type`, `rule_flag`, `rule_value`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 'train/lesson/index', '放款多少', 'sql_rule', '324235', 'fsdfsfdsfdsfdsfdsfdsfsdfdsf', NULL, 1, NULL, NULL, 10000, '2024-12-17 21:58:11');
INSERT INTO `sys_menu_data_permission` (`id`, `component`, `rule_name`, `rule_type`, `rule_flag`, `rule_value`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 'train/lesson/index', '测试用数据规则名称', 'sql_rule', 'this', 'fdslkjfdsfdsfdsfdsfdsfds', 3, 1, 10000, '2024-12-08 16:02:26', 10000, '2024-12-08 16:04:08');
INSERT INTO `sys_menu_data_permission` (`id`, `component`, `rule_name`, `rule_type`, `rule_flag`, `rule_value`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 'train/lesson/index', 'q', 'sql_rule', 'q', 'q', 0, 1, 10000, '2024-12-08 16:08:51', 10000, '2024-12-17 21:58:08');
INSERT INTO `sys_menu_data_permission` (`id`, `component`, `rule_name`, `rule_type`, `rule_flag`, `rule_value`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (6, 'train/lesson/index', '3', 'sql_rule', '3', '3', 0, 1, 10000, '2024-12-08 16:11:20', 10000, '2024-12-17 21:58:05');
INSERT INTO `sys_menu_data_permission` (`id`, `component`, `rule_name`, `rule_type`, `rule_flag`, `rule_value`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 'train/student/index', '学员测试数据', 'sql_rule', '1', '1 = 2 or 3 = 4', 2, 0, 10000, '2024-12-08 20:48:15', 10000, '2024-12-08 22:46:55');
INSERT INTO `sys_menu_data_permission` (`id`, `component`, `rule_name`, `rule_type`, `rule_flag`, `rule_value`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (8, 'train/student/index', '学员测试2', 'sql_rule', '2', '222', 0, 0, 10000, '2024-12-08 20:48:25', 10000, '2024-12-08 20:48:25');
INSERT INTO `sys_menu_data_permission` (`id`, `component`, `rule_name`, `rule_type`, `rule_flag`, `rule_value`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (9, 'train/lesson/index', '只看自己的课', 'sql_rule', 'timetable', 'teacher_code = \'{USERNAME}\'', 0, 0, 10000, '2024-12-17 22:00:01', 10000, '2024-12-17 22:00:01');
COMMIT;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级ID',
  `name` varchar(50) DEFAULT NULL COMMENT '机构名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `leader_id` bigint(20) DEFAULT NULL COMMENT '负责人ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='机构管理';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
BEGIN;
INSERT INTO `sys_org` (`id`, `pid`, `name`, `sort`, `leader_id`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, NULL, '测试培训机构', 0, NULL, NULL, 0, 0, 10000, '2024-11-18 14:31:39', 10000, '2024-11-18 14:31:39');
INSERT INTO `sys_org` (`id`, `pid`, `name`, `sort`, `leader_id`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, NULL, '测试机构2', 0, NULL, NULL, 0, 0, 10000, '2024-12-08 17:25:28', 10000, '2024-12-08 17:25:28');
INSERT INTO `sys_org` (`id`, `pid`, `name`, `sort`, `leader_id`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 1, '分之一', 0, NULL, NULL, 0, 0, 10000, '2024-12-08 17:26:10', 10000, '2024-12-08 17:26:10');
COMMIT;

-- ----------------------------
-- Table structure for sys_params
-- ----------------------------
DROP TABLE IF EXISTS `sys_params`;
CREATE TABLE `sys_params` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `param_name` varchar(100) DEFAULT NULL COMMENT '参数名称',
  `param_type` tinyint(4) NOT NULL COMMENT '系统参数   0：否   1：是',
  `param_key` varchar(100) DEFAULT NULL COMMENT '参数键',
  `param_value` varchar(2000) DEFAULT NULL COMMENT '参数值',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='参数管理';

-- ----------------------------
-- Records of sys_params
-- ----------------------------
BEGIN;
INSERT INTO `sys_params` (`id`, `param_name`, `param_type`, `param_key`, `param_value`, `remark`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, '用户登录-验证码开关', 1, 'LOGIN_CAPTCHA', 'false', '是否开启验证码（true：开启，false：关闭）', 10000, 0, 0, 10000, '2024-11-12 06:56:56', 10000, '2024-11-12 06:56:56');
INSERT INTO `sys_params` (`id`, `param_name`, `param_type`, `param_key`, `param_value`, `remark`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, '节假日', 0, 'holiday', '2024-12-14,2024-12-15', NULL, NULL, 0, 0, 10000, '2024-12-15 10:28:55', 10000, '2024-12-15 10:28:55');
INSERT INTO `sys_params` (`id`, `param_name`, `param_type`, `param_key`, `param_value`, `remark`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, '学员端H5', 1, 'student_h5', 'http://127.0.0.1/s5/', NULL, NULL, 2, 0, 10000, '2024-12-16 18:04:52', 10000, '2024-12-18 10:41:17');
COMMIT;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `post_code` varchar(100) DEFAULT NULL COMMENT '岗位编码',
  `post_name` varchar(100) DEFAULT NULL COMMENT '岗位名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='岗位管理';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_post` (`id`, `post_code`, `post_name`, `sort`, `status`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 'gg', '测试岗', 0, 1, NULL, 0, 0, 10000, '2024-12-11 11:45:17', 10000, '2024-12-11 11:45:17');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `data_scope` tinyint(4) DEFAULT NULL COMMENT '数据范围  0：全部数据  1：本机构及子机构数据  2：本机构数据  3：本人数据  4：自定义数据',
  `org_id` bigint(20) DEFAULT NULL COMMENT '机构ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_org_id` (`org_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='角色管理';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `name`, `role_code`, `remark`, `data_scope`, `org_id`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, '老师', 'teacher', NULL, 3, NULL, NULL, 0, 0, 10000, '2024-11-20 22:34:54', 10000, '2024-12-17 22:14:43');
INSERT INTO `sys_role` (`id`, `name`, `role_code`, `remark`, `data_scope`, `org_id`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, '校长', 'schoolmaster', NULL, NULL, NULL, NULL, 0, 0, 10000, '2024-12-08 17:19:22', 10000, '2024-12-08 17:19:22');
INSERT INTO `sys_role` (`id`, `name`, `role_code`, `remark`, `data_scope`, `org_id`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, '管理员', 'admin', NULL, NULL, NULL, NULL, 0, 0, 10000, '2024-12-08 22:22:20', 10000, '2024-12-08 22:22:55');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_permission`;
CREATE TABLE `sys_role_data_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `data_permission_id` bigint(20) DEFAULT NULL COMMENT '数据权限ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='角色数据权限';

-- ----------------------------
-- Records of sys_role_data_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_data_permission` (`id`, `role_id`, `data_permission_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 3, 7, 0, 0, 10000, '2024-12-08 22:22:55', 10000, '2024-12-08 22:22:55');
INSERT INTO `sys_role_data_permission` (`id`, `role_id`, `data_permission_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (8, 1, 9, 0, 0, 10000, '2024-12-17 22:14:43', 10000, '2024-12-17 22:14:43');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_scope`;
CREATE TABLE `sys_role_data_scope` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `org_id` bigint(20) DEFAULT NULL COMMENT '机构ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色数据权限';

-- ----------------------------
-- Records of sys_role_data_scope
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 1, 77, 0, 0, 10000, '2024-12-08 21:03:01', 10000, '2024-12-08 21:03:01');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 1, 78, 0, 1, 10000, '2024-12-08 21:03:01', 10000, '2024-12-08 21:03:01');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 1, 100, 0, 1, 10000, '2024-12-08 21:03:01', 10000, '2024-12-08 21:03:01');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 1, 101, 0, 1, 10000, '2024-12-08 21:03:01', 10000, '2024-12-08 21:03:01');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 1, 102, 0, 1, 10000, '2024-12-08 21:03:01', 10000, '2024-12-08 21:03:01');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (6, 1, 80, 0, 1, 10000, '2024-12-08 22:20:59', 10000, '2024-12-08 22:20:59');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 1, 107, 0, 1, 10000, '2024-12-08 22:20:59', 10000, '2024-12-08 22:20:59');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (8, 1, 108, 0, 1, 10000, '2024-12-08 22:20:59', 10000, '2024-12-08 22:20:59');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (9, 1, 109, 0, 1, 10000, '2024-12-08 22:20:59', 10000, '2024-12-08 22:20:59');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10, 3, 77, 0, 0, 10000, '2024-12-08 22:22:55', 10000, '2024-12-08 22:22:55');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (11, 3, 78, 0, 0, 10000, '2024-12-08 22:22:55', 10000, '2024-12-08 22:22:55');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (12, 3, 100, 0, 0, 10000, '2024-12-08 22:22:55', 10000, '2024-12-08 22:22:55');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (13, 3, 101, 0, 0, 10000, '2024-12-08 22:22:55', 10000, '2024-12-08 22:22:55');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (14, 3, 102, 0, 0, 10000, '2024-12-08 22:22:55', 10000, '2024-12-08 22:22:55');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (15, 1, 116, 0, 0, 10000, '2024-12-17 22:14:43', 10000, '2024-12-17 22:14:43');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (16, 1, 117, 0, 0, 10000, '2024-12-17 22:14:43', 10000, '2024-12-17 22:14:43');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (17, 1, 118, 0, 0, 10000, '2024-12-17 22:14:43', 10000, '2024-12-17 22:14:43');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (18, 1, 119, 0, 0, 10000, '2024-12-17 22:14:43', 10000, '2024-12-17 22:14:43');
COMMIT;

-- ----------------------------
-- Table structure for sys_sms_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_config`;
CREATE TABLE `sys_sms_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `platform` tinyint(4) DEFAULT NULL COMMENT '平台类型  0：阿里云   1：腾讯云   2：七牛云   3：华为云',
  `group_name` varchar(100) DEFAULT NULL COMMENT '分组名称，发送短信时，可指定分组',
  `sign_name` varchar(100) DEFAULT NULL COMMENT '短信签名',
  `template_id` varchar(100) DEFAULT NULL COMMENT '短信模板',
  `app_id` varchar(100) DEFAULT NULL COMMENT '短信应用ID，如：腾讯云等',
  `sender_id` varchar(100) DEFAULT NULL COMMENT '腾讯云国际短信、华为云等需要',
  `url` varchar(200) DEFAULT NULL COMMENT '接入地址，如：华为云',
  `access_key` varchar(100) DEFAULT NULL COMMENT 'AccessKey',
  `secret_key` varchar(100) DEFAULT NULL COMMENT 'SecretKey',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：启用',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信配置';

-- ----------------------------
-- Records of sys_sms_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_log`;
CREATE TABLE `sys_sms_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `platform_id` bigint(20) DEFAULT NULL COMMENT '平台ID',
  `platform` tinyint(4) DEFAULT NULL COMMENT '平台类型',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `params` varchar(200) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：失败   1：成功',
  `error` varchar(2000) DEFAULT NULL COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信日志';

-- ----------------------------
-- Records of sys_sms_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_third_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_third_login`;
CREATE TABLE `sys_third_login` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `open_type` varchar(50) DEFAULT NULL COMMENT '开放平台类型',
  `open_id` varchar(100) DEFAULT NULL COMMENT '开放平台，唯一标识',
  `username` varchar(100) DEFAULT NULL COMMENT '昵称',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方登录';

-- ----------------------------
-- Records of sys_third_login
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_third_login_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_third_login_config`;
CREATE TABLE `sys_third_login_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `open_type` varchar(50) DEFAULT NULL COMMENT '开放平台类型',
  `client_id` varchar(200) DEFAULT NULL COMMENT 'ClientID',
  `client_secret` varchar(200) DEFAULT NULL COMMENT 'ClientSecret',
  `redirect_uri` varchar(200) DEFAULT NULL COMMENT 'RedirectUri',
  `agent_id` varchar(200) DEFAULT NULL COMMENT 'AgentID',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='第三方登录配置';

-- ----------------------------
-- Records of sys_third_login_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_third_login_config` (`id`, `open_type`, `client_id`, `client_secret`, `redirect_uri`, `agent_id`, `tenant_id`, `version`, `deleted`, `create_time`) VALUES (1, 'feishu', 'cli_a541d3aa03f8500b', '5Chz39zvEhZtxSVZz3vLjfQHdkvavQaH', 'http://localhost:8080/sys/third/callback/feishu', '', 10000, 0, 1, '2024-11-12 06:56:56');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别   0：男   1：女   2：未知',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `org_id` bigint(20) DEFAULT NULL COMMENT '机构ID',
  `super_admin` tinyint(4) DEFAULT NULL COMMENT '超级管理员   0：否   1：是',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10009 DEFAULT CHARSET=utf8mb4 COMMENT='用户管理';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `org_id`, `super_admin`, `status`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10000, 'admin', '667c756cf9334e328a56e44e906245c8e214c655a160f18fdb84d79c209c49cf', 'admin', 'http://127.0.0.1/api/upload/20241218/a_41753.png', 0, 'a@a.com', '13612345678', NULL, 1, 1, 10000, 3, 0, 10000, '2024-11-12 06:56:53', 10000, '2024-12-26 00:07:00');
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `org_id`, `super_admin`, `status`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10001, 'u1', '6e0f9e14344c5406a0cf5a3b4dfb665f87f4a771a31f7edbb5c72874a32b2957', '用户一', NULL, 0, NULL, '18812312301', 1, 0, 1, NULL, 0, 1, 10000, '2024-11-19 22:49:16', 10000, '2024-12-24 23:24:22');
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `org_id`, `super_admin`, `status`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10002, 'zhanglaoshi', '207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb', '张老师', NULL, 0, NULL, '18812312302', 1, 0, 1, NULL, 0, 1, 10000, '2024-11-20 23:57:08', 10000, '2024-12-24 23:24:15');
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `org_id`, `super_admin`, `status`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10003, 'lilaoshi', 'c68ac63173fcfc537bf22f19a425977029d7dd35ddc5d76b36e58af222dfda39', '李老师', NULL, 1, NULL, '18812312303', 1, 0, 1, NULL, 0, 0, 10000, '2024-11-21 16:19:59', 10000, '2024-12-17 22:14:09');
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `org_id`, `super_admin`, `status`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10004, 'qinza', '207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb', '勤杂', NULL, 0, NULL, 'admin', 1, 0, 1, NULL, 0, 0, 10000, '2024-12-02 15:28:14', 10000, '2024-12-02 15:28:14');
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `org_id`, `super_admin`, `status`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10005, 'liceshi', 'c68ac63173fcfc537bf22f19a425977029d7dd35ddc5d76b36e58af222dfda39', '李测试老师', NULL, 1, NULL, '18712312300', 1, 0, 1, NULL, 0, 0, 10000, '2024-12-15 10:25:44', 10000, '2024-12-15 10:27:06');
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `org_id`, `super_admin`, `status`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10006, 'wanglaoshi', 'c68ac63173fcfc537bf22f19a425977029d7dd35ddc5d76b36e58af222dfda39', '张老师', NULL, 0, NULL, '18912312301', 2, 0, 1, NULL, 0, 0, 10000, '2024-12-18 22:55:16', 10000, '2024-12-18 22:55:16');
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `org_id`, `super_admin`, `status`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10007, '张三', '680aceb47997b6db7498588cfa49a392ce7dcce05ec39cd68fb930d93d3025f5', '张三', NULL, 0, NULL, '11111111111', 1, 0, 1, NULL, 0, 1, 10000, '2024-12-24 19:16:06', 10000, '2024-12-24 23:24:26');
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `org_id`, `super_admin`, `status`, `tenant_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10008, 'liyang', '2302b7dece4c24aa63441410a52f956a74426a08348dfc744a7cad78c45486be', '李杨', NULL, 0, NULL, '123123123', 1, 0, 1, NULL, 0, 0, 10000, '2024-12-24 23:24:05', 10000, '2024-12-24 23:24:05');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `post_id` bigint(20) DEFAULT NULL COMMENT '岗位ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_post_id` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户岗位关系';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `role_id`, `user_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 3, 10000, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` (`id`, `role_id`, `user_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 1, 10005, 0, 0, 10000, '2024-12-15 10:27:06', 10000, '2024-12-15 10:27:06');
INSERT INTO `sys_user_role` (`id`, `role_id`, `user_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 1, 10003, 0, 0, 10000, '2024-12-17 22:14:09', 10000, '2024-12-17 22:14:09');
INSERT INTO `sys_user_role` (`id`, `role_id`, `user_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 1, 10007, 0, 1, 10000, '2024-12-24 19:16:06', 10000, '2024-12-24 19:16:06');
INSERT INTO `sys_user_role` (`id`, `role_id`, `user_id`, `version`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 1, 10008, 0, 0, 10000, '2024-12-24 23:24:05', 10000, '2024-12-24 23:24:05');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `access_token` varchar(32) DEFAULT NULL COMMENT 'accessToken',
  `access_token_expire` datetime DEFAULT NULL COMMENT 'accessToken 过期时间',
  `refresh_token` varchar(32) DEFAULT NULL COMMENT 'refreshToken',
  `refresh_token_expire` datetime DEFAULT NULL COMMENT 'refreshToken 过期时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_class_course
-- ----------------------------
DROP TABLE IF EXISTS `tt_class_course`;
CREATE TABLE `tt_class_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `class_code` varchar(50) NOT NULL COMMENT '班级编码',
  `course_code` varchar(50) NOT NULL COMMENT '课程编码',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '老师编码',
  `classroom_code` varchar(50) DEFAULT NULL COMMENT '教室编码',
  `plan_hours` int(10) DEFAULT NULL COMMENT '计划课时数',
  `course_hour_once` int(10) DEFAULT NULL COMMENT '每次上课课时',
  `completed_hours` int(10) DEFAULT '0' COMMENT '已授课时',
  `completed_times` int(10) DEFAULT '0' COMMENT '已上课次数',
  `schedule_hours` int(10) DEFAULT '0' COMMENT '已排课学时',
  `schedule_rule` varchar(1000) DEFAULT NULL COMMENT '排课规则',
  `schedule_status` varchar(50) DEFAULT NULL COMMENT '排课状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_course_code` (`course_code`) USING BTREE,
  KEY `idx_class_code` (`class_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='班级课程';

-- ----------------------------
-- Records of tt_class_course
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_class_info
-- ----------------------------
DROP TABLE IF EXISTS `tt_class_info`;
CREATE TABLE `tt_class_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `class_code` varchar(50) DEFAULT NULL COMMENT '班级编码',
  `class_name` varchar(100) NOT NULL COMMENT '班级名称',
  `class_type` varchar(50) DEFAULT NULL COMMENT '班级类型一对一or一对多',
  `capacity` int(10) DEFAULT NULL COMMENT '班级容量',
  `course_code` varchar(50) DEFAULT NULL COMMENT '关联课程',
  `manager_teacher` varchar(50) DEFAULT NULL COMMENT '管理老师',
  `class_status` varchar(50) DEFAULT NULL COMMENT '班级状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_class_code` (`class_code`) USING BTREE,
  KEY `idx_course_code` (`course_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='班级';

-- ----------------------------
-- Records of tt_class_info
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_class_student
-- ----------------------------
DROP TABLE IF EXISTS `tt_class_student`;
CREATE TABLE `tt_class_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `class_code` varchar(50) DEFAULT NULL COMMENT '班级编码',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `enter_time` datetime DEFAULT NULL COMMENT '入班时间',
  `status` varchar(50) DEFAULT NULL COMMENT '状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`) USING BTREE,
  KEY `idx_class_code` (`class_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='班级学员';

-- ----------------------------
-- Records of tt_class_student
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_classroom
-- ----------------------------
DROP TABLE IF EXISTS `tt_classroom`;
CREATE TABLE `tt_classroom` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `classroom_code` varchar(50) NOT NULL COMMENT '教室编码',
  `classroom_name` varchar(100) DEFAULT NULL COMMENT '教室名称',
  `capacity` int(10) DEFAULT NULL COMMENT '教室容量',
  `address` varchar(500) DEFAULT NULL COMMENT '教室地址',
  `ignore_conflict` tinyint(4) DEFAULT '0' COMMENT '忽略排课冲突',
  `classroom_status` varchar(50) DEFAULT NULL COMMENT '教室状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_classroom_code` (`classroom_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='教室';

-- ----------------------------
-- Records of tt_classroom
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_course
-- ----------------------------
DROP TABLE IF EXISTS `tt_course`;
CREATE TABLE `tt_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `course_code` varchar(50) NOT NULL COMMENT '课程编码',
  `course_name` varchar(100) NOT NULL COMMENT '课程名称',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '任课老师',
  `course_hour_total` int(10) DEFAULT '2' COMMENT '总课时数',
  `course_hour_once` int(10) DEFAULT '2' COMMENT '每次课课时',
  `amount_one_hour` decimal(10,2) DEFAULT '100.00' COMMENT '每课时收费',
  `course_desc` varchar(1000) DEFAULT NULL COMMENT '课程简介',
  `course_status` varchar(50) DEFAULT 'new' COMMENT '课程状态new/normal/close',
  `class_type` varchar(50) DEFAULT NULL COMMENT '关联的班级人数类型一对一一对多',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_course_code` (`course_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='课程';

-- ----------------------------
-- Records of tt_course
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_course_audition
-- ----------------------------
DROP TABLE IF EXISTS `tt_course_audition`;
CREATE TABLE `tt_course_audition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `lesson_id` bigint(20) NOT NULL COMMENT '课程计划ID',
  `course_code` varchar(50) DEFAULT NULL COMMENT '课程编码',
  `class_code` varchar(50) DEFAULT NULL COMMENT '班级编码',
  `classroom_code` varchar(50) DEFAULT NULL COMMENT '教室编码',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '老师编码',
  `audition_status` varchar(50) DEFAULT NULL COMMENT '试听状态',
  `audition_feedback` varchar(500) DEFAULT NULL COMMENT '试听反馈',
  `approval_status` varchar(50) DEFAULT NULL COMMENT '审批状态',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`) USING BTREE,
  KEY `idx_lesson_id` (`lesson_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程试听';

-- ----------------------------
-- Records of tt_course_audition
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tt_course_enroll
-- ----------------------------
DROP TABLE IF EXISTS `tt_course_enroll`;
CREATE TABLE `tt_course_enroll` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `course_code` varchar(50) DEFAULT NULL COMMENT '课程编码',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '老师编码',
  `enroll_status` varchar(50) DEFAULT NULL COMMENT '报名状态',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`) USING BTREE,
  KEY `idx_course_code` (`course_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='课程报名';

-- ----------------------------
-- Records of tt_course_enroll
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_course_hour_buy_log
-- ----------------------------
DROP TABLE IF EXISTS `tt_course_hour_buy_log`;
CREATE TABLE `tt_course_hour_buy_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `course_code` varchar(50) DEFAULT NULL COMMENT '课程编码',
  `amount_one_hour` decimal(10,2) DEFAULT NULL COMMENT '课时单价',
  `course_hour_buy` int(10) DEFAULT NULL COMMENT '购买课时',
  `order_amount` decimal(10,2) DEFAULT NULL COMMENT '订单金额',
  `course_hour_before` int(10) DEFAULT NULL COMMENT '购买前课时',
  `course_hour_after` int(10) DEFAULT NULL COMMENT '购买后课时',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`,`order_id`) USING BTREE,
  KEY `idx_course_code` (`course_code`) USING BTREE,
  KEY `idx_student_id` (`student_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='课时购买记录';

-- ----------------------------
-- Records of tt_course_hour_buy_log
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_course_hour_deplete_log
-- ----------------------------
DROP TABLE IF EXISTS `tt_course_hour_deplete_log`;
CREATE TABLE `tt_course_hour_deplete_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `lesson_id` bigint(20) NOT NULL COMMENT '课程ID',
  `class_course_id` varchar(50) DEFAULT NULL COMMENT '班级课程ID',
  `class_code` varchar(50) DEFAULT NULL COMMENT '班级编码',
  `course_code` varchar(50) DEFAULT NULL COMMENT '课程编码',
  `classroom_code` varchar(50) DEFAULT NULL COMMENT '教室编码',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '任课老师',
  `lesson_date` date DEFAULT NULL COMMENT '课程日期',
  `lesson_begin_time` datetime DEFAULT NULL COMMENT '课程开始时间',
  `lesson_end_time` datetime DEFAULT NULL COMMENT '课程结束时间',
  `arrival_status` varchar(50) NOT NULL COMMENT '到课状态',
  `course_hour` int(10) DEFAULT NULL COMMENT '扣课时数',
  `deplete_time` datetime DEFAULT NULL COMMENT '消课时间',
  `course_hour_before` int(10) DEFAULT NULL COMMENT '消课前课时',
  `course_hour_after` int(10) DEFAULT NULL COMMENT '消课后课时',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_course_code` (`course_code`) USING BTREE,
  KEY `idx_student_id` (`student_id`) USING BTREE,
  KEY `idx_teacher` (`teacher_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COMMENT='消课记录';

-- ----------------------------
-- Records of tt_course_hour_deplete_log
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_leave_apply
-- ----------------------------
DROP TABLE IF EXISTS `tt_leave_apply`;
CREATE TABLE `tt_leave_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `apply_code` varchar(50) DEFAULT NULL COMMENT '申请编码',
  `leave_type` varchar(36) NOT NULL COMMENT '请假类型',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '老师编码',
  `lesson_id` varchar(50) DEFAULT NULL COMMENT '课程ID',
  `apply_reason` varchar(500) DEFAULT NULL COMMENT '申请原因',
  `approval_status` varchar(50) DEFAULT NULL COMMENT '审批状态',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_apply_code` (`apply_code`) USING BTREE,
  KEY `idx_lesson_id` (`lesson_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='请假申请';

-- ----------------------------
-- Records of tt_leave_apply
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tt_lesson_student
-- ----------------------------
DROP TABLE IF EXISTS `tt_lesson_student`;
CREATE TABLE `tt_lesson_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lesson_id` bigint(20) NOT NULL COMMENT '课程ID',
  `class_course_id` bigint(20) NOT NULL COMMENT '班级课程ID',
  `class_code` varchar(50) DEFAULT NULL COMMENT '班级编码',
  `course_code` varchar(50) DEFAULT NULL COMMENT '课程编码',
  `classroom_code` varchar(50) DEFAULT NULL COMMENT '教室编码',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `member_type` varchar(50) NOT NULL COMMENT '成员类型',
  `arrival_status` varchar(50) NOT NULL COMMENT '到课状态',
  `deplete_time` datetime DEFAULT NULL COMMENT '消课时间',
  `course_hour` int(10) DEFAULT NULL COMMENT '扣课时数',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`,`class_course_id`) USING BTREE,
  KEY `idx_lesson_id` (`lesson_id`) USING BTREE,
  KEY `idx_student_id` (`student_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COMMENT='课程计划学员';

-- ----------------------------
-- Records of tt_lesson_student
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_lesson_timetable
-- ----------------------------
DROP TABLE IF EXISTS `tt_lesson_timetable`;
CREATE TABLE `tt_lesson_timetable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `class_course_id` varchar(50) DEFAULT NULL COMMENT '班级课程ID',
  `class_code` varchar(50) DEFAULT NULL COMMENT '班级编码',
  `course_code` varchar(50) DEFAULT NULL COMMENT '课程编码',
  `classroom_code` varchar(50) DEFAULT NULL COMMENT '教室编码',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '任课老师',
  `lesson_date` date NOT NULL COMMENT '课程日期',
  `lesson_begin_time` datetime DEFAULT NULL COMMENT '课程开始时间',
  `lesson_end_time` datetime DEFAULT NULL COMMENT '课程结束时间',
  `course_hour` int(10) DEFAULT NULL COMMENT '扣课时数',
  `lesson_status` varchar(50) DEFAULT NULL COMMENT '课程状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_course_code` (`course_code`) USING BTREE,
  KEY `idx_class_code` (`class_code`) USING BTREE,
  KEY `idx_classroom_code` (`classroom_code`) USING BTREE,
  KEY `idx_teacher` (`teacher_code`) USING BTREE,
  KEY `idx_date` (`lesson_date`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8mb4 COMMENT='课程计划';

-- ----------------------------
-- Records of tt_lesson_timetable
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_order
-- ----------------------------
DROP TABLE IF EXISTS `tt_order`;
CREATE TABLE `tt_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `should_pay` decimal(10,2) DEFAULT NULL COMMENT '需要支付金额',
  `paid` decimal(10,2) DEFAULT NULL COMMENT '已支付金额',
  `paid_time` datetime DEFAULT NULL COMMENT '支付时间',
  `service_fee` decimal(10,2) DEFAULT '0.00' COMMENT '支付服务费',
  `actually_received` decimal(10,2) DEFAULT NULL COMMENT '实际收到金额',
  `total_price` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '减免金额',
  `course_detail` text COMMENT '订单课程明细',
  `discount_reason` varchar(255) DEFAULT NULL COMMENT '减免原因',
  `order_status` varchar(20) DEFAULT NULL COMMENT '订单状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_student_id` (`student_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='订单';

-- ----------------------------
-- Records of tt_order
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_reassign_apply
-- ----------------------------
DROP TABLE IF EXISTS `tt_reassign_apply`;
CREATE TABLE `tt_reassign_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `apply_code` varchar(50) DEFAULT NULL COMMENT '申请编码',
  `reassign_type` varchar(36) NOT NULL COMMENT '调课类型',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '老师编码',
  `lesson_id` varchar(50) DEFAULT NULL COMMENT '课程ID',
  `apply_reason` varchar(500) DEFAULT NULL COMMENT '申请原因',
  `approval_status` varchar(50) DEFAULT NULL COMMENT '审批状态',
  `to_lesson_id` varchar(50) DEFAULT NULL COMMENT '变更为课程ID',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_apply_code` (`apply_code`) USING BTREE,
  KEY `idx_lesson_id` (`lesson_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='调课申请';

-- ----------------------------
-- Records of tt_reassign_apply
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tt_student
-- ----------------------------
DROP TABLE IF EXISTS `tt_student`;
CREATE TABLE `tt_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `student_name` varchar(55) NOT NULL COMMENT '姓名',
  `phone` varchar(255) NOT NULL COMMENT '手机号',
  `phone_belong` varchar(255) DEFAULT NULL COMMENT '手机号归属',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `student_type` varchar(255) DEFAULT NULL COMMENT '学员类型',
  `source_type` varchar(255) DEFAULT NULL COMMENT '学员来源类型',
  `referrer` varchar(255) DEFAULT NULL COMMENT '推荐人姓名',
  `school` varchar(255) DEFAULT NULL COMMENT '在读学校名称',
  `grade` varchar(50) DEFAULT NULL COMMENT '在读年级',
  `avatar` varchar(255) DEFAULT NULL COMMENT '学员头像URL或路径',
  `status` varchar(50) DEFAULT 'normal' COMMENT '状态normal/block',
  `password` varchar(255) NOT NULL COMMENT '学员登录密码',
  `alternate_phone` varchar(255) DEFAULT NULL COMMENT '备用手机号',
  `alternate_phone_belong` varchar(255) DEFAULT NULL COMMENT '备用手机号归属',
  `address` varchar(255) DEFAULT NULL COMMENT '家庭地址',
  `id_card` varchar(18) DEFAULT NULL COMMENT '学员身份证号',
  `intention_level` varchar(50) DEFAULT NULL COMMENT '意向级别或程度',
  `followup_status` varchar(50) DEFAULT NULL COMMENT '跟进状态',
  `followup_person` varchar(255) DEFAULT NULL COMMENT '跟进人',
  `academic_advisor` varchar(255) DEFAULT NULL COMMENT '学管师姓名',
  `token` varchar(100) DEFAULT NULL COMMENT '登录验证token',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_phone` (`phone`) USING BTREE,
  KEY `idx_token` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='学员';

-- ----------------------------
-- Records of tt_student
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_student_course_hour
-- ----------------------------
DROP TABLE IF EXISTS `tt_student_course_hour`;
CREATE TABLE `tt_student_course_hour` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `course_code` varchar(50) NOT NULL COMMENT '课程编码',
  `course_hour_deplete` int(10) NOT NULL DEFAULT '0' COMMENT '总消耗课时',
  `course_hour_left` int(10) NOT NULL DEFAULT '0' COMMENT '剩余课时',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_course_code` (`course_code`) USING BTREE,
  KEY `idx_student_id` (`student_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='学员课程课时';

-- ----------------------------
-- Records of tt_student_course_hour
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_student_flowup_log
-- ----------------------------
DROP TABLE IF EXISTS `tt_student_flowup_log`;
CREATE TABLE `tt_student_flowup_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `student_id` bigint(20) NOT NULL COMMENT '学员ID',
  `flowup_person` varchar(50) DEFAULT NULL COMMENT '跟进人',
  `flowup_type` varchar(50) DEFAULT NULL COMMENT '跟进类型',
  `flowup_desc` varchar(500) DEFAULT NULL COMMENT '跟进内容',
  `next_date` date DEFAULT NULL COMMENT '下次跟进日期',
  `file_ids` varchar(255) DEFAULT NULL COMMENT '附件',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='跟进记录';

-- ----------------------------
-- Records of tt_student_flowup_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tt_teacher
-- ----------------------------
DROP TABLE IF EXISTS `tt_teacher`;
CREATE TABLE `tt_teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '登录名',
  `teacher_name` varchar(50) DEFAULT NULL COMMENT '老师名称',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号',
  `job_type` varchar(50) DEFAULT NULL COMMENT '工作类型 兼职全职',
  `certification` varchar(100) DEFAULT NULL COMMENT '资格证',
  `wage_rule` varchar(200) DEFAULT NULL COMMENT '工资方案',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` varchar(50) NOT NULL COMMENT '状态 离职 在职',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_teacher_code` (`teacher_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='教师';

-- ----------------------------
-- Records of tt_teacher
-- ----------------------------
BEGIN;

COMMIT;

-- ----------------------------
-- Table structure for tt_teacher_settlement
-- ----------------------------
DROP TABLE IF EXISTS `tt_teacher_settlement`;
CREATE TABLE `tt_teacher_settlement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `settlement_name` varchar(50) DEFAULT NULL COMMENT '结算名称',
  `settlement_amount` decimal(10,2) DEFAULT NULL COMMENT '结算金额',
  `settlement_date` date DEFAULT NULL COMMENT '结算日期',
  `teacher_code` varchar(50) DEFAULT NULL COMMENT '结算对象',
  `settlement_begin_date` date DEFAULT NULL COMMENT '结算开始日期包含',
  `settlement_end_date` date DEFAULT NULL COMMENT '结算结束日期包含',
  `pay_date` date DEFAULT NULL COMMENT '发薪日',
  `processor` varchar(50) DEFAULT NULL COMMENT '经办人',
  `settlement_detail` varchar(2000) DEFAULT NULL COMMENT '结算详情',
  `settlement_status` varchar(50) DEFAULT NULL COMMENT '结算状态',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`),
  KEY `idx_teacher` (`teacher_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工工资条';

-- ----------------------------
-- Records of tt_teacher_settlement
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
