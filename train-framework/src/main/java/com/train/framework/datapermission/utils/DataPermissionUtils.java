package com.train.framework.datapermission.utils;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.train.framework.datapermission.context.DataPermissionContextHolder;
import com.train.framework.datapermission.dto.DataPermissionDTO;
import com.train.framework.datapermission.enums.DataPermissionRuleTypeEnum;
import com.train.framework.security.user.SecurityUser;
import com.train.framework.security.user.UserDetail;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据权限工具
 *
 * @author zhushengran
 */
@Slf4j
public class DataPermissionUtils {
    /**
     * 获取数据权限SQL
     *
     * @param ruleFlag 权限标示名称 传入null时，所有SQL权限全部生效
     * @return SQL
     */
    public static String getSql(String ruleFlag) {
        List<DataPermissionDTO> list = null;
        try {
            list = DataPermissionContextHolder.getAllRule();
        } catch (Exception e) {
            log.warn("获取数据权限异常", e);
        }
        if (list == null || list.isEmpty()) {
            return "";
        }
        list = list.stream().filter(a -> DataPermissionRuleTypeEnum.SQL_RULE.getCode().equals(a.getRuleType())
                && StrUtil.isNotBlank(a.getRuleValue())
                && (ruleFlag == null || ruleFlag.equals(a.getRuleFlag()))).collect(Collectors.toList());
        if (list.isEmpty()) {
            return "";
        }
        QueryWrapper<Object> query = new QueryWrapper<>();
        for (DataPermissionDTO dto : list) {
            query.apply(parseSqlTemplate(dto.getRuleValue()));
        }
        return query.getSqlSegment();
    }

    /**
     * 包装搜索条件 把所有符合标签的数据权限进行包装
     *
     * @param ruleFlag     权限标示名称 传入null时，所有SQL权限全部生效
     * @param queryWrapper 搜索
     * @param T            数据库实例类型
     * @return queryWrapper
     */
    public static <T> LambdaQueryWrapper<T> wrapDataPermission(String ruleFlag, LambdaQueryWrapper<T> queryWrapper) {
        queryWrapper = queryWrapper == null ? new LambdaQueryWrapper<>() : queryWrapper;
        List<DataPermissionDTO> list = null;
        try {
            list = DataPermissionContextHolder.getAllRule();
        } catch (Exception e) {
            log.warn("获取数据权限异常", e);
        }
        if (list == null || list.isEmpty()) {
            return queryWrapper;
        }
        list = list.stream().filter(a -> DataPermissionRuleTypeEnum.SQL_RULE.getCode().equals(a.getRuleType())
                && StrUtil.isNotBlank(a.getRuleValue())
                && (ruleFlag == null || ruleFlag.equals(a.getRuleFlag()))).collect(Collectors.toList());
        if (list.isEmpty()) {
            return queryWrapper;
        }
        // 逐个进行包装
        for (DataPermissionDTO dto : list) {
            queryWrapper.apply(parseSqlTemplate(dto.getRuleValue()));
        }

        return queryWrapper;
    }

    /**
     * 根据用户当前用户信息 填充数据权限SQL
     *
     * @param template SQL模版 {A} 格式
     * @return 格式化后字符串
     */
    private static String parseSqlTemplate(String template) {
        if (template.indexOf('{') < 0) {
            return template;
        }
        Map<String, Object> map = new HashMap<>(5);
        // 用户信息
        UserDetail user = SecurityUser.getUser();
        if (user != null) {
            map.put("USER_ID", user.getId());
            map.put("USERNAME", user.getUsername());
            map.put("REAL_NAME", user.getRealName());
            map.put("ORG_ID", user.getOrgId());
            map.put("TENANT_ID", user.getTenantId());
        }
        return StrUtil.format(template, map);
    }

