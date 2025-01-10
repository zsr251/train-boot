package com.train.framework.mq.refresh;

import com.train.framework.mq.pubsub.AbstractChannelMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地缓存刷新消息
 * @author zhushengran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LocalCacheRefreshMessage extends AbstractChannelMessage {
    /**
     * 本地缓存标识
     */
    private String localCacheKey;
    /**
     * 其他负载
     */
    private Map<String,Object> params;

    public LocalCacheRefreshMessage(){}
    public LocalCacheRefreshMessage(@NonNull String localCacheKey){
        this.localCacheKey = localCacheKey;
        this.params = new HashMap<>(0);
    }
    public LocalCacheRefreshMessage(@NonNull String localCacheKey, Map<String,Object> params){
        this.localCacheKey = localCacheKey;
        if (params == null){
            params = new HashMap<>(0);
        }
        this.params = params;
    }
    @Override
    public String getChannel() {
        return "system.local.refresh";
    }

}
