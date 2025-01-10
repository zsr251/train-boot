package com.train.system.api;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.train.framework.dict.api.TransApi;
import com.train.system.dao.SysDictDataDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component("transApi")
@AllArgsConstructor
public class TransApiImpl implements TransApi {
    private final SysDictDataDao sysDictDataDao;

    @Override
    public Map<String, Object> transOneFromTable(String table, String key, String[] fields, String[] refs, Object keyValue) {
        if (StrUtil.isBlank(table) || StrUtil.isBlank(key) || fields == null || fields.length == 0 || refs == null || refs.length != fields.length || keyValue == null) {
            return Map.of();
        }
        StringBuilder sql = new StringBuilder("select ");
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                sql.append(" , ");
            }
            sql.append(fields[i]).append(" as ").append(refs[i]);
        }
        sql.append(" from ").append(table).append(" where deleted = 0 and ").append(key).append(" = ");
        // 判断 keyValue 是否是数字 主要是 Long
        if (StrUtil.contains(key, "id") && NumberUtil.isNumber(StrUtil.toString(keyValue))) {
            sql.append(keyValue);
        } else {
            if (StrUtil.containsAny(StrUtil.toString(keyValue), '\'', ',', ';', '+')) {
                log.error("可能存在SQL注入风险：{} 【{}】", sql, StrUtil.toString(keyValue));
                return Map.of();
            }
            sql.append("'").append(keyValue).append("'");
        }
        log.info("数据库转换SQL：{}", sql);
        try {
            List<Map<String, Object>> ml = sysDictDataDao.getListMapForSql(sql.toString());
            if (CollUtil.isEmpty(ml)) {
                return Map.of();
            }
            return ml.get(0);
        } catch (Exception e) {
            log.warn("数据库转换SQL执行异常", e);
            return Map.of();
        }
    }

    @Override
    public Map<String, Map<String, Object>> transListFromTable(String table, String key, String[] fields, String[] refs, List<Object> keyValues) {
        if (StrUtil.isBlank(table) || StrUtil.isBlank(key) || fields == null || fields.length == 0 || refs == null || refs.length != fields.length || keyValues == null) {
            return Map.of();
        }
        if (CollUtil.isEmpty(keyValues)) {
            return Map.of();
        }
        StringBuilder sql = new StringBuilder("select ").append(key);
        for (int i = 0; i < fields.length; i++) {
            sql.append(" , ").append(fields[i]).append(" as ").append(refs[i]);
        }
        sql.append(" from ").append(table).append(" where deleted = 0 and ").append(key).append(" in ( ");
        // 判断 keyValue 是否是数字 主要是 Long
        if (StrUtil.contains(key, "id") && NumberUtil.isNumber(StrUtil.toString(keyValues.get(0)))) {
            for (int i = 0; i < keyValues.size(); i++) {
                if (i > 0) {
                    sql.append(" , ");
                }
                sql.append(keyValues.get(i));
            }
        } else {
            for (int i = 0; i < keyValues.size(); i++) {
                if (StrUtil.containsAny(StrUtil.toString(keyValues.get(i)), '\'', ',', ';', '+')) {
                    log.error("可能存在SQL注入风险：{} 【{}】", sql, StrUtil.toString(keyValues.get(i)));
                    return Map.of();
                }
                if (i > 0) {
                    sql.append(" , ");
                }
                sql.append("'").append(keyValues.get(i)).append("'");
            }
        }
        sql.append(" )");
        log.info("数据库转换SQL：{}", sql);
        try {
            List<Map<String, Object>> ml = sysDictDataDao.getListMapForSql(sql.toString());
            if (CollUtil.isEmpty(ml)) {
                return Map.of();
            }
            Map<String, Map<String, Object>> mm = ml.stream().collect(Collectors.toMap(map -> StrUtil.toString(map.get(key)), map -> map, (a, b) -> a));
            return mm;
        } catch (Exception e) {
            log.warn("数据库转换SQL执行异常", e);
            return Map.of();
        }
    }
}
