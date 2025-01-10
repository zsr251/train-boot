package com.train.system.service;

import me.zhyd.oauth.model.AuthUser;
import com.train.framework.mybatis.service.BaseService;
import com.train.system.entity.SysThirdLoginEntity;
import com.train.system.vo.SysThirdLoginVO;

import java.util.List;

/**
 * 第三方登录
 *
 */
public interface SysThirdLoginService extends BaseService<SysThirdLoginEntity> {

    List<SysThirdLoginVO> listByUserId(Long userId);

    void unBind(Long userId, String openType);

    void bind(Long userId, String openType, AuthUser authUser);

    /**
     * 根据第三方登录类型和openId，查询用户Id
     *
     * @param openType 第三方登录类型
     * @param openId   第三方用户唯一标识
     * @return 用户Id
     */
    Long getUserIdByOpenTypeAndOpenId(String openType, String openId);

}