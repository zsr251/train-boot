package com.train.framework.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误编码
 *

 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(401, "登录超时或失效，请重试"),
    FORBIDDEN(403, "没有权限，禁止访问"),
    REFRESH_TOKEN_INVALID(400, "refresh_token 已失效"),
    INTERNAL_SERVER_ERROR(500, "服务器异常，请稍后再试");

    private final int code;
    private final String msg;
}
