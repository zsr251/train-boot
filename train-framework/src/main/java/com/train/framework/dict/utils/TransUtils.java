package com.train.framework.dict.utils;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.train.framework.dict.annotations.TransTable;
import com.train.framework.dict.api.TransApi;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 转换工具
 */
@Slf4j
public class TransUtils {
    /**
     * 转换
     */
    private static TransApi transApi;

    /**
     * 转换单个
     * <h2>注意字段值，必须保证非用户输入，否则会造成SQL注入</h2>
     *
     * @param source      原数据
     * @param targetClass 目标类
     * @return
     */
    public static <T> T transOne(T source, Class<T> targetClass) {
        if (source == null || targetClass == null) {
            return null;
        }
        initBean();
        Map<String, Field> fieldMap = ReflectUtil.getFieldMap(targetClass);
        for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
            Field field = entry.getValue();
            TransTable transTable = field.getAnnotation(TransTable.class);
            if (transTable == null) {
                continue;
            }
            String table = transTable.table();
            String key = transTable.key();
            String[] fields = transTable.fields();
            String[] refs = transTable.refs();
            if (fields.length != refs.length) {
                log.warn("{} 表字段转换错误：fields 与 refs的个数不匹配", field.getName());
                continue;
            }
            Object keyValue = ReflectUtil.getFieldValue(source, field);
            if (keyValue == null) {
                continue;
            }
            Map<String, Object> result = transApi.transOneFromTable(table, key, fields, refs, keyValue);
            // 对结果进行赋值
            for (Map.Entry<String, Object> objectEntry : result.entrySet()) {
                try {
                    ReflectUtil.setFieldValue(source, objectEntry.getKey(), objectEntry.getValue());
                } catch (Exception e) {
                    log.warn("字段 {} 赋值失败：{}", objectEntry.getKey(), e.getMessage());
                }
            }
        }
        return source;
    }

    /**
     * 转换列表
     * <h2>注意字段值，必须保证非用户输入，否则会造成SQL注入</h2>
     *
     * @param source      原数据
     * @param targetClass 目标类
     * @return
     */
    public static <T> List<T> transList(List<T> source, Class<T> targetClass) {
        if (source == null || targetClass == null || source.isEmpty()) {
            return source;
        }
        initBean();
        Map<String, Field> fieldMap = ReflectUtil.getFieldMap(targetClass);
        for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
            Field field = entry.getValue();
            TransTable transTable = field.getAnnotation(TransTable.class);
            if (transTable == null) {
                continue;
            }
            String table = transTable.table();
            String key = transTable.key();
            String[] fields = transTable.fields();
            String[] refs = transTable.refs();
            if (fields.length != refs.length) {
                log.warn("{} 表字段转换错误：fields 与 refs的个数不匹配", field.getName());
                continue;
            }
            Set keyValues = source.stream().map(obj -> ReflectUtil.getFieldValue(obj, field)).collect(Collectors.toSet());
            Map<String, Map<String, Object>> tableResult = transApi.transListFromTable(table, key, fields, refs, keyValues.stream().toList());
            for (T t : source) {
                Object ko = ReflectUtil.getFieldValue(t, entry.getKey());
                if (ko == null) {
                    continue;
                }
                String k = StrUtil.toString(ko);
                if (tableResult.containsKey(k)) {
                    Map<String, Object> result = tableResult.get(k);
                    for (Map.Entry<String, Object> r : result.entrySet()) {
                        if (StrUtil.equals(key, r.getKey())) {
                            continue;
                        }
                        try {
                            ReflectUtil.setFieldValue(t, r.getKey(), r.getValue());
                        } catch (Exception e) {
                            log.warn("字段 {} 赋值失败：{}", r.getKey(), e.getMessage());
                            continue;
                        }
                    }
                }
            }
        }
        return source;
    }

    /**
     * 初始化Bean
     */
    private static void initBean() {
        if (transApi == null) {
            transApi = SpringUtil.getBean(TransApi.class);
        }
    }
}
