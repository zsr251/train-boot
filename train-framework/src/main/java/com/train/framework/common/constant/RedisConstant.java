package com.train.framework.common.constant;

/**
 * redis key 常量
 */
public interface RedisConstant {
    /**
     * 重复请求限制
     */
    String LIMIT_REPEAT_ = "limit:repeat:";
    /**
     * 数据权限缓存
     */
    String DATA_PERMISSION_ = "data:permission:";
    /**
     * 字典缓存
     */
    String DICT_ = "dict:";
    /**
     * 字典缓存时间 1小时
     */
    Long DICT_EXPIRE = 60 * 60L;
    /**
     * 登录错误次数
     */
    String LOGIN_ERR_ = "login:err:";
    /**
     * 登录锁定
     */
    String LOGIN_LOCK_ = "login:lock:";

    /**
     * 学员登录错误次数
     */
    String STUDENT_LOGIN_ERR_ = "student:login:err:";
    /**
     * 学员登录锁定
     */
    String STUDENT_LOGIN_LOCK_ = "student:login:lock:";
    /**
     * 学员TOKEN
     */
    String STUDENT_TOKEN_ = "student:token:";
    /**
     * 学员ID 对应token
     */
    String STUDENT_ID_ = "student:id:";
    /**
     * 有效时间 一天
     */
    Long STUDENT_TOKEN_EXPIRE = 24 * 60 * 60L;
}
