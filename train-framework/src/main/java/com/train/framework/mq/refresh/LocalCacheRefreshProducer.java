package com.train.framework.mq.refresh;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.train.framework.mq.RedisMQTemplate;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;

/**
 * 本地缓存刷新消息发送
 * @author zhushengran
 */
@Slf4j
public class LocalCacheRefreshProducer {
    /**
     * 发送本地缓存刷新消息
     *
     * @param localCacheKey 本地缓存标识 无需是具体的缓存key
     * @param params        其他负载参数
     * @param headers       消息Header
     */
    public static void sendRefreshMessage(@NonNull String localCacheKey, Map<String, Object> params, Map<String, String> headers) {
        RedisMQTemplate redisMQTemplate = SpringUtil.getBean(RedisMQTemplate.class);
        if (redisMQTemplate == null) {
            log.error("本地缓存刷新消息发送失败，未获取到 redisMQTemplate");
            return;
        }
        LocalCacheRefreshMessage message = new LocalCacheRefreshMessage(localCacheKey, params);
        if (MapUtil.isNotEmpty(headers)) {
            Iterator it = headers.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                message.addHeader(key, headers.get(key));
            }
        }
        try {
            redisMQTemplate.send(message);
        } catch (Exception e) {
            log.error("本地缓存刷新消息发送失败", e);
        }
    }

    /**
     * 发送本地缓存刷新消息
     *
     * @param localCacheKey 本地缓存标识 无需是具体的缓存key
     * @param params        其他负载参数
     */
    public static void sendRefreshMessage(@NonNull String localCacheKey, Map<String, Object> params) {
        sendRefreshMessage(localCacheKey, params, null);
    }

    /**
     * 发送本地缓存刷新消息
     *
     * @param localCacheKey 本地缓存标识 无需是具体的缓存key
     */
    public static void sendRefreshMessage(@NonNull String localCacheKey) {
        sendRefreshMessage(localCacheKey, null);
    }
}
