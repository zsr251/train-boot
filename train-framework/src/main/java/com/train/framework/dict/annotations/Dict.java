package com.train.framework.dict.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
    /**
     * 字典类型 对应字典表中的 dict_type 字段
     */
    String key();

    /**
     * 翻译后的字段名
     * 如果没有填，则默认新增字段名为： 字段名 + _label
     * 比如： 字段名：sex， 则新增字段名：sex_label
     */
    String ref() default "";

}