    /**
     * 获取配置的数据权限：列展示权限  列权限不会在自动组装到SQL中
     * 不能自动处理，需要在业务代码中处理，权限内容中使用,分割列名
     * 权限类型包含：只允许展示列 不允许展示列
     *
     * @param ruleFlag 列权限标示名称
     * @return 如果不存在 则为 null
     */
    private static Map<DataPermissionRuleTypeEnum, Set<String>> getColumnShowDataAuth(String ruleFlag) {
        if (StrUtil.isEmpty(ruleFlag)) {
            return null;
        }
        List<DataPermissionDTO> list = null;
        try {
            list = DataPermissionContextHolder.getAllRule();
        } catch (Exception e) {
            log.debug("获取数据权限异常");
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        list = list.stream().filter(a -> ruleFlag.equals(a.getRuleFlag())).collect(Collectors.toList());
        if (list.isEmpty()) {
            return null;
        }
        Map<DataPermissionRuleTypeEnum, Set<String>> result = new HashMap<>();
        Set<String> canOnly = new HashSet<>();
        Set<String> ban = new HashSet<>();
        for (DataPermissionDTO ruleModel : list) {
            // 如果是只允许展示
            if (DataPermissionRuleTypeEnum.COL_CAN_ONLY.getCode().equals(ruleModel.getRuleType())) {
                canOnly.addAll(StrUtil.splitTrim(ruleModel.getRuleValue(), ","));
            }
            // 如果是不允许展示
            if (DataPermissionRuleTypeEnum.COL_BAN.getCode().equals(ruleModel.getRuleType())) {
                ban.addAll(StrUtil.splitTrim(ruleModel.getRuleValue(), ","));
            }
        }
        result.put(DataPermissionRuleTypeEnum.COL_CAN_ONLY, canOnly);
        result.put(DataPermissionRuleTypeEnum.COL_BAN, ban);
        return result;
    }

    /**
     * 根据列权限 过滤一个列表中 每个对象中的字段
     * 例如：[A(name='张三',age=18),A(name='李四',age=20)]
     * 如果没有看到A对象age字段的权限，过滤之后则为: [A(name='张三',age=null),A(name='李四',age=null)]
     *
     * @param ruleFlag 列权限标示名称
     * @param tList    需要过滤字段的对象 的list
     * @param clz      范型
     * @return
     */
    public static <T> List<T> filterFieldWithAuth(String ruleFlag, List<T> tList, Class<T> clz) {
        if (StrUtil.isBlank(ruleFlag) || tList == null || tList.isEmpty()) {
            return tList;
        }
        Map<DataPermissionRuleTypeEnum, Set<String>> authMap = DataPermissionUtils.getColumnShowDataAuth(ruleFlag);
        if (authMap == null) {
            return tList;
        }
        // 不允许看的
        Set<String> ban = authMap.get(DataPermissionRuleTypeEnum.COL_BAN);
        // 只能看的
        Set<String> canOnly = authMap.get(DataPermissionRuleTypeEnum.COL_CAN_ONLY);
        for (T t : tList) {
            filterFieldWithAuth(ban, canOnly, t, clz);
        }
        return tList;
    }

    /**
     * 根据列权限 过滤一个对象中的字段
     * 例如：A(name='张三',age=18)
     * 如果没有看到A对象age字段的权限，过滤之后则为: A(name='张三',age=null)
     *
     * @param ruleFlag 列权限标示名称
     * @param t        需要过滤字段的对象
     * @param clz      范型
     * @return
     */
    public static <T> T filterFieldWithAuth(String ruleFlag, T t, Class<T> clz) {
        if (StrUtil.isBlank(ruleFlag) || t == null) {
            return t;
        }
        Map<DataPermissionRuleTypeEnum, Set<String>> authMap = DataPermissionUtils.getColumnShowDataAuth(ruleFlag);
        if (authMap == null) {
            return t;
        }
        // 不允许看的
        Set<String> ban = authMap.get(DataPermissionRuleTypeEnum.COL_BAN);
        // 只能看的
        Set<String> canOnly = authMap.get(DataPermissionRuleTypeEnum.COL_CAN_ONLY);
        return filterFieldWithAuth(ban, canOnly, t, clz);
    }

    /**
     * 根据具体权限过滤字段
     *
     * @param ban     不允许看的
     * @param canOnly 只能看的
     * @param t       需要过滤字段的对象
     * @param clz     范型
     * @return
     */
    private static <T> T filterFieldWithAuth(Set<String> ban, Set<String> canOnly, T t, Class<T> clz) {
        if (t == null) {
            return t;
        }
        if (ban.isEmpty() && canOnly.isEmpty()) {
            return t;
        }
        Map<String, Field> fieldMap = ReflectUtil.getFieldMap(clz);
        // 遍历对象字段
        for (String k : fieldMap.keySet()) {
            // 如果禁止的包含该字段
            if (ban.contains(k)) {
                // 非final修饰的字段才能修改
                if (!java.lang.reflect.Modifier.isFinal(fieldMap.get(k).getModifiers())) {
                    ReflectUtil.setFieldValue(t, fieldMap.get(k), null);
                }
            }
            // 如果只允许的列表不为空 且字段不包含在只允许的范围内
            if (!canOnly.isEmpty() && !canOnly.contains(k)) {
                // 非final修饰的字段才能修改
                if (!java.lang.reflect.Modifier.isFinal(fieldMap.get(k).getModifiers())) {
                    ReflectUtil.setFieldValue(t, fieldMap.get(k), null);
                }
            }
        }
        return t;
    }

    /**
     * 根据列权限 根据对象指定的字段名 过滤掉不符合权限的对象
     * 例如：[A(realName='张三',age=18),A(realName='李四',age=20)]
     * 如果没有A对象字段名realName为张三的对象权限，过滤之后则为：[A(realName='李四',age=20)]
     *
     * @param ruleFlag  列权限标示名称
     * @param tList     需要过滤的所有对象列表
     * @param fieldName 过滤对象里指定字段名
     * @param clz
     * @return
     */
    public static <T> List<T> filterItemWithAuth(String ruleFlag, List<T> tList, String fieldName, Class<T> clz) {
        if (StrUtil.isBlank(ruleFlag) || StrUtil.isBlank(fieldName) || tList == null || tList.isEmpty()) {
            return tList;
        }
        Map<DataPermissionRuleTypeEnum, Set<String>> authMap = DataPermissionUtils.getColumnShowDataAuth(ruleFlag);
        if (authMap == null) {
            return tList;
        }
        // 不允许看的
        Set<String> ban = authMap.get(DataPermissionRuleTypeEnum.COL_BAN);
        // 只能看的
        Set<String> canOnly = authMap.get(DataPermissionRuleTypeEnum.COL_CAN_ONLY);

        return tList.stream()
                // 过滤不允许看的
                .filter(a -> ban == null || ban.isEmpty() || !ban.contains(StrUtil.toString(ReflectUtil.getFieldValue(a, fieldName))))
                // 过滤只能看的
                .filter(a -> canOnly == null || canOnly.isEmpty() || canOnly.contains(StrUtil.toString(ReflectUtil.getFieldValue(a, fieldName))))
                .collect(Collectors.toList());
    }

    /**
     * 根据列权限 根据对象指定的字段名 过滤掉不符合权限的对象
     * 例如：['张三','李四']
     * 如果禁止张三权限，过滤之后则为：['李四']
     *
     * @param ruleFlag 列权限标示名称
     * @param tList    需要过滤的所有字符列表
     * @return
     */
    public static List<String> filterItemWithAuth(String ruleFlag, List<String> tList) {
        if (StrUtil.isBlank(ruleFlag) || tList == null || tList.isEmpty()) {
            return tList;
        }
        Map<DataPermissionRuleTypeEnum, Set<String>> authMap = DataPermissionUtils.getColumnShowDataAuth(ruleFlag);
        if (authMap == null) {
            return tList;
        }
        // 不允许看的
        Set<String> ban = authMap.get(DataPermissionRuleTypeEnum.COL_BAN);
        // 只能看的
        Set<String> canOnly = authMap.get(DataPermissionRuleTypeEnum.COL_CAN_ONLY);

        return tList.stream()
                // 过滤不允许看的
                .filter(a -> ban == null || ban.isEmpty() || !ban.contains(a))
                // 过滤只能看的
                .filter(a -> canOnly == null || canOnly.isEmpty() || canOnly.contains(a))
                .collect(Collectors.toList());
    }

}
