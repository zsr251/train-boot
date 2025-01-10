package com.train.framework.datapermission.aspect;

import com.train.framework.common.exception.ErrorCode;
import com.train.framework.common.exception.ServerException;
import com.train.framework.datapermission.annotations.DataPermission;
import com.train.framework.datapermission.api.DataPermissionApi;
import com.train.framework.datapermission.context.DataPermissionContextHolder;
import com.train.framework.datapermission.dto.DataPermissionDTO;
import com.train.framework.security.user.SecurityUser;
import com.train.framework.security.user.UserDetail;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 数据权限切面处理类
 *
 * @author zhushengran
 */
@Slf4j
@Aspect
@Component
public class DataPermissionAspect {
    @Resource
    private DataPermissionApi dataPermissionApi;

    @Around("@annotation(dataPermission)")
    public Object around(ProceedingJoinPoint point, DataPermission dataPermission) throws Throwable {
        // 组件
        String[] components = dataPermission.components();
        if (components == null || components.length < 1) {
            return point.proceed();
        }
        // 用户信息
        UserDetail user = SecurityUser.getUser();
        if (user == null) {
            throw new ServerException(ErrorCode.UNAUTHORIZED);
        }
        // 保存当前请求的数据权限
        List<DataPermissionDTO> dtoList = dataPermissionApi.getDataPermissionList(user.getId(), components);
        DataPermissionContextHolder.addRule(dtoList);
        try {
            return point.proceed();
        } finally {
            // 清空当前
            DataPermissionContextHolder.clear();
        }
    }
}
