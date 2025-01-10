package com.train.system.api;

import com.train.framework.common.api.ParamsApi;
import com.train.system.service.SysParamsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ParamsApiImpl implements ParamsApi {
    private final SysParamsService paramsService;

    @Override
    public String getString(String paramKey) {
        try {
            return paramsService.getString(paramKey);
        } catch (Exception e) {
            log.warn("获取参数异常：{} 原因：{}", paramKey, e.getMessage());
            return null;
        }
    }

    @Override
    public Integer getInt(String paramKey) {
        try {
            return paramsService.getInt(paramKey);
        } catch (Exception e) {
            log.warn("获取参数异常：{} 原因：{}", paramKey, e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean getBoolean(String paramKey) {
        try {
            return paramsService.getBoolean(paramKey);
        } catch (Exception e) {
            log.warn("获取参数异常：{} 原因：{}", paramKey, e.getMessage());
            return null;
        }
    }

    @Override
    public <T> T getJSONObject(String paramKey, Class<T> valueType) {
        try {
            return paramsService.getJSONObject(paramKey, valueType);
        } catch (Exception e) {
            log.warn("获取参数异常：{} 原因：{}", paramKey, e.getMessage());
            return null;
        }
    }

    @Override
    public List<String> getHolidayList() {
        List<String> list = null;
        try {
            list = paramsService.getHolidayList();
        } catch (Exception e) {
            log.warn("获取节假日失败:" + e.getMessage());
        }
        return list == null ? new ArrayList<>() : list;
    }
}
