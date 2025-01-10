package com.train.system.convert;

import com.train.email.config.EmailConfig;
import com.train.system.entity.SysMailConfigEntity;
import com.train.system.vo.SysMailConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 邮件配置
 *

 */
@Mapper
public interface SysMailConfigConvert {
    SysMailConfigConvert INSTANCE = Mappers.getMapper(SysMailConfigConvert.class);

    SysMailConfigEntity convert(SysMailConfigVO vo);

    SysMailConfigVO convert(SysMailConfigEntity entity);

    List<SysMailConfigVO> convertList(List<SysMailConfigEntity> list);

    EmailConfig convert2(SysMailConfigEntity entity);

    List<EmailConfig> convertList2(List<SysMailConfigEntity> list);

}