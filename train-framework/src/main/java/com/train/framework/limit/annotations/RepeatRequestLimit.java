package com.train.framework.limit.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatRequestLimit {
    /**
     * 请求标示 redis key 组成部分
     * @return
     */
    String key() default "";
    /**
     * 重复超时时间
     * @return
     */
    long expireSeconds() default 3;
    /**
     * 提醒的错误内容
     * @return
     */
    String errMsg() default "请求太频繁，请稍后再试";
}
