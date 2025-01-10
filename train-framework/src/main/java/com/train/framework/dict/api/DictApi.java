package com.train.framework.dict.api;

import com.train.framework.dict.dto.DictDTO;

import java.util.List;

/**
 * 字典接口
 */
public interface DictApi {
    /**
     * 获取字典列表
     * @param dictType
     * @return
     */
    List<DictDTO> getDictList(String dictType);

}
