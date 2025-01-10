package com.train.train.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.train.framework.common.exception.ServerException;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.constant.LessonArrivalStatusEnum;
import com.train.train.constant.LessonMemberTypeEnum;
import com.train.train.constant.TrainConstant;
import com.train.train.convert.LessonStudentConvert;
import com.train.train.dao.ClassStudentDao;
import com.train.train.dao.LessonTimetableDao;
import com.train.train.dao.StudentDao;
import com.train.train.entity.ClassStudentEntity;
import com.train.train.entity.LessonStudentEntity;
import com.train.train.entity.LessonTimetableEntity;
import com.train.train.entity.StudentEntity;
import com.train.train.query.LessonStudentQuery;
import com.train.train.vo.LessonStudentVO;
import com.train.train.dao.LessonStudentDao;
import com.train.train.service.LessonStudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 课程计划学员
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class LessonStudentServiceImpl extends BaseServiceImpl<LessonStudentDao, LessonStudentEntity> implements LessonStudentService {
    private final LessonTimetableDao lessonTimetableDao;
    private final StudentDao studentDao;
    private final ClassStudentDao classStudentDao;

    @Override
    public PageResult<LessonStudentVO> page(LessonStudentQuery query) {
        IPage<LessonStudentEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(LessonStudentConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<LessonStudentEntity> getWrapper(LessonStudentQuery query) {
        LambdaQueryWrapper<LessonStudentEntity> wrapper = Wrappers.lambdaQuery();
        return wrapper;
    }

    @Override
    public void save(LessonStudentVO vo) {
        Validator.validateNotNull(vo.getLessonId(), "课程ID不能为空");
        Validator.validateNotNull(vo.getStudentId(), "学员ID不能为空");
        LessonTimetableEntity lesson = lessonTimetableDao.selectById(vo.getLessonId());
        if (lesson == null || !StrUtil.equals(lesson.getLessonStatus(), TrainConstant.LESSON_STATUS_WAIT)) {
            throw new ServerException("这节课不存在或状态不正常");
        }
        StudentEntity student = studentDao.selectById(vo.getStudentId());
        if (student == null || !StrUtil.equals(student.getStatus(), TrainConstant.STUDENT_STATUS_NORMAL)) {
            throw new ServerException("学员不存在或状态不正常");
        }
        Long n = baseMapper.selectCount(new LambdaQueryWrapper<LessonStudentEntity>().eq(LessonStudentEntity::getLessonId, vo.getLessonId())
                .eq(LessonStudentEntity::getStudentId, vo.getStudentId()));
        if (n > 0) {
            throw new ServerException("这节课已存在该学员");
        }
        LessonStudentEntity entity = new LessonStudentEntity();
        entity.setLessonId(vo.getLessonId());
        entity.setClassCourseId(lesson.getClassCourseId());
        entity.setClassCode(lesson.getClassCode());
        entity.setCourseCode(lesson.getCourseCode());
        entity.setClassroomCode(lesson.getClassroomCode());
        entity.setStudentId(vo.getStudentId());
        entity.setMemberType(vo.getMemberType());
        entity.setArrivalStatus(TrainConstant.LESSON_ARRIVAL_STATUS_WAIT);
        baseMapper.insert(entity);
    }

    @Override
    public void addAudition(LessonStudentVO vo) {
        Validator.validateNotNull(vo.getLessonId(), "课程ID不能为空");
        Validator.validateNotNull(vo.getStudentId(), "学员ID不能为空");
        LessonTimetableEntity lesson = lessonTimetableDao.selectById(vo.getLessonId());
        if (lesson == null || !StrUtil.equals(lesson.getLessonStatus(), TrainConstant.LESSON_STATUS_WAIT)) {
            throw new ServerException("这节课不存在或状态不正常");
        }
        StudentEntity student = studentDao.selectById(vo.getStudentId());
        if (student == null || !StrUtil.equals(student.getStatus(), TrainConstant.STUDENT_STATUS_NORMAL)) {
            throw new ServerException("学员不存在或状态不正常");
        }
        Long n = baseMapper.selectCount(new LambdaQueryWrapper<LessonStudentEntity>().eq(LessonStudentEntity::getLessonId, vo.getLessonId())
                .eq(LessonStudentEntity::getStudentId, vo.getStudentId()));
        if (n > 0) {
            throw new ServerException("这节课已存在该学员");
        }
        // 判断该学员是否是班级的成员
        Long m = classStudentDao.selectCount(new LambdaQueryWrapper<ClassStudentEntity>()
                .eq(ClassStudentEntity::getStudentId, vo.getStudentId())
                .eq(ClassStudentEntity::getClassCode, lesson.getClassCode())
                .eq(ClassStudentEntity::getStatus, TrainConstant.CLASS_STUDENT_STATUS_NORMAL));
        if (m > 0) {
            throw new ServerException("这节课已存在该正式学员");
        }
        LessonStudentEntity entity = new LessonStudentEntity();
        entity.setLessonId(vo.getLessonId());
        entity.setClassCourseId(lesson.getClassCourseId());
        entity.setClassCode(lesson.getClassCode());
        entity.setCourseCode(lesson.getCourseCode());
        entity.setClassroomCode(lesson.getClassroomCode());
        entity.setStudentId(vo.getStudentId());
        entity.setMemberType(TrainConstant.LESSON_MEMBER_TYPE_AUDITION);
        entity.setArrivalStatus(TrainConstant.LESSON_ARRIVAL_STATUS_WAIT);
        baseMapper.insert(entity);
    }

    @Override
    public void update(LessonStudentVO vo) {
        LessonStudentEntity entity = LessonStudentConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<LessonStudentVO> getLessonStudents(Long lessonId) {
        Validator.validateNotNull(lessonId, "课程ID不能为空");
        LessonTimetableEntity lesson = lessonTimetableDao.selectById(lessonId);
        if (lesson == null) {
            throw new ServerException("这节课不存在");
        }
        List<LessonStudentVO> voList = new ArrayList<>();
        // 获取
        List<LessonStudentEntity> studentList = baseMapper.selectList(new LambdaQueryWrapper<LessonStudentEntity>().eq(LessonStudentEntity::getLessonId, lessonId));
        // 如果不是等待状态 则直接返回 lesson 成员
        if (!StrUtil.equals(lesson.getLessonStatus(), TrainConstant.LESSON_STATUS_WAIT)) {
            return wrapVO(LessonStudentConvert.INSTANCE.convertList(studentList));
        }

        Set<Long> studentIdSet = studentList.stream().map(LessonStudentEntity::getStudentId).collect(Collectors.toSet());
        // 获取班级的所有正常成员
        List<ClassStudentEntity> classStudentList = classStudentDao.selectList(new LambdaQueryWrapper<ClassStudentEntity>().eq(ClassStudentEntity::getClassCode, lesson.getClassCode())
                .eq(ClassStudentEntity::getStatus, TrainConstant.CLASS_STUDENT_STATUS_NORMAL));
        List<LessonStudentVO> sList = classStudentList.stream().filter(item -> !studentIdSet.contains(item.getStudentId()))
                .map(item -> new LessonStudentVO().setLessonId(lessonId).setStudentId(item.getStudentId())
                        .setMemberType(TrainConstant.LESSON_MEMBER_TYPE_STUDENT).setArrivalStatus(TrainConstant.LESSON_ARRIVAL_STATUS_WAIT)).toList();
        voList.addAll(sList);
        if (!studentList.isEmpty()) {
            voList.addAll(LessonStudentConvert.INSTANCE.convertList(studentList));
        }
        return wrapVO(voList);
    }

    /**
     * 包装vo 翻译学员ID、成员类型、到课状态
     *
     * @param voList
     * @return
     */
    private List<LessonStudentVO> wrapVO(List<LessonStudentVO> voList) {
        if (CollUtil.isEmpty(voList)) {
            return voList;
        }
        Set<Long> studentIdSet = voList.stream().map(LessonStudentVO::getStudentId).collect(Collectors.toSet());
        Map<Long, String> studentMap = studentDao.selectBatchIds(studentIdSet)
                .stream().collect(Collectors.toMap(StudentEntity::getId, StudentEntity::getStudentName));
        voList.forEach(vo -> {
            vo.setStudentName(studentMap.get(vo.getStudentId()));
            vo.setMemberTypeLabel(LessonMemberTypeEnum.getByCode(vo.getMemberType()).getLabel());
            vo.setArrivalStatusLabel(LessonArrivalStatusEnum.getByCode(vo.getArrivalStatus()).getLabel());
        });
        return voList;
    }

}