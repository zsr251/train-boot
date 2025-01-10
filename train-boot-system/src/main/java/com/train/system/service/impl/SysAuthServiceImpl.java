package com.train.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.train.framework.common.api.SmsApi;
import com.train.framework.common.cache.RedisCache;
import com.train.framework.common.constant.Constant;
import com.train.framework.common.constant.RedisConstant;
import com.train.framework.common.exception.ServerException;
import com.train.framework.security.cache.TokenStoreCache;
import com.train.framework.security.crypto.Sm2Util;
import com.train.framework.security.mobile.MobileAuthenticationToken;
import com.train.framework.security.third.ThirdAuthenticationToken;
import com.train.framework.security.third.ThirdLogin;
import com.train.framework.security.user.UserDetail;
import com.train.system.enums.LoginOperationEnum;
import com.train.system.service.*;
import com.train.system.vo.*;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 权限认证服务
 */
@Service
@AllArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {
    private final SysCaptchaService sysCaptchaService;
    private final TokenStoreCache tokenStoreCache;
    private final AuthenticationManager authenticationManager;
    private final SysLogLoginService sysLogLoginService;
    private final SysUserService sysUserService;
    private final SysUserTokenService sysUserTokenService;
    private final SmsApi smsApi;
    private final RedisCache redisCache;

    @Override
    public SysUserTokenVO loginByAccount(SysAccountLoginVO login) {
        // 验证码效验
        boolean flag = sysCaptchaService.validate(login.getKey(), login.getCaptcha());
        if (!flag) {
            // 保存登录日志
            sysLogLoginService.save(login.getUsername(), Constant.FAIL, LoginOperationEnum.CAPTCHA_FAIL.getValue());

            throw new ServerException("验证码错误");
        }
        if (StrUtil.isBlank(login.getUsername()) || StrUtil.isBlank(login.getPassword())) {
            throw new ServerException("用户名或密码错误");
        }
        checkCanLogin(login.getUsername());
        Authentication authentication;
        try {
            // 用户认证
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), Sm2Util.decrypt(login.getPassword())));
        } catch (BadCredentialsException e) {
            handleLoginErr(login.getUsername());
            throw new ServerException("用户名或密码错误");
        }
        handleLoginSuccess(login.getUsername());
        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // 生成 accessToken
        SysUserTokenVO userTokenVO = sysUserTokenService.createToken(user.getId());

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);

        return userTokenVO;
    }

    @Override
    public SysUserTokenVO loginApp(SysAccountLoginVO login) {
        if (StrUtil.isBlank(login.getUsername()) || StrUtil.isBlank(login.getPassword())) {
            throw new ServerException("用户名或密码错误");
        }
        checkCanLogin(login.getUsername());
        Authentication authentication;
        try {
            // 用户认证
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        } catch (BadCredentialsException e) {
            handleLoginErr(login.getUsername());
            throw new ServerException("用户名或密码错误");
        }
        handleLoginSuccess(login.getUsername());
        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // 生成 accessToken
        SysUserTokenVO userTokenVO = sysUserTokenService.createToken(user.getId());

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);

        return userTokenVO;
    }

    /**
     * 判定登录锁定时间
     *
     * @param username 用户名
     */
    public void checkCanLogin(String username) {
        Long expire = redisCache.getExpire(RedisConstant.LOGIN_LOCK_ + username);
        if (expire > 0) {
            throw new ServerException("账号被锁定，请" + expire + "秒后重试");
        }
    }

    /**
     * 登录错误处理
     *
     * @param username 用户名
     */
    public void handleLoginErr(String username) {
        Long errCount = redisCache.increment(RedisConstant.LOGIN_ERR_ + username);
        if (errCount == 1L) {
            redisCache.expire(RedisConstant.LOGIN_ERR_ + username, 12 * 60 * 60);
        }
        if (errCount == 3L) {
            // 错误3次 锁定1分钟
            redisCache.set(RedisConstant.LOGIN_LOCK_ + username, errCount, 60L);
            throw new ServerException("账号被锁定，请" + 60 + "秒后重试");
        }
        if (errCount == 4L) {
            // 错误4次 锁定5分钟
            redisCache.set(RedisConstant.LOGIN_LOCK_ + username, errCount, 300L);
            throw new ServerException("账号被锁定，请" + 300 + "秒后重试");
        }
        if (errCount == 5L) {
            // 错误5次 锁定10分钟
            redisCache.set(RedisConstant.LOGIN_LOCK_ + username, errCount, 600L);
            throw new ServerException("账号被锁定，请" + 600 + "秒后重试");
        }
        if (errCount == 6L) {
            // 错误6次 锁定60分钟
            redisCache.set(RedisConstant.LOGIN_LOCK_ + username, errCount, 3600L);
            throw new ServerException("账号被锁定，请" + 3600 + "秒后重试");
        }
        if (errCount == 7L) {
            // 错误7次 锁定24小时
            redisCache.set(RedisConstant.LOGIN_LOCK_ + username, errCount, 24 * 3600L);
            throw new ServerException("账号被锁定，请" + (24 * 3600) + "秒后重试");
        }
    }

    /**
     * 登录成功处理
     *
     * @param username
     */
    public void handleLoginSuccess(String username) {
        redisCache.delete(RedisConstant.LOGIN_ERR_ + username);
        redisCache.delete(RedisConstant.LOGIN_LOCK_ + username);
    }

    @Override
    public SysUserTokenVO loginByMobile(SysMobileLoginVO login) {
        Authentication authentication;
        try {
            // 用户认证
            authentication = authenticationManager.authenticate(
                    new MobileAuthenticationToken(login.getMobile(), login.getCode()));
        } catch (BadCredentialsException e) {
            throw new ServerException("手机号或验证码错误");
        }

        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // 生成 accessToken
        SysUserTokenVO userTokenVO = sysUserTokenService.createToken(user.getId());

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);

        return userTokenVO;
    }

    @Override
    public SysUserTokenVO loginByThird(SysThirdCallbackVO login) {
        Authentication authentication;
        try {
            // 转换对象
            ThirdLogin thirdLogin = BeanUtil.copyProperties(login, ThirdLogin.class);

            // 用户认证
            authentication = authenticationManager.authenticate(new ThirdAuthenticationToken(thirdLogin));
        } catch (BadCredentialsException e) {
            throw new ServerException("第三方登录失败");
        }

        // 用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();

        // 生成 accessToken
        SysUserTokenVO userTokenVO = sysUserTokenService.createToken(user.getId());

        // 保存用户信息到缓存
        tokenStoreCache.saveUser(userTokenVO.getAccessToken(), user);

        return userTokenVO;
    }

    @Override
    public boolean sendCode(String mobile) {
        // 生成6位验证码
        String code = RandomUtil.randomNumbers(6);

        SysUserVO user = sysUserService.getByMobile(mobile);
        if (user == null) {
            throw new ServerException("手机号未注册");
        }

        // 发送短信
        return smsApi.sendCode(mobile, "code", code);
    }

    @Override
    public AccessTokenVO getAccessToken(String refreshToken) {
        SysUserTokenVO token = sysUserTokenService.refreshToken(refreshToken);

        // 封装 AccessToken
        AccessTokenVO accessToken = new AccessTokenVO();
        accessToken.setAccessToken(token.getAccessToken());
        accessToken.setAccessTokenExpire(token.getAccessTokenExpire());

        return accessToken;
    }

    @Override
    public void logout(String accessToken) {
        // 用户信息
        UserDetail user = tokenStoreCache.getUser(accessToken);

        // 删除用户信息
        tokenStoreCache.deleteUser(accessToken);

        // Token过期
        sysUserTokenService.expireToken(user.getId());

        // 删除数据权限
        redisCache.delete(redisCache.keys(RedisConstant.DATA_PERMISSION_ + user.getId() + ":*"));

        // 保存登录日志
        sysLogLoginService.save(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGOUT_SUCCESS.getValue());
    }
}
