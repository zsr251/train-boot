package com.train.storage.properties;

import lombok.Data;

/**
 * 七牛云存储配置项
 *
 */
@Data
public class QiniuStorageProperties {
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
