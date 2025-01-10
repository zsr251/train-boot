package com.train.system.api;

import cn.hutool.crypto.SecureUtil;
import com.train.framework.common.constant.RedisConstant;
import com.train.framework.datapermission.api.DataPermissionApi;
import com.train.framework.datapermission.dto.DataPermissionDTO;
import com.train.system.service.SysMenuDataPermissionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DataPermissionApiImpl implements DataPermissionApi {
    @Autowired
    private SysMenuDataPermissionService sysMenuDataPermissionService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<DataPermissionDTO> getDataPermissionList(Long userId, String[] components) {
        if (userId == null || components == null || components.length < 1) {
            return new ArrayList<>();
        }
        // 获取key
        String redisKey = RedisConstant.DATA_PERMISSION_ + userId + ":" + SecureUtil.md5(String.join(",", components));
        List<DataPermissionDTO> dtoList = (List<DataPermissionDTO>) redisTemplate.opsForValue().get(redisKey);
        if (dtoList != null) {
            return dtoList;
        }
        dtoList = sysMenuDataPermissionService.getListByUserId(userId, List.of(components));
        if (dtoList != null){
            redisTemplate.opsForValue().set(redisKey, dtoList, 10 * 60, TimeUnit.SECONDS);
        }
        return dtoList;
    }
}
