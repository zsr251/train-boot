package com.train.train.constant;

public interface TrainConstant {
    /**
     * 学生状态：正常
     */
    String STUDENT_STATUS_NORMAL = "normal";
    /**
     * 学生状态：冻结
     */
    String STUDENT_STATUS_BLOCK = "block";
    /**
     * 课程状态：未开始
     */
    String COURSE_STATUS_wait = "wait";
    /**
     * 课程状态：正常
     */
    String COURSE_STATUS_NORMAL = "normal";
    /**
     * 课程状态：已结束
     */
    String COURSE_STATUS_FINISH = "finish";
    /**
     * 报名状态：正常
     */
    String COURSE_ENROLL_STATUS_NORMAL = "normal";
    /**
     * 报名状态：取消
     */
    String COURSE_ENROLL_STATUS_CANCEL = "cancel";
    /**
     * 报名状态：已分班
     */
    String COURSE_ENROLL_STATUS_ASSIGN = "assign";
    /**
     * 班级状态：正常
     */
    String CLASS_STATUS_NORMAL = "normal";
    /**
     * 班级状态：结班
     */
    String CLASS_STATUS_FINISH = "finish";
    /**
     * 班级状态：未开始
     */
    String CLASS_STATUS_WAIT = "wait";
    /**
     * 班级类型： 一对一
     */
    String CLASS_TYPE_ONE_TO_ONE = "1";
    /**
     * 班级类型：一对多
     */
    String CLASS_TYPE_ONE_TO_MANY = "n";
    /**
     * 教室状态：正常
     */
    String CLASSROOM_STATUS_NORMAL = "normal";
    /**
     * 教室状态：已停用
     */
    String CLASSROOM_STATUS_DISABLE = "disable";
    /**
     * 排课状态：等待排课
     */
    String SCHEDULE_STATUS_WAIT = "wait";
    /**
     * 排课状态：进行中
     */
    String SCHEDULE_STATUS_ING = "ing";
    /**
     * 排课状态：已排课
     */
    String SCHEDULE_STATUS_DONE = "done";
    /**
     * 班级学员状态：正常
     */
    String CLASS_STUDENT_STATUS_NORMAL = "normal";
    /**
     * 班级学员状态：已退班
     */
    String CLASS_STUDENT_STATUS_QUIT = "quit";
    /**
     * 课程计划状态：未开始
     */
    String LESSON_STATUS_WAIT = "wait";
/**
     * 课程计划状态：已完成
     */
    String LESSON_STATUS_DONE = "done";
    /**
     * 课程计划状态：已取消
     */
    String LESSON_STATUS_CANCEL = "cancel";
    /**
     * 课程计划状态：已调课
     */
    String LESSON_STATUS_REASSIGN = "reassign";
    /**
     * 排课规则 固定频率
     */
    String SCHEDULE_TYPE_FREQUENCY = "frequency";
    /**
     * 排课规则 指定日期
     */
    String SCHEDULE_TYPE_APPOINT = "appoint";
    /**
     * 排课固定频率类型：每日
     */
    String FREQUENCY_TYPE_DAILY = "daily";
    /**
     * 排课固定频率类型：每周
     */
    String FREQUENCY_TYPE_WEEKLY = "weekly";
    /**
     * 排课固定频率类型：每月
     */
    String FREQUENCY_TYPE_MONTHLY = "monthly";

    /**
     * 课程计划成员类型：试听
     */
    String LESSON_MEMBER_TYPE_AUDITION = "audition";
    /**
     * 课程计划成员类型：正式
     */
    String LESSON_MEMBER_TYPE_STUDENT = "student";

    /**
     * 到课状态：等待上课
     */
    String LESSON_ARRIVAL_STATUS_WAIT ="wait";
    /**
     * 到课状态：已到
     */
    String LESSON_ARRIVAL_STATUS_ARRIVED ="arrived";
    /**
     * 到课状态：迟到
     */
    String LESSON_ARRIVAL_STATUS_DELAY ="delay";
    /**
     * 到课状态：缺席
     */
    String LESSON_ARRIVAL_STATUS_ABSENT ="absent";
    /**
     * 到课状态：请假
     */
    String LESSON_ARRIVAL_STATUS_LEAVE ="leave";
    /**
     * 订单状态：待支付
     */
    String ORDER_STATUS_WAIT = "wait";
    /**
     * 订单状态：已支付
     */
    String ORDER_STATUS_PAID = "paid";
    /**
     * 订单状态：已取消
     */
    String ORDER_STATUS_CANCEL = "cancel";
}
