package com.train.framework.security.mobile;

/**
 * 手机短信登录，验证码效验
 *

 */
public interface MobileVerifyCodeService {

    boolean verifyCode(String mobile, String code);
}
