package com.train.sms;

import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;
import com.train.framework.common.exception.ServerException;
import com.train.sms.config.SmsConfig;

import java.util.Map;

/**
 * 七牛云短信
 *
 */
public class QiniuSmsStrategy implements SmsStrategy {
    private final SmsConfig smsConfig;
    private final SmsManager smsManager;

    public QiniuSmsStrategy(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;

        Auth auth = Auth.create(smsConfig.getAccessKey(), smsConfig.getSecretKey());
        smsManager = new SmsManager(auth);
    }

    @Override
    public void send(String mobile, Map<String, String> params) {
        try {
            Response response = smsManager.sendSingleMessage(smsConfig.getTemplateId(), mobile, params);

            // 是否发送成功
            if (!response.isOK()) {
                throw new ServerException(response.error);
            }
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }
}
