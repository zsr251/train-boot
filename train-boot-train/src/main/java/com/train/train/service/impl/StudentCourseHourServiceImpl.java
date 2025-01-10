package com.train.train.service.impl;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.convert.CourseHourDepleteLogConvert;
import com.train.train.convert.StudentCourseHourConvert;
import com.train.train.dao.CourseHourDepleteLogDao;
import com.train.train.entity.CourseHourDepleteLogEntity;
import com.train.train.entity.StudentCourseHourEntity;
import com.train.train.query.StudentCourseHourQuery;
import com.train.train.vo.CourseHourDepleteVO;
import com.train.train.vo.StudentCourseHourVO;
import com.train.train.dao.StudentCourseHourDao;
import com.train.train.service.StudentCourseHourService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 学员课程课时
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class StudentCourseHourServiceImpl extends BaseServiceImpl<StudentCourseHourDao, StudentCourseHourEntity> implements StudentCourseHourService {
    private final CourseHourDepleteLogDao courseHourDepleteLogDao;

    @Override
    public PageResult<StudentCourseHourVO> page(StudentCourseHourQuery query) {
        IPage<StudentCourseHourEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(StudentCourseHourConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<StudentCourseHourEntity> getWrapper(StudentCourseHourQuery query) {
        LambdaQueryWrapper<StudentCourseHourEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StringUtils.isNotBlank(query.getCourseCode()), StudentCourseHourEntity::getCourseCode, query.getCourseCode())
                .eq(query.getStudentId() != null, StudentCourseHourEntity::getStudentId, query.getStudentId());
        return wrapper;
    }

    @Override
    public void save(StudentCourseHourVO vo) {
        StudentCourseHourEntity entity = StudentCourseHourConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(StudentCourseHourVO vo) {
        StudentCourseHourEntity entity = StudentCourseHourConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<StudentCourseHourVO> getByStudentId(Long studentId) {
        List<StudentCourseHourEntity> entityList = baseMapper.selectList(new LambdaQueryWrapper<StudentCourseHourEntity>().eq(StudentCourseHourEntity::getStudentId, studentId));

        return StudentCourseHourConvert.INSTANCE.convertList(entityList);
    }

    @Override
    public void depleteHour(CourseHourDepleteVO depleteVO) {
        // 单独消课
        Validator.validateNotEmpty(depleteVO.getCourseCode(), "课程编码不能为空");
        Validator.validateTrue(depleteVO.getCourseHour() != null && depleteVO.getCourseHour() > 0, "消课时间不能为空");
        Validator.validateNotNull(depleteVO.getStudentId(), "学员ID不能为空");
        Validator.validateNotEmpty(depleteVO.getRemark(), "单独消课必须填写原因");
        StudentCourseHourEntity entity = baseMapper.selectOne(new LambdaQueryWrapper<StudentCourseHourEntity>()
                .eq(StudentCourseHourEntity::getCourseCode, depleteVO.getCourseCode())
                .eq(StudentCourseHourEntity::getStudentId, depleteVO.getStudentId()));
        CourseHourDepleteLogEntity logEntity = CourseHourDepleteLogConvert.INSTANCE.convert(depleteVO);
        logEntity.setDepleteTime(LocalDateTime.now());
        // 如果没有记录 则创建
        if (entity == null) {
            logEntity.setCourseHourBefore(0).setCourseHourAfter(-depleteVO.getCourseHour());
            entity = new StudentCourseHourEntity().setStudentId(depleteVO.getStudentId()).setCourseCode(depleteVO.getCourseCode())
                    .setCourseHourDeplete(depleteVO.getCourseHour())
                    .setCourseHourLeft(-depleteVO.getCourseHour());
            baseMapper.insert(entity);
        } else {
            logEntity.setCourseHourBefore(entity.getCourseHourLeft()).setCourseHourAfter(entity.getCourseHourLeft() - depleteVO.getCourseHour());
            entity.setCourseHourDeplete(entity.getCourseHourDeplete() + depleteVO.getCourseHour())
                    .setCourseHourLeft(entity.getCourseHourLeft() - depleteVO.getCourseHour());
            baseMapper.updateById(entity);
        }
        courseHourDepleteLogDao.insert(logEntity);
    }

}