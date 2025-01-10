package com.train.system.convert;

import com.train.system.entity.SysMailLogEntity;
import com.train.system.vo.SysMailLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 邮件日志
 *

 */
@Mapper
public interface SysMailLogConvert {
    SysMailLogConvert INSTANCE = Mappers.getMapper(SysMailLogConvert.class);

    SysMailLogEntity convert(SysMailLogVO vo);

    SysMailLogVO convert(SysMailLogEntity entity);

    List<SysMailLogVO> convertList(List<SysMailLogEntity> list);

}