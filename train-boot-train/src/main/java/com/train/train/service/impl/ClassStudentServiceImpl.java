package com.train.train.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.train.framework.common.exception.ServerException;
import com.train.framework.common.utils.PageResult;
import com.train.framework.dict.utils.TransUtils;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.constant.TrainConstant;
import com.train.train.convert.ClassStudentConvert;
import com.train.train.dao.ClassStudentDao;
import com.train.train.entity.ClassStudentEntity;
import com.train.train.query.ClassStudentQuery;
import com.train.train.service.ClassStudentService;
import com.train.train.service.CourseEnrollService;
import com.train.train.vo.ClassStudentVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 班级学员
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class ClassStudentServiceImpl extends BaseServiceImpl<ClassStudentDao, ClassStudentEntity> implements ClassStudentService {
    private final CourseEnrollService courseEnrollService;

    @Override
    public PageResult<ClassStudentVO> page(ClassStudentQuery query) {
        IPage<ClassStudentEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        List<ClassStudentVO> list = ClassStudentConvert.INSTANCE.convertList(page.getRecords());
        return new PageResult<>(TransUtils.transList(list, ClassStudentVO.class), page.getTotal());
    }

    private LambdaQueryWrapper<ClassStudentEntity> getWrapper(ClassStudentQuery query) {
        LambdaQueryWrapper<ClassStudentEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StringUtils.isNotBlank(query.getClassCode()), ClassStudentEntity::getClassCode, query.getClassCode());
        wrapper.eq(query.getStudentId() != null && query.getStudentId() != 0, ClassStudentEntity::getStudentId, query.getStudentId());
        wrapper.eq(StringUtils.isNotBlank(query.getStatus()), ClassStudentEntity::getStatus, query.getStatus());
        return wrapper;
    }

    @Override
    public void save(ClassStudentVO vo) {
        Validator.validateNotNull(vo.getStudentId(), "学员ID不能为空");
        Validator.validateNotEmpty(vo.getClassCode(), "班级编码不能为空");
        // 判断是否重复
        ClassStudentEntity exist = baseMapper.selectOne(Wrappers.lambdaQuery(ClassStudentEntity.class).eq(ClassStudentEntity::getStudentId, vo.getStudentId())
                .eq(ClassStudentEntity::getClassCode, vo.getClassCode()));
        if (exist != null && !StrUtil.equals(exist.getStatus(), TrainConstant.CLASS_STUDENT_STATUS_QUIT)) {
            throw new ServerException("该学员已在班级中");
        }
        courseEnrollService.updateEnrollStatus(vo.getCourseEnrollId(), TrainConstant.COURSE_ENROLL_STATUS_ASSIGN);
        if (exist != null && StrUtil.equals(exist.getStatus(), TrainConstant.CLASS_STUDENT_STATUS_QUIT)) {
            ClassStudentEntity up = new ClassStudentEntity();
            up.setId(exist.getId());
            up.setEnterTime(vo.getEnterTime() == null ? LocalDateTime.now() : vo.getEnterTime());
            up.setStatus(TrainConstant.CLASS_STUDENT_STATUS_NORMAL);
            baseMapper.updateById(up);
            return;
        }

        ClassStudentEntity entity = ClassStudentConvert.INSTANCE.convert(vo);
        entity.setEnterTime(vo.getEnterTime() == null ? LocalDateTime.now() : vo.getEnterTime());
        entity.setStatus(TrainConstant.CLASS_STUDENT_STATUS_NORMAL);

        baseMapper.insert(entity);
    }

    @Override
    public void update(ClassStudentVO vo) {
        ClassStudentEntity entity = ClassStudentConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}