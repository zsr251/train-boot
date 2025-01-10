package com.train.framework.dict.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.dict.annotations.Dict;
import com.train.framework.dict.annotations.DictNeed;
import com.train.framework.dict.utils.DictUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class DictAspect {
    /**
     * 默认的翻译后的字段
     */
    private static final String DICT_SUFFIX = "_label";
    /**
     * 定义切点Pointcut
     */
    @Pointcut("execution(public * com.train..*.*Controller.*(..))")
    public void executeService() {}

    @Around("executeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time1 = System.currentTimeMillis();
        Object result = pjp.proceed();
        long time2 = System.currentTimeMillis();
        log.debug("字典：获取返回数据 耗时：" + (time2 - time1) + "ms");

        long start = System.currentTimeMillis();
        this.parseDictText(result);
        long end = System.currentTimeMillis();
        log.debug("字典：解析返回数据 耗时" + (end - start) + "ms");
        return result;
    }

    /**
     * 本方法针对返回对象为Result
     * 如果是分页的则 只对 PageResult 进行转换
     * <p>
     * 支持各种嵌套 但是在需要翻译的VO上需要添加 @DictNeed 注解，在嵌套需要翻译的字段上也需要添加 @DictNeed 注解
     *
     */
    private void parseDictText(Object result) {
        if (result instanceof Result) {
            Object record = ((Result) result).getData();
            ((Result) result).setData(convert(record));
        }
    }
    /**
     * 进行字典转化
     * 转化结果只能进行Json返回 不要在业务代码中使用
     *
     * @param record 需要处理的对象
     * @return 转化后的对象
     */
    private Object convert(Object record) {
        if (record == null) {
            return null;
        }
        // 如果是分页
        if (record instanceof PageResult) {
            return ((PageResult) record).setList((List) convert(((PageResult) record).getList()));
        }
        // 如果是列表
        if (record instanceof List) {
            List<Object> objectList = new ArrayList<>();
            for (Object o : (List) record) {
                objectList.add(convertOne(o));
            }
            return objectList;
        }
        // 如果是Map
        if (record instanceof Map) {
            for (Object o : ((Map) record).keySet()) {
                ((Map) record).put(o, convertOne(((Map) record).get(o)));
            }
            return record;
        }
        // 如果是单值 则看是否需要翻译
        if (record.getClass().getAnnotation(DictNeed.class) == null) {
            return record;
        }
        // 如果是单个值
        return convertOne(record);
    }

    /**
     * 转化单个值
     * @param record 需要处理的对象
     * @return 转化后的对象
     */
    private Object convertOne(Object record) {
        if (record == null) {
            return null;
        }
        // 如果是列表 Map 分页
        if (record instanceof List || record instanceof Map || record instanceof PageResult) {
            return convert(record);
        }
        // 如果没有标注为需要字典翻译的Bean则不翻译
        if (record.getClass().getAnnotation(DictNeed.class) == null) {
            return record;
        }
        Map<String, Object> propMap = BeanUtil.beanToMap(record);
        Map<String, Field> fieldMap = ReflectUtil.getFieldMap(record.getClass());
        for (String fieldName : fieldMap.keySet()) {
            Field field = fieldMap.get(fieldName);
            Dict dict = field.getAnnotation(Dict.class);
            // 如果存在 则需要翻译
            if (dict != null) {
                String dictType = dict.key();
                String ref = dict.ref();
                String dictValue = StrUtil.toString(propMap.get(fieldName));
                //翻译字典值对应的txt
                String dictLabel = DictUtils.transValueToLabel(dictType, dictValue);
                if (StrUtil.isBlank(ref)){
                    propMap.put(fieldName + DICT_SUFFIX, dictLabel);
                }else {
                    propMap.put(StrUtil.trim(ref), dictLabel);
                }
                continue;
            }
            DictNeed voDict = field.getAnnotation(DictNeed.class);
            // 如果存在 则需要对该vo翻译
            if (voDict != null) {
                propMap.put(fieldName, convert(propMap.get(fieldName)));
                continue;
            }
            // 如果是日期格式 因为不再是对象Json不能自动格式化 处理
            if (field.getType().equals(Date.class)) {
                String pattern = null;
                if (field.getAnnotation(JsonFormat.class) != null) {
                    pattern = field.getAnnotation(JsonFormat.class).pattern();
                }
                if (StrUtil.isBlank(pattern)) {
                    pattern = "yyyy-MM-dd HH:mm:ss";
                }
                propMap.put(fieldName, DateUtil.format((Date) propMap.get(fieldName), pattern));
            }
            if (field.getType().equals(LocalDateTime.class)){
                String pattern = null;
                if (field.getAnnotation(JsonFormat.class) != null) {
                    pattern = field.getAnnotation(JsonFormat.class).pattern();
                }
                if (StrUtil.isBlank(pattern)) {
                    pattern = "yyyy-MM-dd HH:mm:ss";
                }
                propMap.put(fieldName, DateUtil.format((LocalDateTime) propMap.get(fieldName), pattern));
            }
        }
        return propMap;
    }
}
