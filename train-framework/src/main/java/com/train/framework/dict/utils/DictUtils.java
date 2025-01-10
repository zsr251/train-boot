package com.train.framework.dict.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.train.framework.common.constant.RedisConstant;
import com.train.framework.dict.api.DictApi;
import com.train.framework.dict.dto.DictDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 字典工具类
 */
@Slf4j
public class DictUtils {
    private static Cache<String, Object> dictLocalCache;
    private static RedisTemplate<String, Object> redisTemplate;
    private static DictApi dictApi;

    /**
     * 字典正转换 从 value --> label
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    public static String transValueToLabel(String dictType, String dictValue) {
        if (StrUtil.isBlank(dictType) || StrUtil.isBlank(dictValue)) {
            return null;
        }
        initBean();
        String redisKey = RedisConstant.DICT_ + dictType + ":vl";
        Object temp = dictLocalCache.get(redisKey + ":" + dictValue, (key) -> {
            if (Boolean.FALSE.equals(redisTemplate.hasKey(redisKey))) {
                List<DictDTO> dtoList = dictApi.getDictList(dictType);
                if (CollUtil.isEmpty(dtoList)) {
                    log.warn("获取字典类型为：" + dictType + " 的字典数据为空");
                    return "";
                }
                redisTemplate.opsForHash().putAll(redisKey, dtoList.stream().collect(Collectors.toMap(DictDTO::getValue, DictDTO::getLabel, (first, second) -> first)));
                redisTemplate.expire(redisKey, RedisConstant.DICT_EXPIRE, TimeUnit.SECONDS);
                log.info("缓存字典数据 value --> key：【{}】完成", dictType);
            }
            Object o = redisTemplate.opsForHash().get(redisKey, dictValue);
            if (o == null) {
                return "";
            }
            return o;
        });
        return StrUtil.toString(temp);
    }

    /**
     * 字典逆转换 从 label --> value
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @return 字典值
     */
    public static String transLabelToValue(String dictType, String dictLabel) {
        if (StrUtil.isBlank(dictType) || StrUtil.isBlank(dictLabel)) {
            return null;
        }
        initBean();
        String redisKey = RedisConstant.DICT_ + dictType + ":lv";
        Object temp = dictLocalCache.get(redisKey + ":" + dictLabel, (key) -> {
            if (Boolean.FALSE.equals(redisTemplate.hasKey(redisKey))) {
                List<DictDTO> dtoList = dictApi.getDictList(dictType);
                if (CollUtil.isEmpty(dtoList)) {
                    log.warn("获取字典类型为：" + dictType + " 的字典数据为空");
                    return "";
                }
                redisTemplate.opsForHash().putAll(redisKey, dtoList.stream().collect(Collectors.toMap(DictDTO::getLabel, DictDTO::getValue, (first, second) -> first)));
                redisTemplate.expire(redisKey, RedisConstant.DICT_EXPIRE, TimeUnit.SECONDS);
                log.info("缓存字典数据 key --> value：【{}】完成", dictType);
            }
            Object o = redisTemplate.opsForHash().get(redisKey, dictLabel);
            if (o == null) {
                return "";
            }
            return o;
        });
        return StrUtil.toString(temp);
    }

    /**
     * 初始化Bean
     */
    @SuppressWarnings("unchecked")
    private static void initBean() {
        if (dictLocalCache == null) {
            dictLocalCache = (Cache<String, Object>) SpringUtil.getBean("dictLocalCache");
        }
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
        }
        if (dictApi == null) {
            dictApi = SpringUtil.getBean(DictApi.class);
        }
    }

    /**
     * 刷新redis缓存
     * @param dictType
     */
    public static void refreshRedisCache(String dictType) {
        if (StrUtil.isNotBlank(dictType)){
            return;
        }
        initBean();
        String vlKey = RedisConstant.DICT_ + dictType + ":vl";
        String lvKey = RedisConstant.DICT_ + dictType + ":lv";
        redisTemplate.delete(vlKey);
        redisTemplate.delete(lvKey);
        log.info("刷新redis字典缓存：" + dictType);
    }
    /**
     * 清除本地缓存
     */
    public static void refreshLocalCache() {
        initBean();
        dictLocalCache.invalidateAll();
        log.info("刷新本地字典缓存");
    }

    /**
     * 转换工具 转换单个对象
     */
    public static <T> T  transOne(T source, Class<T> targetClass){
        return source;
    }

    /**
     * 转换工具 转换对象列表
     */
    public static <T> List<T> transList(List<T> source, Class<T> targetClass){
        return source;
    }
}
