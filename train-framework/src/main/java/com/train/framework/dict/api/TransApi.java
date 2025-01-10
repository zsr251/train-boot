package com.train.framework.dict.api;

import java.util.List;
import java.util.Map;

/**
 * 基于数据库的查询
 */
public interface TransApi {
    /**
     * 转换一个值
     * 无缓存，以后可增加数据权限
     * @param table 表名
     * @param key where 条件的字段名
     * @param fields 搜索的字段
     * @param refs 搜索的字段别名
     * @param keyValue where 条件的值
     * @return 搜索结果
     */
    Map<String, Object> transOneFromTable(String table, String key, String[] fields, String[] refs, Object keyValue);

    /**
     * 转换列表值
     * 无缓存，以后可增加数据权限
     * @param table 表名
     * @param key where 条件的字段名
     * @param fields 搜索的字段
     * @param refs 搜索的字段别名
     * @param keyValues where 条件的值
     * @return 搜索结果
     */
    Map<String,Map<String, Object>> transListFromTable(String table, String key, String[] fields, String[] refs, List<Object> keyValues);
}
