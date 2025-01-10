package com.train.monitor.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Redis Info
 *
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cache {

    private String cacheKey;

    private Object cacheValue;
}
