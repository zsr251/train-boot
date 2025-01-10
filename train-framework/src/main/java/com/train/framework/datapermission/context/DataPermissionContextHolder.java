package com.train.framework.datapermission.context;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.train.framework.datapermission.dto.DataPermissionDTO;
import com.train.framework.datapermission.enums.DataPermissionRuleTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据权限缓存
 *
 * @author zhushengran
 */
public class DataPermissionContextHolder {
    /**
     * 使用 List 的原因，可能存在方法的嵌套调用
     */
    private static final ThreadLocal<List<DataPermissionDTO>> DATA_PERMISSIONS = TransmittableThreadLocal.withInitial(ArrayList::new);

    /**
     * 添加缓存
     *
     * @param dtoList
     */
    public static void addRule(List<DataPermissionDTO> dtoList) {
        if (!CollUtil.isEmpty(dtoList)) {
            DATA_PERMISSIONS.get().addAll(dtoList);
        }
    }

    /**
     * 获取当前请求所有数据权限
     *
     * @return
     */
    public static List<DataPermissionDTO> getAllRule() {
        return DATA_PERMISSIONS.get();
    }

    /**
     * 获取当前请求指定类型的数据权限
     *
     * @param ruleTypeEnum
     * @return
     */
    public static List<DataPermissionDTO> getRule(DataPermissionRuleTypeEnum ruleTypeEnum) {
        List<DataPermissionDTO> dtoList = DATA_PERMISSIONS.get();
        if (CollUtil.isEmpty(dtoList)) {
            return new ArrayList<>();
        }
        return dtoList.stream().filter(a -> ruleTypeEnum.getCode().equals(a.getRuleType())).collect(Collectors.toList());
    }

    /**
     * 是否有过滤规则，无过滤规则则不进行sql处理
     *
     * @return
     */
    public static boolean hasRule() {
        return CollectionUtil.isNotEmpty(DATA_PERMISSIONS.get());
    }


    /**
     * 清空上下文
     * <p>
     */
    public static void clear() {
        DATA_PERMISSIONS.remove();
    }
}
