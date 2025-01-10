package com.train.framework.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RepeatRequestLimitException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String requestMethod;
    private String ip;
    private String username;
    private long repeatTimes;

    public RepeatRequestLimitException(String message, String requestMethod, String ip, String username, long repeatTimes) {
        super(message);
        this.requestMethod = requestMethod;
        this.ip = ip;
        this.username = username;
        this.repeatTimes = repeatTimes;
    }

    public RepeatRequestLimitException(String message) {
        super(message);
    }

    public RepeatRequestLimitException(Throwable cause) {
        super(cause);
    }

    public RepeatRequestLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getRequestInfo() {
        return String.format("请求太频繁 IP：【%s】 用户：【%s】 方法名：【%s】 请求次数：【%s】", ip, username, requestMethod, repeatTimes);
    }
}
