package com.train.train.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.train.framework.common.api.ParamsApi;
import com.train.framework.common.exception.ServerException;
import com.train.framework.common.utils.JsonUtils;
import com.train.framework.common.utils.PageResult;
import com.train.framework.datapermission.utils.DataPermissionUtils;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.constant.LessonStatusEnum;
import com.train.train.constant.TrainConstant;
import com.train.train.convert.LessonStudentConvert;
import com.train.train.convert.LessonTimetableConvert;
import com.train.train.dao.*;
import com.train.train.entity.*;
import com.train.train.query.LessonTimetableQuery;
import com.train.train.query.TimetableQuery;
import com.train.train.service.LessonTimetableService;
import com.train.train.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 课程计划
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Slf4j
@Service
@AllArgsConstructor
public class LessonTimetableServiceImpl extends BaseServiceImpl<LessonTimetableDao, LessonTimetableEntity> implements LessonTimetableService {
    private final ClassCourseDao classCourseDao;
    private final ClassInfoDao classInfoDao;
    private final CourseDao courseDao;
    private final TeacherDao teacherDao;
    private final ClassroomDao classroomDao;
    private final ClassStudentDao classStudentDao;
    private final LessonStudentDao lessonStudentDao;
    private final ParamsApi paramsApi;
    private final StudentCourseHourDao studentCourseHourDao;
    private final CourseHourDepleteLogDao courseHourDepleteLogDao;

    @Override
    public PageResult<LessonTimetableVO> page(LessonTimetableQuery query) {

        IPage<LessonTimetableEntity> page = baseMapper.selectPage(getPage(query), DataPermissionUtils.wrapDataPermission("timetable", getWrapper(query)));

        return new PageResult<>(wrapVO(LessonTimetableConvert.INSTANCE.convertList(page.getRecords())), page.getTotal());
    }

    @Override
    public List<LessonTimetableVO> today(LessonTimetableQuery query) {
        if (query.getStudentId() == null || query.getStudentId() < 1) {
            query.setLessonDate(LocalDate.now());
            LambdaQueryWrapper<LessonTimetableEntity> wrapper = getWrapper(query);
            wrapper.orderByAsc(LessonTimetableEntity::getLessonBeginTime);
            List<LessonTimetableVO> voList = LessonTimetableConvert.INSTANCE.convertList(baseMapper.selectList(wrapper));
            return wrapVO(voList);
        }
        Set<String> classCodeSet = classStudentDao.selectList(new LambdaQueryWrapper<ClassStudentEntity>().eq(ClassStudentEntity::getStudentId, query.getStudentId())
                        .eq(ClassStudentEntity::getStatus, TrainConstant.CLASS_STUDENT_STATUS_NORMAL).select(ClassStudentEntity::getClassCode))
                .stream().map(ClassStudentEntity::getClassCode).collect(Collectors.toSet());
        Set<Long> otherLesson = lessonStudentDao.selectList(new LambdaQueryWrapper<LessonStudentEntity>()
                        .eq(LessonStudentEntity::getStudentId, query.getStudentId())
                        .notIn(CollUtil.isNotEmpty(classCodeSet), LessonStudentEntity::getClassCode, classCodeSet).select(LessonStudentEntity::getLessonId))
                .stream()
                .map(LessonStudentEntity::getLessonId)
                .collect(Collectors.toSet());
        List<LessonTimetableVO> voList = new ArrayList<>();
        if (CollUtil.isNotEmpty(classCodeSet)) {
            List<LessonTimetableEntity> list = baseMapper.selectList(
                    new LambdaQueryWrapper<LessonTimetableEntity>()
                            .in(LessonTimetableEntity::getLessonStatus, TrainConstant.LESSON_STATUS_WAIT, TrainConstant.LESSON_STATUS_DONE)
                            .eq(LessonTimetableEntity::getLessonDate, LocalDate.now())
                            .in(LessonTimetableEntity::getClassCode, classCodeSet));
            voList.addAll(wrapVO(LessonTimetableConvert.INSTANCE.convertList(list)));
        }
        if (CollUtil.isNotEmpty(otherLesson)) {
            List<LessonTimetableEntity> list = baseMapper.selectList(
                    new LambdaQueryWrapper<LessonTimetableEntity>()
                            .in(LessonTimetableEntity::getLessonStatus, TrainConstant.LESSON_STATUS_WAIT, TrainConstant.LESSON_STATUS_DONE)
                            .eq(LessonTimetableEntity::getLessonDate, LocalDate.now())
                            .in(LessonTimetableEntity::getId, otherLesson));
            voList.addAll(wrapVO(LessonTimetableConvert.INSTANCE.convertList(list)));
        }
        return voList;
    }

    @Override
    public LessonTimetableVO getVOById(Long id) {
        LessonTimetableEntity entity = getById(id);
        if (entity == null) {
            throw new ServerException("课程计划不存在");
        }
        return wrapVO(List.of(LessonTimetableConvert.INSTANCE.convert(entity))).get(0);
    }

