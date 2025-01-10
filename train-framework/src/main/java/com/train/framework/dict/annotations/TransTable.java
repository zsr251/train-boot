package com.train.framework.dict.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用SQL进行数据转换
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransTable {
    /**
     * 表名 例如：sys_user
     */
    String table();

    /**
     * where条件的键，默认：id  例如：username
     */
    String key() default "id";

    /**
     * 搜索的字段  例如：{username,real_name}
     */
    String[] fields();

    /**
     * 与搜索字段对应附值  例如：{username,realName}
     * 在sql中会变为 username as username,real_name as realName
     */
    String[] refs();
}
