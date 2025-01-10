package com.train.framework.common.exception;

/**
 * Excel异常 该异常不会被导入工具捕获
 *
 * @author jasonzhu
 * @date 2020/6/26
 */
public class ExcelException extends RuntimeException {
    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }
}