    private LambdaQueryWrapper<LessonTimetableEntity> getWrapper(LessonTimetableQuery query) {
        LambdaQueryWrapper<LessonTimetableEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StrUtil.isNotBlank(query.getClassCode()), LessonTimetableEntity::getClassCode, query.getClassCode());
        wrapper.eq(StrUtil.isNotBlank(query.getCourseCode()), LessonTimetableEntity::getCourseCode, query.getCourseCode());
        wrapper.eq(StrUtil.isNotBlank(query.getTeacherCode()), LessonTimetableEntity::getTeacherCode, query.getTeacherCode());
        wrapper.eq(StrUtil.isNotBlank(query.getClassroomCode()), LessonTimetableEntity::getClassroomCode, query.getClassroomCode());
        wrapper.eq(StrUtil.isNotBlank(query.getLessonStatus()), LessonTimetableEntity::getLessonStatus, query.getLessonStatus());
//        wrapper.eq(query.getLessonDate() == null && StrUtil.isBlank(query.getLessonStatus()), LessonTimetableEntity::getLessonStatus, TrainConstant.LESSON_STATUS_WAIT);
        wrapper.eq(query.getLessonDate() != null, LessonTimetableEntity::getLessonDate, query.getLessonDate());
        return wrapper;
    }

    @Override
    public void save(LessonTimetableVO vo) {
        LessonTimetableEntity entity = LessonTimetableConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(LessonTimetableVO vo) {
        LessonTimetableEntity entity = LessonTimetableConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public Integer scheduleCourse(Long classCourseId) {
        ClassCourseEntity classCourse = classCourseDao.selectById(classCourseId);
        if (classCourse == null) {
            throw new ServerException("班级课程不存在");
        }
        return scheduleCourse(classCourse);
    }

    @Override
    public Integer scheduleCourse(ClassCourseEntity classCourse) {
        if (classCourse == null || classCourse.getId() == null) {
            throw new ServerException("班级课程不存在");
        }
        if (StrUtil.isBlank(classCourse.getScheduleRule())) {
            throw new ServerException("排课规则不存在");
        }
        ClassInfoEntity classInfo = classInfoDao.getByClassCode(classCourse.getClassCode());
        if (classInfo == null) {
            throw new ServerException("班级不存在");
        }
        CourseEntity course = courseDao.getByCourseCode(classCourse.getCourseCode());
        if (course == null) {
            throw new ServerException("课程不存在");
        }
        TeacherEntity teacher = teacherDao.getByTeacherCode(course.getTeacherCode());
        if (teacher == null) {
            throw new ServerException("老师不存在");
        }
        if (classCourse.getCourseHourOnce() == null || classCourse.getCourseHourOnce() < 1) {
            throw new ServerException("每次课程至少一个课时");
        }
        ClassroomEntity targetClassroom = null;
        List<ClassroomEntity> classroomList = null;
        if (StringUtils.isNotBlank(classCourse.getClassroomCode())) {
            targetClassroom = classroomDao.getByClassroomCode(classCourse.getClassroomCode());
            if (targetClassroom == null) {
                throw new ServerException("指定教室不存在");
            }
            if (!StrUtil.equals(targetClassroom.getClassroomStatus(), TrainConstant.CLASSROOM_STATUS_NORMAL)) {
                throw new ServerException("指定教室状态不可用");
            }
        } else {
            classroomList = classroomDao.getByCapacity(classInfo.getCapacity());
        }
        ScheduleRuleVO scheduleRuleVO = JsonUtils.parseObject(classCourse.getScheduleRule(), ScheduleRuleVO.class);
        if (scheduleRuleVO == null || StrUtil.isBlank(scheduleRuleVO.getScheduleType())) {
            throw new ServerException("排课失败，请先配置排课规则");
        }
        if(CollUtil.isEmpty(scheduleRuleVO.getTimeSection())){
            throw new ServerException("排课失败，请先配置时间段");
        }
        // 获取该课程已经排过的课时
        Integer existLessonHours = baseMapper.getLessonExistCourseHour(classCourse.getId());
        if (targetClassroom != null) {
            // 指定了教室进行排课
            List<LessonTimetableEntity> timetables = scheduleCourse(scheduleRuleVO, classCourse, classInfo, targetClassroom, course, teacher, existLessonHours);
            if (CollUtil.isNotEmpty(timetables)) {
                saveBatch(timetables);
            }
            return getScheduleHours(existLessonHours, timetables);
        }
        List<String> errMsg = new ArrayList<>();
        // 没有指定教室进行排课，循环对教室进行排课
        for (ClassroomEntity classroom : classroomList) {
            if (StrUtil.equals(classInfo.getClassType(), TrainConstant.CLASS_TYPE_ONE_TO_ONE)
                    && classroom.getCapacity() > 1) {
                log.warn("班级：{} 为一对一班级，教室：{} 不是一对一教室，不允许分配", classInfo.getClassName(), classroom.getClassroomCode());
                errMsg.add("教室：" + classroom.getClassroomName() + " 不是一对一教室，不允许分配");
                continue;
            }
            try {
                List<LessonTimetableEntity> timetables = scheduleCourse(scheduleRuleVO, classCourse, classInfo, classroom, course, teacher, existLessonHours);
                if (CollUtil.isNotEmpty(timetables)) {
                    saveBatch(timetables);
                }
                return getScheduleHours(existLessonHours, timetables);
            } catch (Exception e) {
                log.error("教室：" + classroom.getClassroomName() + " 排班失败。原因：" + e.getMessage(), e);
                errMsg.add("教室：" + classroom.getClassroomName() + " 排班失败。原因：" + e.getMessage());
            }
        }
        throw new ServerException("排课失败，原因：\r\n" + CollUtil.join(errMsg, "；\r\n"));
    }


    private List<LessonTimetableEntity> scheduleCourse(ScheduleRuleVO scheduleRuleVO, ClassCourseEntity classCourse, ClassInfoEntity classInfo, ClassroomEntity classroom, CourseEntity course, TeacherEntity teacher, Integer existLessonHours) {
        List<Date> selectDays = new ArrayList<>();
        List<String> holidayList = paramsApi.getHolidayList();
        int planeHours = classCourse.getPlanHours() - existLessonHours;
        int oneHours = classCourse.getCourseHourOnce();
        if (TrainConstant.SCHEDULE_TYPE_FREQUENCY.equals(scheduleRuleVO.getScheduleType())) {
            int times = planeHours / oneHours + (planeHours % oneHours == 0 ? 0 : 1);
            int days = times / scheduleRuleVO.getTimeSection().size() + (times % scheduleRuleVO.getTimeSection().size() == 0 ? 0 : 1);
            Date curDay = null;
            Date endDay = DateUtil.parse(scheduleRuleVO.getEndDate());
            for (int i = 0; i < days; i++) {
                if (curDay == null) {
                    curDay = DateUtil.parse(scheduleRuleVO.getBeginDate());
                    if (!isMatchDayRule(curDay, scheduleRuleVO.getFrequencyType(), scheduleRuleVO.getFrequencyAppoint())) {
                        curDay = getNextDay(curDay, scheduleRuleVO.getFrequencyType(), scheduleRuleVO.getFrequencyAppoint());
                    }
                } else {
                    curDay = getNextDay(curDay, scheduleRuleVO.getFrequencyType(), scheduleRuleVO.getFrequencyAppoint());
                }
                if (curDay == null) {
                    log.warn("未找到下一次日期，退出循环");
                    break;
                }
                if (endDay != null && DateUtil.compare(curDay, endDay) > 0) {
                    log.warn("已到达指定结束日期，退出循环");
                    break;
                }
                if (isExcludeDay(curDay, scheduleRuleVO.getExcludeType(), scheduleRuleVO.getExcludeDays(), holidayList)) {
                    --i;
                    continue;
                }
                selectDays.add(curDay);
            }
        } else {
            if (CollUtil.isEmpty(scheduleRuleVO.getAppointDays())) {
                throw new ServerException("排课规则错误，未指定排课时间");
            }
            scheduleRuleVO.getAppointDays().stream().distinct().map(day -> DateUtil.parse(day, "yyyy-MM-dd")).forEach(selectDays::add);
        }
        if (selectDays.isEmpty()) {
            throw new ServerException("排课规则错误，未匹配到排课日期");
        }
        String classroomCode = classroom.getIgnoreConflict() == 1 ? null : classroom.getClassroomCode();
        List<LessonTimetableEntity> existLessonList = baseMapper.getTimetableForSchedule(selectDays, classInfo.getClassCode(), classroomCode, teacher.getTeacherCode());
        Map<String, List<LessonTimetableEntity>> timetableMap = existLessonList.stream().collect(Collectors.groupingBy(a -> DateUtil.format(a.getLessonDate(), "yyyy-MM-dd")));
        LessonTimetableEntity conflictTimetable = getConflictTimetable(timetableMap, selectDays, scheduleRuleVO.getTimeSection());
        if (conflictTimetable != null) {
            log.error("排课冲突：" + JSONUtil.toJsonStr(conflictTimetable));
            String errMsg = "排课冲突，日期：" + DateUtil.format(conflictTimetable.getLessonDate(), "yyyy-MM-dd")
                    + "，时间：" + DateUtil.format(conflictTimetable.getLessonBeginTime(), "HH:mm") + "-" + DateUtil.format(conflictTimetable.getLessonEndTime(), "HH:mm");
            if (conflictTimetable.getClassCode().equals(classInfo.getClassCode())) {
                errMsg += "，班级：" + classInfo.getClassName();
            }
            if (conflictTimetable.getClassroomCode().equals(classroom.getClassroomCode())) {
                errMsg += "，教室：" + classroom.getClassroomName();
            }
            if (conflictTimetable.getTeacherCode().equals(teacher.getTeacherCode())) {
                errMsg += "，老师：" + teacher.getTeacherName();
            }
            throw new ServerException(errMsg);
        }
        List<LessonTimetableEntity> lessonList = new ArrayList<>();
        for (Date selectDay : selectDays) {
            String curDate = DateUtil.format(selectDay, "yyyy-MM-dd");
            for (String section : scheduleRuleVO.getTimeSection()) {
                LocalDateTime beginTime = DateUtil.parse(curDate + " " + section.split("-")[0], "yyyy-MM-dd HH:mm").toLocalDateTime();
                LocalDateTime endTime = DateUtil.parse(curDate + " " + section.split("-")[1], "yyyy-MM-dd HH:mm").toLocalDateTime();
                LessonTimetableEntity lesson = new LessonTimetableEntity();
                lesson.setClassCourseId(classCourse.getId());
                lesson.setClassCode(classInfo.getClassCode());
                lesson.setCourseCode(course.getCourseCode());
                lesson.setClassroomCode(classroom.getClassroomCode());
                lesson.setTeacherCode(teacher.getTeacherCode());
                lesson.setLessonDate(DateUtil.toLocalDateTime(selectDay));
                lesson.setLessonBeginTime(beginTime);
                lesson.setLessonEndTime(endTime);
                lesson.setCourseHour(classCourse.getCourseHourOnce());
                lesson.setLessonStatus(TrainConstant.LESSON_STATUS_WAIT);
                lessonList.add(lesson);
            }
        }
        return lessonList;
    }

    private Integer getScheduleHours(Integer existLessonHours, List<LessonTimetableEntity> newLessonList) {
        if (CollUtil.isNotEmpty(newLessonList)) {
            existLessonHours += newLessonList.stream().mapToInt(LessonTimetableEntity::getCourseHour).sum();
        }
        return existLessonHours;
    }

    @Override
    public Integer scheduleReset(Long classCourseId) {
        LambdaQueryWrapper<LessonTimetableEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LessonTimetableEntity::getClassCourseId, classCourseId)
                .ne(LessonTimetableEntity::getLessonStatus, TrainConstant.LESSON_STATUS_DONE);
        baseMapper.delete(wrapper);
        return baseMapper.getLessonDoneCourseHour(classCourseId);
    }

    @Override
    public LessonTimetableGroupShowVO getTeachersTimetable(Integer week) {
        Date curDay = DateUtil.offsetWeek(new Date(), week);
        Date startDay = DateUtil.beginOfWeek(curDay);
        Date endDay = DateUtil.endOfWeek(curDay);
        LessonTimetableGroupShowVO showVO = new LessonTimetableGroupShowVO().setWeek(week);
        showVO.setToday(DateUtil.format(new Date(), "yyyy-MM-dd")).setMonDay(DateUtil.format(startDay, "yyyy-MM-dd"))
                .setTueDay(DateUtil.format(DateUtil.offsetDay(startDay, 1), "yyyy-MM-dd"))
                .setWedDay(DateUtil.format(DateUtil.offsetDay(startDay, 2), "yyyy-MM-dd"))
                .setThuDay(DateUtil.format(DateUtil.offsetDay(startDay, 3), "yyyy-MM-dd"))
                .setFriDay(DateUtil.format(DateUtil.offsetDay(startDay, 4), "yyyy-MM-dd"))
                .setSatDay(DateUtil.format(DateUtil.offsetDay(startDay, 5), "yyyy-MM-dd"))
                .setSunDay(DateUtil.format(DateUtil.offsetDay(startDay, 6), "yyyy-MM-dd"));

        LambdaQueryWrapper<LessonTimetableEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.in(LessonTimetableEntity::getLessonStatus, TrainConstant.LESSON_STATUS_WAIT, TrainConstant.LESSON_STATUS_DONE);
        wrapper.between(LessonTimetableEntity::getLessonDate, startDay, endDay);
        List<LessonTimetableVO> voList = wrapVO(LessonTimetableConvert.INSTANCE.convertList(baseMapper.selectList(wrapper)));
        List<LessonTimetableGroupVO> groupVOList = new ArrayList<>();
        Map<String, List<LessonTimetableVO>> teacherMap = voList.stream().collect(Collectors.groupingBy(LessonTimetableVO::getTeacherCode));
        for (Map.Entry<String, List<LessonTimetableVO>> entry : teacherMap.entrySet()) {
            List<LessonTimetableVO> lessonList = entry.getValue();
            LessonTimetableGroupVO groupVO = new LessonTimetableGroupVO();
            groupVO.setGroupCode(entry.getKey()).setGroupName(lessonList.get(0).getTeacherName());
            wrapWeekInfo(groupVO, startDay, lessonList);
            groupVOList.add(groupVO);
        }
        return showVO.setVoList(groupVOList);
    }

    private LessonTimetableGroupVO wrapWeekInfo(LessonTimetableGroupVO groupVO, Date startDay, List<LessonTimetableVO> lessonList) {
        groupVO = groupVO == null ? new LessonTimetableGroupVO() : groupVO;
        lessonList = lessonList == null ? new ArrayList<>() : lessonList;
        Map<String, List<LessonTimetableVO>> lessonMap = lessonList.stream().collect(Collectors.groupingBy(lesson -> DateUtil.format(lesson.getLessonDate(), "yyyy-MM-dd")));
        String monDay = DateUtil.format(startDay, "yyyy-MM-dd");
        List<LessonTimetableVO> monList = lessonMap.get(monDay);
        if (CollUtil.isNotEmpty(monList)) {
            monList.sort(Comparator.comparing(LessonTimetableVO::getLessonBeginTime));
            groupVO.setMon(monList);
        } else {
            groupVO.setMon(new ArrayList<>());
        }
        String tueDay = DateUtil.format(DateUtil.offsetDay(startDay, 1), "yyyy-MM-dd");
        List<LessonTimetableVO> tueList = lessonMap.get(tueDay);
        if (CollUtil.isNotEmpty(tueList)) {
            tueList.sort(Comparator.comparing(LessonTimetableVO::getLessonBeginTime));
            groupVO.setTue(tueList);
        } else {
            groupVO.setTue(new ArrayList<>());
        }
        String wedDay = DateUtil.format(DateUtil.offsetDay(startDay, 2), "yyyy-MM-dd");
        List<LessonTimetableVO> wedList = lessonMap.get(wedDay);
        if (CollUtil.isNotEmpty(wedList)) {
            wedList.sort(Comparator.comparing(LessonTimetableVO::getLessonBeginTime));
            groupVO.setWed(wedList);
        } else {
            groupVO.setWed(new ArrayList<>());
        }
        String thuDay = DateUtil.format(DateUtil.offsetDay(startDay, 3), "yyyy-MM-dd");
        List<LessonTimetableVO> thuList = lessonMap.get(thuDay);
        if (CollUtil.isNotEmpty(thuList)) {
            thuList.sort(Comparator.comparing(LessonTimetableVO::getLessonBeginTime));
            groupVO.setThu(thuList);
        } else {
            groupVO.setThu(new ArrayList<>());
        }
        String friDay = DateUtil.format(DateUtil.offsetDay(startDay, 4), "yyyy-MM-dd");
        List<LessonTimetableVO> friList = lessonMap.get(friDay);
        if (CollUtil.isNotEmpty(friList)) {
            friList.sort(Comparator.comparing(LessonTimetableVO::getLessonBeginTime));
            groupVO.setFri(friList);
        } else {
            groupVO.setFri(new ArrayList<>());
        }
        String satDay = DateUtil.format(DateUtil.offsetDay(startDay, 5), "yyyy-MM-dd");
        List<LessonTimetableVO> satList = lessonMap.get(satDay);
        if (CollUtil.isNotEmpty(satList)) {
            satList.sort(Comparator.comparing(LessonTimetableVO::getLessonBeginTime));
            groupVO.setSat(satList);
        } else {
            groupVO.setSat(new ArrayList<>());
        }
        String sunDay = DateUtil.format(DateUtil.offsetDay(startDay, 6), "yyyy-MM-dd");
        List<LessonTimetableVO> sunList = lessonMap.get(sunDay);
        if (CollUtil.isNotEmpty(sunList)) {
            sunList.sort(Comparator.comparing(LessonTimetableVO::getLessonBeginTime));
            groupVO.setSun(sunList);
        } else {
            groupVO.setSun(new ArrayList<>());
        }
        return groupVO;
    }

    @Override
    public LessonTimetableGroupShowVO getClassroomsTimetable(Integer week) {
        Date curDay = DateUtil.offsetWeek(new Date(), week);
        Date startDay = DateUtil.beginOfWeek(curDay);
        Date endDay = DateUtil.endOfWeek(curDay);
        LessonTimetableGroupShowVO showVO = new LessonTimetableGroupShowVO().setWeek(week);
        showVO.setToday(DateUtil.format(new Date(), "yyyy-MM-dd")).setMonDay(DateUtil.format(startDay, "yyyy-MM-dd"))
                .setTueDay(DateUtil.format(DateUtil.offsetDay(startDay, 1), "yyyy-MM-dd"))
                .setWedDay(DateUtil.format(DateUtil.offsetDay(startDay, 2), "yyyy-MM-dd"))
                .setThuDay(DateUtil.format(DateUtil.offsetDay(startDay, 3), "yyyy-MM-dd"))
                .setFriDay(DateUtil.format(DateUtil.offsetDay(startDay, 4), "yyyy-MM-dd"))
                .setSatDay(DateUtil.format(DateUtil.offsetDay(startDay, 5), "yyyy-MM-dd"))
                .setSunDay(DateUtil.format(DateUtil.offsetDay(startDay, 6), "yyyy-MM-dd"));

        LambdaQueryWrapper<LessonTimetableEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.in(LessonTimetableEntity::getLessonStatus, TrainConstant.LESSON_STATUS_WAIT, TrainConstant.LESSON_STATUS_DONE);
        wrapper.between(LessonTimetableEntity::getLessonDate, startDay, endDay);
        List<LessonTimetableVO> voList = wrapVO(LessonTimetableConvert.INSTANCE.convertList(baseMapper.selectList(wrapper)));
        List<LessonTimetableGroupVO> groupVOList = new ArrayList<>();
        Map<String, List<LessonTimetableVO>> classroomMap = voList.stream().collect(Collectors.groupingBy(LessonTimetableVO::getClassroomCode));
        for (Map.Entry<String, List<LessonTimetableVO>> entry : classroomMap.entrySet()) {
            List<LessonTimetableVO> lessonList = entry.getValue();
            LessonTimetableGroupVO groupVO = new LessonTimetableGroupVO();
            groupVO.setGroupCode(entry.getKey()).setGroupName(lessonList.get(0).getClassroomName());
            wrapWeekInfo(groupVO, startDay, lessonList);
            groupVOList.add(groupVO);
        }
        return showVO.setVoList(groupVOList);
    }

    @Override
    public LessonTimetableAppointShowVO getAppointTimetable(TimetableQuery query) {
        Date curDay = DateUtil.offsetWeek(new Date(), query.getWeek());
        Date startDay = DateUtil.beginOfWeek(curDay);
        Date endDay = DateUtil.endOfWeek(curDay);
        LessonTimetableAppointShowVO showVO = new LessonTimetableAppointShowVO().setWeek(query.getWeek());
        showVO.setToday(DateUtil.format(new Date(), "yyyy-MM-dd")).setMonDay(DateUtil.format(startDay, "yyyy-MM-dd"))
                .setTueDay(DateUtil.format(DateUtil.offsetDay(startDay, 1), "yyyy-MM-dd"))
                .setWedDay(DateUtil.format(DateUtil.offsetDay(startDay, 2), "yyyy-MM-dd"))
                .setThuDay(DateUtil.format(DateUtil.offsetDay(startDay, 3), "yyyy-MM-dd"))
                .setFriDay(DateUtil.format(DateUtil.offsetDay(startDay, 4), "yyyy-MM-dd"))
                .setSatDay(DateUtil.format(DateUtil.offsetDay(startDay, 5), "yyyy-MM-dd"))
                .setSunDay(DateUtil.format(DateUtil.offsetDay(startDay, 6), "yyyy-MM-dd"));

        if ((query.getStudentId() == null || query.getStudentId() < 1) && !StrUtil.isAllBlank(query.getClassCode(), query.getTeacherCode(), query.getClassroomCode(), query.getCourseCode())) {
            LambdaQueryWrapper<LessonTimetableEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.in(LessonTimetableEntity::getLessonStatus, TrainConstant.LESSON_STATUS_WAIT, TrainConstant.LESSON_STATUS_DONE);
            wrapper.eq(StrUtil.isNotBlank(query.getClassCode()), LessonTimetableEntity::getClassCode, query.getClassCode());
            wrapper.eq(StrUtil.isNotBlank(query.getTeacherCode()), LessonTimetableEntity::getTeacherCode, query.getTeacherCode());
            wrapper.eq(StrUtil.isNotBlank(query.getClassroomCode()), LessonTimetableEntity::getClassroomCode, query.getClassroomCode());
            wrapper.eq(StrUtil.isNotBlank(query.getCourseCode()), LessonTimetableEntity::getCourseCode, query.getCourseCode());
            wrapper.between(LessonTimetableEntity::getLessonDate, startDay, endDay);
            List<LessonTimetableVO> voList = wrapVO(LessonTimetableConvert.INSTANCE.convertList(baseMapper.selectList(wrapper)));
            LessonTimetableGroupVO groupVO = wrapWeekInfo(null, startDay, voList);
            showVO.setMonSum(groupVO.getMon().size()).setTueSum(groupVO.getTue().size()).setWedSum(groupVO.getWed().size()).setThuSum(groupVO.getThu().size())
                    .setFriSum(groupVO.getFri().size()).setSatSum(groupVO.getSat().size()).setSunSum(groupVO.getSun().size());
            return showVO.setVoList(partitionToAppoint(groupVO));
        }
        if (query.getStudentId() == null || query.getStudentId() < 1) {
            throw new ServerException("请指定查询类型");
        }
        Set<String> classCodeSet = classStudentDao.selectList(new LambdaQueryWrapper<ClassStudentEntity>().eq(ClassStudentEntity::getStudentId, query.getStudentId())
                        .eq(ClassStudentEntity::getStatus, TrainConstant.CLASS_STUDENT_STATUS_NORMAL).select(ClassStudentEntity::getClassCode))
                .stream().map(ClassStudentEntity::getClassCode).collect(Collectors.toSet());
        Set<Long> otherLesson = lessonStudentDao.selectList(new LambdaQueryWrapper<LessonStudentEntity>()
                        .eq(LessonStudentEntity::getStudentId, query.getStudentId())
                        .notIn(CollUtil.isNotEmpty(classCodeSet), LessonStudentEntity::getClassCode, classCodeSet).select(LessonStudentEntity::getLessonId))
                .stream()
                .map(LessonStudentEntity::getLessonId)
                .collect(Collectors.toSet());
        List<LessonTimetableVO> voList = new ArrayList<>();
        if (CollUtil.isNotEmpty(classCodeSet)) {
            List<LessonTimetableEntity> list = baseMapper.selectList(
                    new LambdaQueryWrapper<LessonTimetableEntity>()
                            .in(LessonTimetableEntity::getLessonStatus, TrainConstant.LESSON_STATUS_WAIT, TrainConstant.LESSON_STATUS_DONE)
                            .between(LessonTimetableEntity::getLessonDate, startDay, endDay)
                            .in(LessonTimetableEntity::getClassCode, classCodeSet));
            voList.addAll(wrapVO(LessonTimetableConvert.INSTANCE.convertList(list)));
        }
        if (CollUtil.isNotEmpty(otherLesson)) {
            List<LessonTimetableEntity> list = baseMapper.selectList(
                    new LambdaQueryWrapper<LessonTimetableEntity>()
                            .in(LessonTimetableEntity::getLessonStatus, TrainConstant.LESSON_STATUS_WAIT, TrainConstant.LESSON_STATUS_DONE)
                            .between(LessonTimetableEntity::getLessonDate, startDay, endDay)
                            .in(LessonTimetableEntity::getId, otherLesson));
            voList.addAll(wrapVO(LessonTimetableConvert.INSTANCE.convertList(list)));
        }
        LessonTimetableGroupVO groupVO = wrapWeekInfo(null, startDay, voList);
        showVO.setMonSum(groupVO.getMon().size()).setTueSum(groupVO.getTue().size()).setWedSum(groupVO.getWed().size()).setThuSum(groupVO.getThu().size())
                .setFriSum(groupVO.getFri().size()).setSatSum(groupVO.getSat().size()).setSunSum(groupVO.getSun().size());
        return showVO.setVoList(partitionToAppoint(groupVO));
    }

    @Override
    public void lessonNamed(LessonNamedVO vo) {
        Validator.validateNotNull(vo.getLessonId(), "课程ID不能为空");
        LessonTimetableEntity lesson = getById(vo.getLessonId());
        if (lesson == null) {
            throw new ServerException("课程计划不存在");
        }
        if (!StrUtil.equals(lesson.getLessonStatus(), TrainConstant.LESSON_STATUS_WAIT)) {
            throw new ServerException("非等待状态不允许点名");
        }
        List<LessonStudentVO> voList = vo.getVoList() == null ? new ArrayList<>() : vo.getVoList();
        Map<Long, LessonStudentVO> voMap = voList.stream().collect(Collectors.toMap(LessonStudentVO::getStudentId, item -> item));

        List<LessonStudentEntity> studentEntityList = lessonStudentDao.selectList(new LambdaQueryWrapper<LessonStudentEntity>().eq(LessonStudentEntity::getLessonId, lesson.getId()));
        List<Long> existStudentIdList = studentEntityList.stream().map(LessonStudentEntity::getStudentId).toList();
        Set<Long> classStudentIdList = classStudentDao.selectList(new LambdaQueryWrapper<ClassStudentEntity>().eq(ClassStudentEntity::getClassCode, lesson.getClassCode()).eq(ClassStudentEntity::getStatus, TrainConstant.CLASS_STUDENT_STATUS_NORMAL))
                .stream().map(ClassStudentEntity::getStudentId).filter(studentId -> !existStudentIdList.contains(studentId)).collect(Collectors.toSet());
        List<LessonStudentVO> resList = new ArrayList();
        studentEntityList.forEach(a -> {
            LessonStudentVO v = voMap.get(a.getStudentId());
            if (v != null && Objects.equals(v.getId(), a.getId())) {
                resList.add(v);
            } else {
                resList.add(LessonStudentConvert.INSTANCE.convert(a));
            }
        });
        classStudentIdList.forEach(a -> {
            LessonStudentVO v = voMap.get(a);
            if (v == null) {
                resList.add(new LessonStudentVO().setStudentId(a).setMemberType(TrainConstant.LESSON_MEMBER_TYPE_STUDENT).setArrivalStatus(TrainConstant.LESSON_ARRIVAL_STATUS_WAIT));
            } else {
                resList.add(v);
            }
        });
        baseMapper.updateById(lesson.setLessonStatus(TrainConstant.LESSON_STATUS_DONE));
        if (CollUtil.isEmpty(resList)) {
            log.warn("未找到需要签到的学员 lessonId：{}", lesson.getId());
            return;
        }
        List<LessonStudentEntity> nList = new ArrayList<>();
        List<LessonStudentEntity> upList = new ArrayList<>();
        List<CourseHourDepleteLogEntity> depleteList = new ArrayList<>();
        resList.forEach(a -> {
            if (StrUtil.isBlank(a.getArrivalStatus()) || StrUtil.equals(a.getArrivalStatus(), TrainConstant.LESSON_ARRIVAL_STATUS_WAIT)) {
                a.setArrivalStatus(vo.getDefaultArrivalStatus());
            }
            if (!StrUtil.equalsAny(a.getArrivalStatus(), TrainConstant.LESSON_ARRIVAL_STATUS_ARRIVED, TrainConstant.LESSON_ARRIVAL_STATUS_DELAY)) {
                a.setDepleteTime(null);
                a.setCourseHour(null);
            } else {
                a.setDepleteTime(LocalDateTime.now());
                a.setCourseHour(a.getCourseHour() == null ? lesson.getCourseHour() : a.getCourseHour());
            }
            if (a.getId() == null) {
                nList.add(new LessonStudentEntity().setLessonId(lesson.getId()).setClassCourseId(lesson.getClassCourseId()).setClassCode(lesson.getClassCode())
                        .setCourseCode(lesson.getCourseCode()).setClassroomCode(lesson.getClassroomCode())
                        .setStudentId(a.getStudentId()).setMemberType(a.getMemberType()).setArrivalStatus(a.getArrivalStatus())
                        .setDepleteTime(a.getDepleteTime()).setCourseHour(a.getCourseHour()).setRemark("点名时创建"));
            } else {
                LessonStudentEntity up = new LessonStudentEntity();
                up.setId(a.getId());
                up.setArrivalStatus(a.getArrivalStatus()).setDepleteTime(a.getDepleteTime()).setCourseHour(a.getCourseHour());
                upList.add(up);
            }
            if (StrUtil.equals(a.getMemberType(), TrainConstant.LESSON_MEMBER_TYPE_STUDENT) && StrUtil.equalsAny(a.getArrivalStatus(), TrainConstant.LESSON_ARRIVAL_STATUS_ARRIVED, TrainConstant.LESSON_ARRIVAL_STATUS_DELAY)) {
                depleteList.add(new CourseHourDepleteLogEntity().setLessonId(lesson.getId()).setClassCourseId(lesson.getClassCourseId()).setTeacherCode(lesson.getTeacherCode())
                        .setClassCode(lesson.getClassCode()).setCourseCode(lesson.getCourseCode()).setClassroomCode(lesson.getClassroomCode())
                        .setLessonDate(lesson.getLessonDate()).setLessonBeginTime(lesson.getLessonBeginTime()).setLessonEndTime(lesson.getLessonEndTime())
                        .setStudentId(a.getStudentId()).setArrivalStatus(a.getArrivalStatus()).setCourseHour(a.getCourseHour()).setDepleteTime(a.getDepleteTime()));
            }
        });
        if (CollUtil.isNotEmpty(nList)) {
            lessonStudentDao.insert(nList);
        }
        if (CollUtil.isNotEmpty(upList)) {
            lessonStudentDao.updateById(upList);
        }
        depleteHour(depleteList);
    }

    @Override
    public void lessonReassign(LessonTimetableVO vo) {
        Validator.validateNotNull(vo.getId(), "课程计划ID不能为空");
        Validator.validateNotEmpty(vo.getTeacherCode(), "新授课老师必选");
        LessonTimetableEntity timetableEntity = getById(vo.getId());
        if (timetableEntity == null) {
            throw new ServerException("这节课不存在");
        }
        if (!StrUtil.equals(timetableEntity.getLessonStatus(), TrainConstant.LESSON_STATUS_WAIT)) {
            throw new ServerException("这节课不是等待状态");
        }
        if (StrUtil.equals(timetableEntity.getTeacherCode(), vo.getTeacherCode())) {
            return;
        }
        TeacherEntity teacher = teacherDao.getByTeacherCode(vo.getTeacherCode());
        if (teacher == null) {
            throw new ServerException("老师不存在");
        }
        List<LessonTimetableEntity> timetableList = baseMapper.selectList(new LambdaQueryWrapper<LessonTimetableEntity>().eq(LessonTimetableEntity::getTeacherCode, vo.getTeacherCode())
                .eq(LessonTimetableEntity::getLessonDate, timetableEntity.getLessonDate())
                .eq(LessonTimetableEntity::getLessonStatus, TrainConstant.LESSON_STATUS_WAIT));
        if (!CollUtil.isEmpty(timetableList)) {
            LocalDateTime curBeginTime = timetableEntity.getLessonBeginTime();
            LocalDateTime curEndTime = timetableEntity.getLessonEndTime();
            for (LessonTimetableEntity timetable : timetableList) {
                if (curEndTime.compareTo(timetable.getLessonBeginTime()) <= 0 || curBeginTime.compareTo(timetable.getLessonEndTime()) >= 0) {
                    continue;
                } else {
                    throw new ServerException("新授课老师上课时间冲突");
                }
            }
        }
        LessonTimetableEntity up = new LessonTimetableEntity();
        up.setId(timetableEntity.getId());
        up.setLessonStatus(TrainConstant.LESSON_STATUS_REASSIGN).setRemark("授课老师更换为：" + teacher.getTeacherName());
        baseMapper.updateById(up);
        LessonTimetableEntity newTimetable = new LessonTimetableEntity();
        newTimetable.setLessonStatus(TrainConstant.LESSON_STATUS_WAIT).setLessonDate(timetableEntity.getLessonDate())
                .setLessonBeginTime(timetableEntity.getLessonBeginTime()).setLessonEndTime(timetableEntity.getLessonEndTime())
                .setClassroomCode(timetableEntity.getClassroomCode()).setTeacherCode(vo.getTeacherCode())
                .setClassCourseId(timetableEntity.getClassCourseId()).setClassCode(timetableEntity.getClassCode())
                .setCourseCode(timetableEntity.getCourseCode()).setCourseHour(timetableEntity.getCourseHour())
                .setRemark("授课老师更换");
        baseMapper.insert(newTimetable);
    }

    @Override
    public void lessonCancel(Long id) {
        Validator.validateNotNull(id, "课程计划ID不能为空");
        LessonTimetableEntity timetableEntity = getById(id);
        if (timetableEntity == null) {
            throw new ServerException("这节课不存在");
        }
        if (!StrUtil.equals(timetableEntity.getLessonStatus(), TrainConstant.LESSON_STATUS_WAIT)) {
            throw new ServerException("这节课不是等待状态");
        }
        LessonTimetableEntity up = new LessonTimetableEntity();
        up.setId(id);
        up.setLessonStatus(TrainConstant.LESSON_STATUS_CANCEL);
        baseMapper.updateById(up);
    }

    private void depleteHour(List<CourseHourDepleteLogEntity> depleteList) {
        if (CollUtil.isEmpty(depleteList)) {
            return;
        }
        String courseCode = depleteList.get(0).getCourseCode();
        List<Long> studentIdList = depleteList.stream().map(CourseHourDepleteLogEntity::getStudentId).toList();
        Map<Long, StudentCourseHourEntity> hourEntityMap = studentCourseHourDao.selectList(new LambdaQueryWrapper<StudentCourseHourEntity>().eq(StudentCourseHourEntity::getCourseCode, courseCode)
                .in(StudentCourseHourEntity::getStudentId, studentIdList)).stream().collect(Collectors.toMap(StudentCourseHourEntity::getStudentId, a -> a));
        for (CourseHourDepleteLogEntity entity : depleteList) {
            StudentCourseHourEntity hour = hourEntityMap.get(entity.getStudentId());
            if (hour == null) {
                entity.setCourseHourBefore(0).setCourseHourAfter(-entity.getCourseHour());
                hour = new StudentCourseHourEntity().setCourseCode(courseCode).setStudentId(entity.getStudentId())
                        .setCourseHourDeplete(entity.getCourseHour()).setCourseHourLeft(-entity.getCourseHour());
                studentCourseHourDao.insert(hour);
            } else {
                entity.setCourseHourBefore(hour.getCourseHourLeft()).setCourseHourAfter(hour.getCourseHourLeft() - entity.getCourseHour());
                studentCourseHourDao.updateById(hour.setCourseHourDeplete(hour.getCourseHourDeplete() + entity.getCourseHour()).setCourseHourLeft(hour.getCourseHourLeft() - entity.getCourseHour()));
            }
        }
        courseHourDepleteLogDao.insert(depleteList);
    }


    List<LessonTimetableAppointVO> partitionToAppoint(LessonTimetableGroupVO groupVO) {
        List<LessonTimetableAppointVO> list = new ArrayList<>();
        LocalTime t8 = LocalTime.of(8, 0);
        LocalTime t12 = LocalTime.of(12, 0);
        LocalTime t18 = LocalTime.of(18, 0);
        LessonTimetableAppointVO l1 = new LessonTimetableAppointVO().setLabel("上午").setDesc("8:00 ~ 12:00");
        l1.setMon(groupVO.getMon().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t8) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t12) < 0).toList());
        l1.setThu(groupVO.getThu().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t8) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t12) < 0).toList());
        l1.setWed(groupVO.getWed().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t8) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t12) < 0).toList());
        l1.setTue(groupVO.getTue().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t8) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t12) < 0).toList());
        l1.setFri(groupVO.getFri().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t8) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t12) < 0).toList());
        l1.setSat(groupVO.getSat().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t8) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t12) < 0).toList());
        l1.setSun(groupVO.getSun().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t8) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t12) < 0).toList());
        list.add(l1);
        LessonTimetableAppointVO l2 = new LessonTimetableAppointVO().setLabel("下午").setDesc("12:00 ~ 18:00");
        l2.setMon(groupVO.getMon().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t12) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t18) < 0).toList());
        l2.setThu(groupVO.getThu().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t12) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t18) < 0).toList());
        l2.setWed(groupVO.getWed().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t12) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t18) < 0).toList());
        l2.setTue(groupVO.getTue().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t12) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t18) < 0).toList());
        l2.setFri(groupVO.getFri().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t12) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t18) < 0).toList());
        l2.setSat(groupVO.getSat().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t12) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t18) < 0).toList());
        l2.setSun(groupVO.getSun().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t12) >= 0 && t.getLessonBeginTime().toLocalTime().compareTo(t18) < 0).toList());
        list.add(l2);
        LessonTimetableAppointVO l3 = new LessonTimetableAppointVO().setLabel("晚上").setDesc("18:00 ~ 22:00");
        l3.setMon(groupVO.getMon().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t18) >= 0).toList());
        l3.setThu(groupVO.getThu().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t18) >= 0).toList());
        l3.setWed(groupVO.getWed().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t18) >= 0).toList());
        l3.setTue(groupVO.getTue().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t18) >= 0).toList());
        l3.setFri(groupVO.getFri().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t18) >= 0).toList());
        l3.setSat(groupVO.getSat().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t18) >= 0).toList());
        l3.setSun(groupVO.getSun().stream().filter(t -> t.getLessonBeginTime().toLocalTime().compareTo(t18) >= 0).toList());
        list.add(l3);
        return list;
    }

    private boolean isExcludeDay(Date curDay, String excludeType, List<String> excludeDays, List<String> holidayList) {
        String curDayStr = DateUtil.format(curDay, "yyyy-MM-dd");
        if (CollUtil.isNotEmpty(excludeDays) && excludeDays.contains(curDayStr)) {
            return true;
        }
        if ("holiday".equals(excludeType)) {
            return CollUtil.isNotEmpty(holidayList) && holidayList.contains(curDayStr);
        }
        return false;
    }

    private static boolean isMatchDayRule(Date curDay, String frequencyType, List<Integer> frequencyAppoint) {
        if (curDay == null) {
            return false;
        }
        switch (frequencyType) {
            case "daily":
                return true;
            case "weekly":
                int c = DateUtil.dayOfWeek(curDay) - 1;
                c = c == 0 ? 7 : c;
                return CollUtil.isNotEmpty(frequencyAppoint) && frequencyAppoint.contains(c);
            case "monthly":
                return CollUtil.isNotEmpty(frequencyAppoint) && frequencyAppoint.contains(DateUtil.dayOfMonth(curDay));
            default:
                return false;
        }
    }

    private static Date getNextDay(Date curDay, String frequencyType, List<Integer> frequencyAppoint) {
        switch (frequencyType) {
            case "daily":
                return DateUtil.offsetDay(curDay, CollUtil.isEmpty(frequencyAppoint) ? 1 : frequencyAppoint.get(0));
            case "weekly":
                if (CollUtil.isEmpty(frequencyAppoint)) return null;
                List<Integer> weekList = frequencyAppoint.stream().map(a -> a % 7).map(a -> a == 0 ? 7 : a).distinct().sorted().collect(Collectors.toList());
                int c = DateUtil.dayOfWeek(curDay) - 1;
                c = c == 0 ? 7 : c;
                int finalC = c;
                Integer n = weekList.stream().filter(a -> a > finalC).findFirst().orElse(weekList.get(0));
                return DateUtil.offsetDay(curDay, n > c ? n - c : 7 - c + n);
            case "monthly":
                List<Integer> montyList = frequencyAppoint.stream().map(a -> a % 31).map(a -> a == 0 ? 31 : a).distinct().sorted().collect(Collectors.toList());
                int mc = DateUtil.dayOfMonth(curDay);
                int max = DateUtil.dayOfMonth(DateUtil.endOfMonth(curDay));
                int mn = montyList.stream().filter(a -> a > mc && a <= max).findFirst().orElse(-1);
                if (mn > 0) {
                    return DateUtil.offsetDay(curDay, mn - mc);
                } else {
                    int nmax = DateUtil.endOfMonth(DateUtil.offsetMonth(curDay, 1)).dayOfMonth();
                    mn = montyList.get(0) > nmax ? nmax : montyList.get(0);
                    return DateUtil.beginOfDay(DateUtil.offsetDay(DateUtil.endOfMonth(curDay), mn));
                }
            default:
                return null;
        }
    }

    private LessonTimetableEntity getConflictTimetable(Map<String, List<LessonTimetableEntity>> timetableMap, List<Date> selectDays, List<String> timeSection) {
        List<LessonTimetableEntity> conflictTimetables = new ArrayList<>();
        if (timetableMap.isEmpty()) return null;
        for (Date selectDay : selectDays) {
            String curDayStr = DateUtil.format(selectDay, "yyyy-MM-dd");
            List<LessonTimetableEntity> timetables = timetableMap.get(curDayStr);
            if (CollUtil.isEmpty(timetables)) continue;
            for (String section : timeSection) {
                Date curBeginTime = DateUtil.parse(curDayStr + " " + section.split("-")[0], "yyyy-MM-dd HH:mm");
                Date curEndTime = DateUtil.parse(curDayStr + " " + section.split("-")[1], "yyyy-MM-dd HH:mm");
                for (LessonTimetableEntity timetable : timetables) {
                    if (curEndTime.compareTo(DateUtil.date(timetable.getLessonBeginTime()).toJdkDate()) <= 0 || curBeginTime.compareTo(DateUtil.date(timetable.getLessonEndTime()).toJdkDate()) >= 0) {
                        continue;
                    } else {
                        return timetable;
                    }
                }
            }
        }
        return null;
    }

    private List<LessonTimetableVO> wrapVO(List<LessonTimetableVO> voList) {
        if (CollUtil.isEmpty(voList)) {
            return voList;
        }
        Set<String> classCodes = voList.stream().map(LessonTimetableVO::getClassCode).collect(Collectors.toSet());
        Set<String> courseCodes = voList.stream().map(LessonTimetableVO::getCourseCode).collect(Collectors.toSet());
        Set<String> classroomCodes = voList.stream().map(LessonTimetableVO::getClassroomCode).collect(Collectors.toSet());
        Set<String> teacherCodes = voList.stream().map(LessonTimetableVO::getTeacherCode).collect(Collectors.toSet());
        Map<String, String> classCodeMap = classInfoDao.selectList(new LambdaQueryWrapper<ClassInfoEntity>().in(ClassInfoEntity::getClassCode, classCodes))
                .stream().collect(Collectors.toMap(ClassInfoEntity::getClassCode, ClassInfoEntity::getClassName));
        Map<String, String> courseCodeMap = courseDao.selectList(new LambdaQueryWrapper<CourseEntity>().in(CourseEntity::getCourseCode, courseCodes))
                .stream().collect(Collectors.toMap(CourseEntity::getCourseCode, CourseEntity::getCourseName));
        Map<String, String> classroomCodeMap = classroomDao.selectList(new LambdaQueryWrapper<ClassroomEntity>().in(ClassroomEntity::getClassroomCode, classroomCodes))
                .stream().collect(Collectors.toMap(ClassroomEntity::getClassroomCode, ClassroomEntity::getClassroomName));
        Map<String, String> teacherCodeMap = teacherDao.selectList(new LambdaQueryWrapper<TeacherEntity>().in(TeacherEntity::getTeacherCode, teacherCodes))
                .stream().collect(Collectors.toMap(TeacherEntity::getTeacherCode, TeacherEntity::getTeacherName));
        voList.forEach(vo -> {
            vo.setClassName(classCodeMap.get(vo.getClassCode()));
            vo.setCourseName(courseCodeMap.get(vo.getCourseCode()));
            vo.setClassroomName(classroomCodeMap.get(vo.getClassroomCode()));
            vo.setTeacherName(teacherCodeMap.get(vo.getTeacherCode()));
            vo.setLessonStatusLabel(LessonStatusEnum.getByCode(vo.getLessonStatus()).getLabel());
        });
        return voList;
    }
}