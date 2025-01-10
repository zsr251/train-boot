package com.train.framework.mq.stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.train.framework.mq.message.AbstractRedisMessage;

/**
 * Redis Stream Message 抽象类
 *
 */
public abstract class AbstractStreamMessage extends AbstractRedisMessage {

    /**
     * 获得 Redis Stream Key
     *
     * @return Channel
     */
    @JsonIgnore
    public abstract String getStreamKey();

}
