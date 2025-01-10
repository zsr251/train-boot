package com.train.framework.mq.refresh;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.train.framework.common.utils.JsonUtils;
import com.train.framework.mq.pubsub.AbstractChannelMessageListener;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 本地缓存刷新
 * @author zhushengran
 */
@Slf4j
public class LocalCacheRefreshConsumer extends AbstractChannelMessageListener<LocalCacheRefreshMessage> {
    /**
     * 本地缓存key对应的bean 注册表
     */
    private Map<String, Set<LocalCacheRefresh>> refreshBeanMap = new HashMap<>();

    /**
     * 增加本地缓存注册
     *
     * @param bean
     */
    public void addRefreshBean(LocalCacheRefresh bean) {
        if (bean == null) {
            return;
        }
        Set<LocalCacheRefresh> s = refreshBeanMap.get(bean.getLocalCacheKey());
        if (s == null) {
            s = new HashSet<>();
            s.add(bean);
            refreshBeanMap.put(bean.getLocalCacheKey(), s);
        } else {
            s.add(bean);
        }
    }

    /**
     * 增加本地缓存注册
     *
     * @param beanList
     */
    public void addRefreshBean(List<LocalCacheRefresh> beanList) {
        if (CollUtil.isEmpty(beanList)) {
            return;
        }
        beanList.forEach(a -> addRefreshBean(a));
    }

    @Override
    public void onMessage(LocalCacheRefreshMessage message) {
        if (message == null || StrUtil.isBlank(message.getLocalCacheKey())) {
            log.warn("接收到本地缓存刷新消息，但缓存标识不存在，无法处理：{}", JsonUtils.toJsonString(message));
        }
        Set<LocalCacheRefresh> beanSet = refreshBeanMap.get(message.getLocalCacheKey());
        if (beanSet == null || beanSet.isEmpty()) {
            log.warn("接收到本地缓存刷新消息【{}】未设置对应处理Bean", message.getLocalCacheKey());
            return;
        }
        for (LocalCacheRefresh localCacheRefresh : beanSet) {
            try {
                localCacheRefresh.initLocalCache(message);
            } catch (Exception e) {
                log.error(StrUtil.format("接收到本地刷新消息【{}】Bean【{}】方法处理异常", message.getLocalCacheKey(), localCacheRefresh.getClass().getName()), e);
            }
        }
        log.info("接收到本地缓存刷新消息【{}】，并已处理完成",message.getLocalCacheKey());
    }
}
