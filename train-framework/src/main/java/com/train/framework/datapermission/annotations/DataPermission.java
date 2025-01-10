package com.train.framework.datapermission.annotations;

import java.lang.annotation.*;

/**
 * 数据权限注解 一般加在 Controller 方法注解上
 * 可声明在类或者方法上，标识使用的数据权限规则
 *
 * @author zhushengran
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * 配置菜单的组件路径,用于数据权限
     */
    String[] components();

}
