package com.train.train.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.train.framework.dict.utils.TransUtils;
import lombok.AllArgsConstructor;
import com.train.framework.common.exception.ServerException;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.constant.TrainConstant;
import com.train.train.convert.CourseEnrollConvert;
import com.train.train.entity.CourseEnrollEntity;
import com.train.train.entity.CourseEntity;
import com.train.train.entity.StudentEntity;
import com.train.train.query.CourseEnrollQuery;
import com.train.train.service.CourseService;
import com.train.train.service.StudentService;
import com.train.train.vo.CourseEnrollVO;
import com.train.train.dao.CourseEnrollDao;
import com.train.train.service.CourseEnrollService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程报名
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class CourseEnrollServiceImpl extends BaseServiceImpl<CourseEnrollDao, CourseEnrollEntity> implements CourseEnrollService {
    private final CourseService courseService;
    private final StudentService studentService;

    @Override
    public PageResult<CourseEnrollVO> page(CourseEnrollQuery query) {
        IPage<CourseEnrollEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        List<CourseEnrollVO> list = CourseEnrollConvert.INSTANCE.convertList(page.getRecords());
        return new PageResult<>(TransUtils.transList(list, CourseEnrollVO.class), page.getTotal());
    }

    private LambdaQueryWrapper<CourseEnrollEntity> getWrapper(CourseEnrollQuery query) {
        LambdaQueryWrapper<CourseEnrollEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StringUtils.isNotBlank(query.getCourseCode()), CourseEnrollEntity::getCourseCode, query.getCourseCode());
        wrapper.eq(StringUtils.isNotBlank(query.getTeacherCode()), CourseEnrollEntity::getTeacherCode, query.getTeacherCode());
        wrapper.eq(StringUtils.isNotBlank(query.getEnrollStatus()), CourseEnrollEntity::getEnrollStatus, query.getEnrollStatus());
        wrapper.eq(query.getStudentId() != null && query.getStudentId() != 0, CourseEnrollEntity::getStudentId, query.getStudentId());
        return wrapper;
    }

    @Override
    public void save(CourseEnrollVO vo) {
        Validator.validateNotEmpty(vo.getCourseCode(), "课程编码不能为空");
        Validator.validateNotNull(vo.getCourseCode(), "学员不能为空");
        CourseEnrollEntity entity = CourseEnrollConvert.INSTANCE.convert(vo);
        // 获取学生
        StudentEntity studentEntity = studentService.getById(vo.getStudentId());
        if (studentEntity == null || !StrUtil.equals(studentEntity.getStatus(), TrainConstant.STUDENT_STATUS_NORMAL)) {
            throw new ServerException("学员不存在或状态不正常");
        }
        // 获取课程
        CourseEntity courseEntity = courseService.getByCourseCode(vo.getCourseCode());
        if (courseEntity == null || !StrUtil.equals(courseEntity.getCourseStatus(), TrainConstant.COURSE_STATUS_NORMAL)) {
            throw new ServerException("课程不存在或状态不正常");
        }
        CourseEnrollEntity exist = baseMapper.selectOne(Wrappers.lambdaQuery(CourseEnrollEntity.class)
                .eq(CourseEnrollEntity::getCourseCode, vo.getCourseCode())
                .eq(CourseEnrollEntity::getStudentId, vo.getStudentId()), false);
        if (exist != null && !StrUtil.equals(exist.getEnrollStatus(), TrainConstant.COURSE_ENROLL_STATUS_CANCEL)) {
            throw new ServerException("该学生已报名该课程");
        }
        if (exist != null && StrUtil.equals(exist.getEnrollStatus(), TrainConstant.COURSE_ENROLL_STATUS_CANCEL)) {
            exist.setEnrollStatus(TrainConstant.COURSE_ENROLL_STATUS_NORMAL);
            baseMapper.updateById(exist);
            return;
        }

        entity.setEnrollStatus(TrainConstant.COURSE_ENROLL_STATUS_NORMAL);
        entity.setTeacherCode(courseEntity.getTeacherCode());
        baseMapper.insert(entity);
    }

    @Override
    public void update(CourseEnrollVO vo) {
        CourseEnrollEntity entity = CourseEnrollConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public void updateEnrollStatus(Long id, String enrollStatus) {
        if (id == null || StrUtil.isBlank(enrollStatus)) {
            return;
        }
        CourseEnrollEntity entity = new CourseEnrollEntity();
        entity.setId(id);
        entity.setEnrollStatus(enrollStatus);
        updateById(entity);
    }

}