package com.train.bpm.api;

import com.train.framework.bpm.api.BpmProcessInstanceApi;
import com.train.framework.bpm.dto.BpmProcessInstanceCreateReqDTO;
import org.springframework.stereotype.Service;

@Service
public class BpmProcessInstanceApiImpl implements BpmProcessInstanceApi {
    @Override
    public String createProcessInstance(Long userId, BpmProcessInstanceCreateReqDTO reqDTO) {
        return "";
    }
}
