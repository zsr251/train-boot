package com.train.train.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.train.framework.common.exception.ServerException;
import com.train.framework.common.utils.JsonUtils;
import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.constant.TrainConstant;
import com.train.train.convert.ClassCourseConvert;
import com.train.train.entity.ClassCourseEntity;
import com.train.train.entity.CourseEntity;
import com.train.train.query.ClassCourseQuery;
import com.train.train.service.CourseService;
import com.train.train.service.LessonTimetableService;
import com.train.train.vo.ClassCourseVO;
import com.train.train.dao.ClassCourseDao;
import com.train.train.service.ClassCourseService;
import com.train.train.vo.ScheduleRuleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 班级课程
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class ClassCourseServiceImpl extends BaseServiceImpl<ClassCourseDao, ClassCourseEntity> implements ClassCourseService {
    private final CourseService courseService;
    private final LessonTimetableService lessonTimetableService;

    @Override
    public PageResult<ClassCourseVO> page(ClassCourseQuery query) {
        IPage<ClassCourseEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(ClassCourseConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<ClassCourseEntity> getWrapper(ClassCourseQuery query) {
        LambdaQueryWrapper<ClassCourseEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StringUtils.isNotBlank(query.getClassCode()), ClassCourseEntity::getClassCode, query.getClassCode());
        wrapper.eq(StringUtils.isNotBlank(query.getCourseCode()), ClassCourseEntity::getCourseCode, query.getCourseCode());
        wrapper.eq(StringUtils.isNotBlank(query.getScheduleStatus()), ClassCourseEntity::getScheduleStatus, query.getScheduleStatus());
        return wrapper;
    }

    @Override
    public void save(ClassCourseVO vo) {
        Validator.validateNotNull(vo.getClassCode(), "班级编码不能为空");
        Validator.validateNotNull(vo.getCourseCode(), "课程编码不能为空");
        ClassCourseEntity exist = baseMapper.selectOne(Wrappers.lambdaQuery(ClassCourseEntity.class)
                .eq(ClassCourseEntity::getClassCode, vo.getClassCode())
                .eq(ClassCourseEntity::getCourseCode, vo.getCourseCode()));
        if (exist != null) {
            throw new ServerException("该班级已关联该课程");
        }
        CourseEntity courseEntity = courseService.getByCourseCode(vo.getCourseCode());
        if (courseEntity == null || !StrUtil.equals(courseEntity.getCourseStatus(), TrainConstant.COURSE_STATUS_NORMAL)) {
            throw new ServerException("课程不存在或状态不正常");
        }
        ClassCourseEntity entity = ClassCourseConvert.INSTANCE.convert(vo);
        if (entity.getPlanHours() == null) {
            entity.setPlanHours(courseEntity.getCourseHourTotal());
        }
        if (entity.getCourseHourOnce() == null) {
            entity.setCourseHourOnce(courseEntity.getCourseHourOnce());
        }
        if (StrUtil.isBlank(entity.getTeacherCode())) {
            entity.setTeacherCode(courseEntity.getTeacherCode());
        }
        entity.setScheduleStatus(TrainConstant.SCHEDULE_STATUS_WAIT);

        baseMapper.insert(entity);
    }

    @Override
    public void update(ClassCourseVO vo) {
        Validator.validateNotNull(vo.getId(), "班级课程ID不能为空");
        // 不允许修改班级编码和课程编码
        vo.setCourseCode(null).setClassCode(null);
        ScheduleRuleVO scheduleRuleVO = vo.getScheduleRuleVO();
        if (scheduleRuleVO != null) {
            vo.setScheduleRule(JsonUtils.toJsonString(scheduleRuleVO));
        }
        ClassCourseEntity entity = ClassCourseConvert.INSTANCE.convert(vo);
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public ClassCourseVO getVOById(Long id) {
        ClassCourseEntity entity = getById(id);
        if (entity == null) {
            throw new ServerException("班级课程不存在");
        }
        ClassCourseVO vo = ClassCourseConvert.INSTANCE.convert(entity);
        ScheduleRuleVO ruleVO = JsonUtils.parseObject(entity.getScheduleRule(), ScheduleRuleVO.class);
        if (ruleVO == null){
            ruleVO = new ScheduleRuleVO();
        }
        vo.setScheduleRuleVO(ruleVO);
        return vo;
    }

    @Override
    public void scheduleCourse(ClassCourseVO vo) {
        Validator.validateNotNull(vo.getId(), "班级课程ID不能为空");
        Validator.validateNotNull(vo.getScheduleRuleVO(), "排课规则不能为空");
        ClassCourseEntity classCourse = baseMapper.selectById(vo.getId());
        if (classCourse == null) {
            throw new ServerException("班级课程不存在");
        }
        if (StrUtil.equals(classCourse.getScheduleStatus(), TrainConstant.SCHEDULE_STATUS_ING)) {
            throw new ServerException("班级课程正在排课中");
        }
        String oldScheduleStatus = classCourse.getScheduleStatus();
        String scheduleRule = JsonUtils.toJsonString(vo.getScheduleRuleVO());
        vo.setScheduleStatus(TrainConstant.SCHEDULE_STATUS_ING).setScheduleRule(scheduleRule);
        // 保存数据 变更为 排课中
        update(vo);
        classCourse.setScheduleStatus(TrainConstant.SCHEDULE_STATUS_ING);
        classCourse.setClassroomCode(vo.getClassroomCode());
        classCourse.setPlanHours(vo.getPlanHours());
        classCourse.setCourseHourOnce(vo.getCourseHourOnce());
        classCourse.setScheduleRule(scheduleRule);
        // 更新数据
        ClassCourseEntity up = new ClassCourseEntity();
        up.setId(classCourse.getId());
        try {
            // 进行排课
            Integer n = lessonTimetableService.scheduleCourse(classCourse);
            // 状态变成 已排课
            up.setScheduleHours(n);
            up.setScheduleStatus(TrainConstant.SCHEDULE_STATUS_DONE);
            baseMapper.updateById(up);
        } catch (Exception e) {
            log.error("排课失败", e);
            // 状态变成 未排课
            up.setScheduleStatus(oldScheduleStatus);
            baseMapper.updateById(up);
            throw e;
        }
    }

    @Override
    public void scheduleCourse(Long classCourseId) {
        Validator.validateNotNull(classCourseId, "班级课程ID不能为空");
        ClassCourseEntity classCourse = baseMapper.selectById(classCourseId);
        if (classCourse == null) {
            throw new ServerException("班级课程不存在");
        }
        if (StrUtil.equals(classCourse.getScheduleStatus(), TrainConstant.SCHEDULE_STATUS_ING)) {
            throw new ServerException("班级课程正在排课中");
        }
        String oldScheduleStatus = classCourse.getScheduleStatus();
        ClassCourseEntity up = new ClassCourseEntity();
        up.setId(classCourse.getId());
        up.setScheduleStatus(TrainConstant.SCHEDULE_STATUS_ING);
        // 保存数据 变更为 排课中
        baseMapper.updateById(up);
        try {
            // 进行排课
            Integer n = lessonTimetableService.scheduleCourse(classCourse);
            // 状态变成 已排课
            up.setScheduleHours(n);
            up.setScheduleStatus(TrainConstant.SCHEDULE_STATUS_DONE);
            baseMapper.updateById(up);
        } catch (Exception e) {
            log.error("排课失败", e);
            // 状态变成 未排课
            up.setScheduleStatus(oldScheduleStatus);
            baseMapper.updateById(up);
            throw e;
        }
    }

    @Override
    public void resetSchedule(Long classCourseId) {
        ClassCourseEntity classCourse = baseMapper.selectById(classCourseId);
        if (classCourse == null) {
            throw new ServerException("班级课程不存在");
        }
        // 如果状态是未排课，直接返回
        if (StrUtil.equals(classCourse.getScheduleStatus(), TrainConstant.SCHEDULE_STATUS_WAIT)) {
            return;
        }
        // 删除非已完成的排课记录 返回不能删除的已排课时数
        Integer scheduleHours = lessonTimetableService.scheduleReset(classCourseId);
        // 设置状态未 未排课
        ClassCourseEntity up = new ClassCourseEntity();
        up.setId(classCourseId);
        up.setScheduleHours(scheduleHours);
        up.setScheduleStatus(TrainConstant.SCHEDULE_STATUS_WAIT);
        baseMapper.updateById(up);
    }

}