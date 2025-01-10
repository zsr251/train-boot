package com.train.train.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.train.framework.dict.utils.TransUtils;
import lombok.AllArgsConstructor;
import com.train.framework.common.exception.ServerException;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.convert.CourseConvert;
import com.train.train.entity.CourseEntity;
import com.train.train.query.CourseQuery;
import com.train.train.vo.CourseVO;
import com.train.train.dao.CourseDao;
import com.train.train.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class CourseServiceImpl extends BaseServiceImpl<CourseDao, CourseEntity> implements CourseService {

    @Override
    public PageResult<CourseVO> page(CourseQuery query) {
        IPage<CourseEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        List<CourseVO> voList = TransUtils.transList(CourseConvert.INSTANCE.convertList(page.getRecords()), CourseVO.class);
        return new PageResult<>(voList, page.getTotal());
    }

    private LambdaQueryWrapper<CourseEntity> getWrapper(CourseQuery query) {
        LambdaQueryWrapper<CourseEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StringUtils.isNotBlank(query.getCourseCode()), CourseEntity::getCourseCode, query.getCourseCode());
        wrapper.like(StringUtils.isNotBlank(query.getCourseName()), CourseEntity::getCourseName, query.getCourseName());
        wrapper.eq(StringUtils.isNotBlank(query.getTeacherCode()), CourseEntity::getTeacherCode, query.getTeacherCode());
        wrapper.eq(StringUtils.isNotEmpty(query.getCourseStatus()), CourseEntity::getCourseStatus, query.getCourseStatus());
        wrapper.eq(StringUtils.isNotEmpty(query.getClassType()), CourseEntity::getClassType, query.getClassType());

        return wrapper;
    }

    @Override
    public void save(CourseVO vo) {
        CourseEntity entity = CourseConvert.INSTANCE.convert(vo);
        Long n = baseMapper.selectCount(Wrappers.lambdaQuery(CourseEntity.class).eq(CourseEntity::getCourseCode, entity.getCourseCode()));
        if (n > 0) {
            throw new ServerException("课程编号已存在");
        }
        baseMapper.insert(entity);
    }

    @Override
    public void update(CourseVO vo) {
        CourseEntity entity = CourseConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<String> getNameList(List<String> courseCodeList) {
        if (courseCodeList == null || courseCodeList.isEmpty()) {
            return null;
        }
        LambdaQueryWrapper<CourseEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.in(CourseEntity::getCourseCode, courseCodeList);
        return baseMapper.selectList(wrapper).stream().map(CourseEntity::getCourseName).toList();
    }

    @Override
    public CourseEntity getByCourseCode(String courseCode) {
        if (StringUtils.isNotBlank(courseCode)) {
            return baseMapper.getByCourseCode(courseCode);
        }
        return null;
    }


}