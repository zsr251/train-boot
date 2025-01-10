package com.train.storage.properties;

import lombok.Data;

/**
 * 本地存储配置项
 *
 */
@Data
public class LocalStorageProperties {
    /**
     * 本地存储路径
     */
    private String path;
    /**
     * 资源起始路径
     */
    private String url = "upload";
}
