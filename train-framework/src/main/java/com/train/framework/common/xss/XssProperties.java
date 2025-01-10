package com.train.framework.common.xss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * XSS 配置项
 *

 */
@Data
@ConfigurationProperties(prefix = "train.xss")
public class XssProperties {
    /**
     * 是否开启 XSS
     */
    private boolean enabled;
    /**
     * 排除的URL列表
     */
    private List<String> excludeUrls = Collections.emptyList();
}
