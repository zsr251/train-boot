package com.train.system.convert;

import com.train.framework.datapermission.dto.DataPermissionDTO;
import com.train.system.entity.SysMenuDataPermissionEntity;
import com.train.system.vo.SysMenuDataPermissionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 数据权限
*
* @author jasonzhu 
* @since 1.0.0 2024-12-06
*/
@Mapper
public interface SysMenuDataPermissionConvert {
    SysMenuDataPermissionConvert INSTANCE = Mappers.getMapper(SysMenuDataPermissionConvert.class);

    SysMenuDataPermissionEntity convert(SysMenuDataPermissionVO vo);

    SysMenuDataPermissionVO convert(SysMenuDataPermissionEntity entity);

    List<SysMenuDataPermissionVO> convertList(List<SysMenuDataPermissionEntity> list);

    List<DataPermissionDTO> convertList2(List<SysMenuDataPermissionEntity> list);

}