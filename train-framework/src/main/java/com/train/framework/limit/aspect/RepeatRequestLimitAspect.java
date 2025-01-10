package com.train.framework.limit.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import com.train.framework.common.constant.RedisConstant;
import com.train.framework.common.exception.RepeatRequestLimitException;
import com.train.framework.common.utils.HttpContextUtils;
import com.train.framework.common.utils.IpUtils;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.framework.security.user.SecurityUser;
import com.train.framework.security.user.UserDetail;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 防重复提交，切面处理类
 */
@Slf4j
@Aspect
@Component
public class RepeatRequestLimitAspect {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 解析EL表达式
     */
    private SpelExpressionParser spelParser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Pointcut("@annotation(annotations.limit.framework.com.train.RepeatRequestLimit)")
    public void repeatLimitCut() {
    }

    @Before("repeatLimitCut()")
    public void before(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RepeatRequestLimit repeatRequestLimit = method.getAnnotation(RepeatRequestLimit.class);
        String key = "";
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        // 用户信息
        UserDetail user = SecurityUser.getUser();
        String username = "";
        if (StrUtil.isBlank(repeatRequestLimit.key())) {
            Object[] args = joinPoint.getArgs();
            if (user != null) {
                username = user.getUsername();
                key = RedisConstant.LIMIT_REPEAT_ + SecureUtil.md5(user.getId() + className + methodName + JSONUtil.toJsonStr(args));
            } else {
                key = RedisConstant.LIMIT_REPEAT_ + SecureUtil.md5(className + methodName + JSONUtil.toJsonStr(args));
            }

        } else if (!repeatRequestLimit.key().contains("#")) {
            key = RedisConstant.LIMIT_REPEAT_ + repeatRequestLimit.key();
        } else {
            // 使用spring的DefaultParameterNameDiscoverer获取方法形参名数组
            String[] paramNames = nameDiscoverer.getParameterNames(method);
            //获取方法的实际参数值
            Object[] arguments = joinPoint.getArgs();
            //设置解析spel所需的数据上下文
            EvaluationContext context = new StandardEvaluationContext();
            for (int len = 0; len < paramNames.length; len++) {
                context.setVariable(paramNames[len], arguments[len]);
            }
            Expression expression = spelParser.parseExpression(repeatRequestLimit.key());
            Object theValue = expression.getValue(context);
            key = RedisConstant.LIMIT_REPEAT_ + StrUtil.toString(theValue);
        }

        long n = 0;
        try {
            n = redisTemplate.opsForValue().increment(key, 1);
            if (n == 1) {
                // 设置超时时间
                redisTemplate.expire(key, repeatRequestLimit.expireSeconds(), TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.warn("重复提交校验失败", e);
        }
        if (n > 1) {
            //获取request
            // 请求相关
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            throw new RepeatRequestLimitException(StrUtil.isBlank(repeatRequestLimit.errMsg()) ? "请求太频繁，请稍后再试" : repeatRequestLimit.errMsg(), className + "." + methodName + "()", IpUtils.getIpAddr(request), username, n);
        }
    }
}
