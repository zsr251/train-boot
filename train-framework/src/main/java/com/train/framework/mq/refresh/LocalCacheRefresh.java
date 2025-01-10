package com.train.framework.mq.refresh;

/**
 * 刷新本地缓存接口
 */
public interface LocalCacheRefresh {
    /**
     * 获取本地缓存的标识
     *
     * @return 非空标识，不一定是具体的缓存key
     */
    String getLocalCacheKey();

    /**
     * 刷新本地缓存
     *
     * @param message
     */
    void initLocalCache(LocalCacheRefreshMessage message);
}
