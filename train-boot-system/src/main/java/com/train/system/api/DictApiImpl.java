package com.train.system.api;

import com.train.framework.dict.api.DictApi;
import com.train.framework.dict.dto.DictDTO;
import com.train.system.service.SysDictTypeService;
import com.train.system.vo.SysDictVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component("dictApi")
@AllArgsConstructor
public class DictApiImpl implements DictApi {
    private final SysDictTypeService sysDictTypeService;

    @Override
    public List<DictDTO> getDictList(String dictType) {
        SysDictVO vo = sysDictTypeService.getDictVO(dictType);
        if (vo == null || vo.getDataList() == null) {
            return new ArrayList<>();
        }
        return vo.getDataList().stream().map(data -> new DictDTO(data.getDictValue(), data.getDictLabel())).toList();
    }

}
