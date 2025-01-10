package com.train.system.convert;

import com.train.sms.config.SmsConfig;
import com.train.system.entity.SysSmsConfigEntity;
import com.train.system.vo.SysSmsConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信配置
 *

 */
@Mapper
public interface SysSmsConfigConvert {
    SysSmsConfigConvert INSTANCE = Mappers.getMapper(SysSmsConfigConvert.class);

    SysSmsConfigEntity convert(SysSmsConfigVO vo);

    SysSmsConfigVO convert(SysSmsConfigEntity entity);

    List<SysSmsConfigVO> convertList(List<SysSmsConfigEntity> list);

    SmsConfig convert2(SysSmsConfigEntity entity);

    List<SmsConfig> convertList2(List<SysSmsConfigEntity> list);

}