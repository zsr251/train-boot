package com.train.system.service;

import com.train.system.vo.*;
import com.train.system.vo.*;

/**
 * 权限认证服务
 *
 */
public interface SysAuthService {

    /**
     * 账号密码登录
     *
     * @param login 登录信息
     */
    SysUserTokenVO loginByAccount(SysAccountLoginVO login);

    /**
     * 登录APP
     * @param login 登录信息
     * @return
     */
    SysUserTokenVO loginApp(SysAccountLoginVO login);
    /**
     * 手机短信登录
     *
     * @param login 登录信息
     */
    SysUserTokenVO loginByMobile(SysMobileLoginVO login);

    /**
     * 第三方登录
     *
     * @param login 登录信息
     */
    SysUserTokenVO loginByThird(SysThirdCallbackVO login);

    /**
     * 发送手机验证码
     *
     * @param mobile 手机号
     */
    boolean sendCode(String mobile);

    /**
     * 根据刷新Token，获取AccessToken
     *
     * @param refreshToken refreshToken
     */
    AccessTokenVO getAccessToken(String refreshToken);

    /**
     * 退出登录
     *
     * @param accessToken accessToken
     */
    void logout(String accessToken);
}
