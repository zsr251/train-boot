package com.train.system.convert;

import com.train.system.entity.SysDictDataEntity;
import com.train.system.vo.SysDictDataVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysDictDataConvert {
    SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

    SysDictDataVO convert(SysDictDataEntity entity);

    SysDictDataEntity convert(SysDictDataVO vo);
    
    List<SysDictDataVO> convertList(List<SysDictDataEntity> list);

}
