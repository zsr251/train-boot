package com.train.system.service.impl;

import com.train.framework.security.user.UserDetail;
import com.train.system.dao.SysRoleDao;
import com.train.system.enums.UserStatusEnum;
import com.train.system.service.SysMenuService;
import com.train.system.service.SysOrgService;
import com.train.system.service.SysUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 用户 UserDetails 信息
 */
@Service
@AllArgsConstructor
public class SysUserDetailsServiceImpl implements SysUserDetailsService {
    private final SysMenuService sysMenuService;
    private final SysOrgService sysOrgService;
    private final SysRoleDao sysRoleDao;

    @Override
    public UserDetails getUserDetails(UserDetail userDetail) {
        // 账号不可用
        if (userDetail.getStatus() == UserStatusEnum.DISABLE.getValue()) {
            userDetail.setEnabled(false);
        }

        // 用户权限列表
        Set<String> authoritySet = sysMenuService.getUserAuthority(userDetail);

        // 用户角色编码列表
        List<String> roleCodeList = sysRoleDao.geRoleCodeByUserId(userDetail.getId());
        roleCodeList.forEach(roleCode -> authoritySet.add("ROLE_" + roleCode));

        userDetail.setAuthoritySet(authoritySet);

        return userDetail;
    }
}
