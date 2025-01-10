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
import com.train.train.convert.ClassInfoConvert;
import com.train.train.entity.ClassInfoEntity;
import com.train.train.entity.CourseEntity;
import com.train.train.query.ClassInfoQuery;
import com.train.train.service.ClassCourseService;
import com.train.train.service.ClassStudentService;
import com.train.train.vo.ClassCourseVO;
import com.train.train.vo.ClassInfoVO;
import com.train.train.dao.ClassInfoDao;
import com.train.train.service.ClassInfoService;
import com.train.train.vo.ClassStudentVO;
import com.train.train.vo.CourseEnrollVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.core.ClassInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 班级
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class ClassInfoServiceImpl extends BaseServiceImpl<ClassInfoDao, ClassInfoEntity> implements ClassInfoService {
    private final CourseServiceImpl courseService;
    private final ClassCourseService classCourseService;
    private final ClassStudentService classStudentService;

    @Override
    public PageResult<ClassInfoVO> page(ClassInfoQuery query) {
        IPage<ClassInfoEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        List<ClassInfoVO> voList = TransUtils.transList(ClassInfoConvert.INSTANCE.convertList(page.getRecords()), ClassInfoVO.class);
        return new PageResult<>(voList, page.getTotal());
    }

    private LambdaQueryWrapper<ClassInfoEntity> getWrapper(ClassInfoQuery query) {
        LambdaQueryWrapper<ClassInfoEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StringUtils.isNotBlank(query.getClassCode()), ClassInfoEntity::getClassCode, query.getClassCode());
        wrapper.like(StringUtils.isNotBlank(query.getClassName()), ClassInfoEntity::getClassName, query.getClassName());
        wrapper.eq(StringUtils.isNotBlank(query.getCourseCode()), ClassInfoEntity::getCourseCode, query.getCourseCode());
        wrapper.eq(StringUtils.isNotBlank(query.getClassType()), ClassInfoEntity::getClassType, query.getClassType());
        wrapper.eq(StringUtils.isNotBlank(query.getManagerTeacher()), ClassInfoEntity::getManagerTeacher, query.getManagerTeacher());
        wrapper.eq(StringUtils.isNotBlank(query.getClassStatus()), ClassInfoEntity::getClassStatus, query.getClassStatus());
        return wrapper;
    }

    @Override
    public void save(ClassInfoVO vo) {
        Validator.validateNotNull(vo.getClassStatus(), "班级状态不能为空");
        // 判断班级编号是否重复
        Long n = baseMapper.selectCount(Wrappers.lambdaQuery(ClassInfoEntity.class).eq(ClassInfoEntity::getClassCode, vo.getClassCode()));
        if (n > 0) {
            throw new ServerException("班级编号已存在");
        }
        // 判断课程是否存在
        CourseEntity courseEntity = courseService.getByCourseCode(vo.getCourseCode());
        // 判断课程类型与班级类型是否一致
        if (courseEntity == null || !StrUtil.equals(courseEntity.getClassType(), vo.getClassType())) {
            throw new ServerException("课程不存在或班级类型不一致");
        }
        ClassInfoEntity entity = ClassInfoConvert.INSTANCE.convert(vo);
        baseMapper.insert(entity);
        // 增加班级课程
        ClassCourseVO classCourseVO = new ClassCourseVO().setClassCode(entity.getClassCode())
                .setCourseCode(entity.getCourseCode()).setPlanHours(courseEntity.getCourseHourTotal())
                .setScheduleStatus(TrainConstant.SCHEDULE_STATUS_WAIT);
        classCourseService.save(classCourseVO);
        // 增加班级学员
        List<CourseEnrollVO> studentList = vo.getClassStudents();
        if (studentList != null && !studentList.isEmpty()) {
            for (CourseEnrollVO courseEnrollVO : studentList) {
                try {
                    classStudentService.save(new ClassStudentVO().setClassCode(entity.getClassCode())
                            .setStudentId(courseEnrollVO.getStudentId()).setCourseEnrollId(courseEnrollVO.getId()));
                } catch (Exception e) {
                    log.error("班级添加学员失败", e);
                }
            }
        }
    }

    @Override
    public void update(ClassInfoVO vo) {
        ClassInfoEntity entity = ClassInfoConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<String> getNameList(List<String> classCodeList) {
        if (classCodeList == null || classCodeList.isEmpty()) {
            return null;
        }
        LambdaQueryWrapper<ClassInfoEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.in(ClassInfoEntity::getClassCode, classCodeList);
        return baseMapper.selectList(wrapper).stream().map(ClassInfoEntity::getClassName).toList();
    }
}