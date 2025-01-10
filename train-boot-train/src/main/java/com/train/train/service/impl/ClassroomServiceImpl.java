package com.train.train.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.convert.ClassroomConvert;
import com.train.train.entity.ClassroomEntity;
import com.train.train.query.ClassroomQuery;
import com.train.train.vo.ClassroomVO;
import com.train.train.dao.ClassroomDao;
import com.train.train.service.ClassroomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 教室
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class ClassroomServiceImpl extends BaseServiceImpl<ClassroomDao, ClassroomEntity> implements ClassroomService {

    @Override
    public PageResult<ClassroomVO> page(ClassroomQuery query) {
        IPage<ClassroomEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(ClassroomConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<ClassroomEntity> getWrapper(ClassroomQuery query){
        LambdaQueryWrapper<ClassroomEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotBlank(query.getClassroomName()), ClassroomEntity::getClassroomName, query.getClassroomName());
        wrapper.eq(StringUtils.isNotBlank(query.getClassroomCode()), ClassroomEntity::getClassroomCode, query.getClassroomCode());
        wrapper.eq(StringUtils.isNotBlank(query.getClassroomStatus()), ClassroomEntity::getClassroomStatus, query.getClassroomStatus());
        wrapper.eq(query.getIgnoreConflict() != null, ClassroomEntity::getIgnoreConflict, query.getIgnoreConflict());
        return wrapper;
    }

    @Override
    public void save(ClassroomVO vo) {
        ClassroomEntity entity = ClassroomConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(ClassroomVO vo) {
        ClassroomEntity entity = ClassroomConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<String> getNameList(List<String> classroomCodeList) {
        if (classroomCodeList == null || classroomCodeList.isEmpty()) {
            return null;
        }
        LambdaQueryWrapper<ClassroomEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.in(ClassroomEntity::getClassroomCode, classroomCodeList);
        return baseMapper.selectList(wrapper).stream().map(ClassroomEntity::getClassroomName).toList();
    }

}