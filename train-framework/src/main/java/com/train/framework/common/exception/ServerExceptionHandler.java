package com.train.framework.common.exception;

import cn.hutool.core.exceptions.ValidateException;
import com.train.framework.common.utils.IpUtils;
import com.train.framework.common.utils.Result;
import com.train.framework.security.user.SecurityUser;
import com.train.framework.security.user.UserDetail;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;


/**
 * 异常处理器
 */
@Slf4j
@RestControllerAdvice
public class ServerExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServerException.class)
    public Result<String> handleException(HttpServletRequest request, ServerException e) {
        log.error(String.format("自定义业务异常，%s 结论：%s", getCurrentInfo(request), e.getMessage()), e);
        return Result.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler({RepeatRequestLimitException.class})
    public Result<?> handleRepeatException(HttpServletRequest request, RepeatRequestLimitException e) {
        log.error("请求频次限制，{} 结论：{}", getCurrentInfo(request), e.getRequestInfo());
        return Result.error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(ExcelException.class)
    public Result<?> handleExcelException(HttpServletRequest request, ExcelException e) {
        log.error(String.format("Excel导入导出异常，%s 结论：%s", getCurrentInfo(request), e.getMessage()), e);
        return Result.error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(ValidateException.class)
    public Result<String> validateException(HttpServletRequest request, ValidateException ex) {
        log.error(String.format("参数验证异常，%s 结论：%s", getCurrentInfo(request), ex.getMessage()), ex);
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result<?> handleDuplicateKeyException(HttpServletRequest request, DuplicateKeyException e) {
        log.error(String.format("数据库数据重复，%s 结论：%s", getCurrentInfo(request), e.getMessage()), e);
        return Result.error("数据库中已存在该记录");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<?> handlerNoFoundException(HttpServletRequest request, Exception e) {
        log.error(String.format("路径不存在，%s 结论：%s", getCurrentInfo(request), e.getMessage()), e);
        return Result.error("路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<?> HttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        StringBuffer sb = new StringBuffer();
        sb.append("不支持");
        sb.append(e.getMethod());
        sb.append("请求方法，");
        sb.append("支持：");
        String[] methods = e.getSupportedMethods();
        sb.append(String.join("、", methods));
        log.error("调用方法不支持，{} 结论：{}", getCurrentInfo(request), sb.toString());
        return Result.error(sb.toString());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException e) {
        String str = String.format("参数缺失：%s", e.getParameterName());
        log.error("调用参数缺失，{} 结论：{}", getCurrentInfo(request), str);
        return Result.error(str);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        String str = String.format("参数类型错误，参数：%s 类型应该是：%s", e.getParameter().getParameterName(), e.getParameter().getParameterType().getSimpleName());
        log.error("参数类型错误，{} 结论：{}", getCurrentInfo(request), str);
        return Result.error(str);
    }

    /**
     * SpringMVC参数绑定，Validator校验不正确
     */
    @ExceptionHandler(BindException.class)
    public Result<String> bindException(HttpServletRequest request, BindException ex) {
        log.error(String.format("参数绑定异常，%s 结论：%s", getCurrentInfo(request), ex.getMessage()), ex);
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null;
        return Result.error(fieldError.getDefaultMessage());
    }

    /**
     * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<?> handleMaxUploadSizeExceededException(HttpServletRequest request, MaxUploadSizeExceededException e) {
        log.error(String.format("上传异常，%s 结论：%s", getCurrentInfo(request), e.getMessage()));
        return Result.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result<?> handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException e) {
        log.error(String.format("数据库插入异常，%s 结论：%s", getCurrentInfo(request), e.getMessage()), e);
        return Result.error("数据库插入异常，可能有未填字段或字段超长");
    }

    @ExceptionHandler(PoolException.class)
    public Result<?> handlePoolException(HttpServletRequest request, PoolException e) {
        log.error(String.format("Redis连接异常，%s 结论：%s", getCurrentInfo(request), e.getMessage()), e);
        return Result.error("Redis 连接异常!");
    }

    @ExceptionHandler(MyBatisSystemException.class)
    public Result<?> handleMybatisException(HttpServletRequest request, MyBatisSystemException e) {
        log.error(String.format("数据库操作异常，%s 结论：%s", getCurrentInfo(request), e.getMessage()), e);
        return Result.error("数据操作异常，请检查后再重试");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(HttpServletRequest request, NoResourceFoundException e) {
        log.error(String.format("资源未找到，%s 结论：%s", getCurrentInfo(request), e.getMessage()), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 Not Found: " + e.getResourcePath());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> handleAccessDeniedException(HttpServletRequest request, Exception ex) {
        log.error(String.format("权限不足，%s 结论：%s", getCurrentInfo(request), ex.getMessage()), ex);
        return Result.error(ErrorCode.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(HttpServletRequest request, Exception e) {
        log.error(String.format("其他异常，%s 结论：%s", getCurrentInfo(request), e.getMessage()), e);
        if (e.getClass() == HttpMessageNotReadableException.class) {
            return Result.error("提交的参数异常，请检查后再提交");
        }
        if (e.getClass() == SQLException.class) {
            return Result.error("提交的参数不合法，请检查后再提交");
        }
        if (e.getClass() == IllegalArgumentException.class) {
            return Result.error(e.getMessage());
        }
        return Result.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 获取当前信息
     */
    private String getCurrentInfo(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        String address = IpUtils.getAddressByIP(ip);
        UserDetail user = SecurityUser.getUser();
        if (user != null) {
            return String.format("路径：【%s】 IP：【%s】 地址：【%s】 用户：【%s】 用户ID：【%s】", request.getRequestURI(), ip, address, user.getUsername(), user.getId());
        } else {
            return String.format("路径：【%s】 IP：【%s】 地址：【%s】", request.getRequestURI(), ip, address);
        }
    }
}