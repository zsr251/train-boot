package com.train.framework.dict.annotations;

import java.lang.annotation.*;

/**
 * VO或字段中的VO是否需要被字典解析
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictNeed {
}
