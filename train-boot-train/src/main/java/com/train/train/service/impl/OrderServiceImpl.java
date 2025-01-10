package com.train.train.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.train.framework.common.exception.ServerException;
import com.train.framework.common.utils.JsonUtils;
import com.train.framework.common.utils.PageResult;
import com.train.framework.dict.utils.TransUtils;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.constant.TrainConstant;
import com.train.train.convert.OrderConvert;
import com.train.train.dao.CourseDao;
import com.train.train.dao.CourseHourBuyLogDao;
import com.train.train.dao.OrderDao;
import com.train.train.dao.StudentCourseHourDao;
import com.train.train.entity.CourseEntity;
import com.train.train.entity.CourseHourBuyLogEntity;
import com.train.train.entity.OrderEntity;
import com.train.train.entity.StudentCourseHourEntity;
import com.train.train.query.OrderPriceQuery;
import com.train.train.query.OrderQuery;
import com.train.train.service.OrderService;
import com.train.train.vo.CoursePriceVO;
import com.train.train.vo.OrderVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl extends BaseServiceImpl<OrderDao, OrderEntity> implements OrderService {
    private final CourseDao courseDao;
    private final StudentCourseHourDao studentCourseHourDao;
    private final CourseHourBuyLogDao courseHourBuyLogDao;

    @Override
    public PageResult<OrderVO> page(OrderQuery query) {
        IPage<OrderEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        List<OrderVO> voList = new ArrayList<>();
        for (OrderEntity record : page.getRecords()) {
            OrderVO vo = OrderConvert.INSTANCE.convert(record);
            vo.setCreateTime(record.getCreateTime());
            vo.setCourseList(JsonUtils.parseArray(record.getCourseDetail(), CoursePriceVO.class));
            voList.add(vo);
        }

        return new PageResult<>(TransUtils.transList(voList, OrderVO.class), page.getTotal());
    }

    private LambdaQueryWrapper<OrderEntity> getWrapper(OrderQuery query) {
        LambdaQueryWrapper<OrderEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(query.getStudentId() != null && query.getStudentId() != 0, OrderEntity::getStudentId, query.getStudentId());
        wrapper.eq(StringUtils.isNotBlank(query.getOrderStatus()), OrderEntity::getOrderStatus, query.getOrderStatus());
        wrapper.ge(query.getPaidTimeBegin() != null, OrderEntity::getPaidTime, query.getPaidTimeBegin());
        wrapper.le(query.getPaidTimeEnd() != null, OrderEntity::getPaidTime, query.getPaidTimeEnd());
        wrapper.orderByDesc(OrderEntity::getId);
        return wrapper;
    }

    @Override
    public void save(OrderVO vo) {
        createOrder(vo);
    }

    @Override
    public void update(OrderVO vo) {
        Validator.validateNotNull(vo.getId(), "订单ID不能为空");
        cancel(vo.getId());
    }

    @Override
    public OrderVO getVOById(Long orderId) {
        OrderEntity entity = getById(orderId);
        if (entity == null) {
            throw new ServerException("订单不存在");
        }
        OrderVO vo = OrderConvert.INSTANCE.convert(entity);
        if (entity.getCourseDetail() != null) {
            vo.setCourseList(JsonUtils.parseArray(entity.getCourseDetail(), CoursePriceVO.class));
        }
        return TransUtils.transOne(vo, OrderVO.class);
    }

    @Override
    public OrderPriceQuery calcOrderPrice(OrderPriceQuery query) {
        Validator.validateNotNull(query.getStudentId(), "学员ID不能为空");
        if (query.getCourseList() == null || query.getCourseList().isEmpty()) {
            return query;
        }
        // 课程编码
        List<String> courseCodeList = query.getCourseList().stream().map(CoursePriceVO::getCourseCode).toList();
        Map<String, CourseEntity> courseMap = courseDao.selectList(new LambdaQueryWrapper<CourseEntity>().in(CourseEntity::getCourseCode, courseCodeList))
                .stream().collect(Collectors.toMap(CourseEntity::getCourseCode, course -> course));
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CoursePriceVO course : query.getCourseList()) {
            CourseEntity entity = courseMap.get(course.getCourseCode());
            if (entity == null) {
                throw new ServerException("课程不存在：" + course.getCourseCode());
            }
            course.setCourseName(entity.getCourseName()).setCourseHourOnce(entity.getCourseHourOnce());
            course.setCourseHourTotal(entity.getCourseHourTotal()).setAmountOneHour(entity.getAmountOneHour());
            int h = course.getBuyHours() == null || course.getBuyHours() == 0 ? entity.getCourseHourTotal() : course.getBuyHours();
            course.setBuyHours(h).setTotalPrice(new BigDecimal(h).multiply(entity.getAmountOneHour()));
            totalPrice = totalPrice.add(course.getTotalPrice());
        }
        query.setTotalPrice(totalPrice);
        return query;
    }

    @Override
    public OrderVO createOrder(OrderVO order) {
        Validator.validateNotNull(order.getStudentId(), "学员ID不能为空");
        if (order.getCourseList() == null || order.getCourseList().isEmpty()) {
            throw new ServerException("课程不能为空");
        }
        order.setId(null);
        // 课程编码
        List<String> courseCodeList = order.getCourseList().stream().map(CoursePriceVO::getCourseCode).toList();
        Map<String, CourseEntity> courseMap = courseDao.selectList(new LambdaQueryWrapper<CourseEntity>().in(CourseEntity::getCourseCode, courseCodeList))
                .stream().collect(Collectors.toMap(CourseEntity::getCourseCode, course -> course));
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CoursePriceVO course : order.getCourseList()) {
            CourseEntity entity = courseMap.get(course.getCourseCode());
            if (entity == null) {
                throw new ServerException("课程不存在：" + course.getCourseCode());
            }
            course.setCourseName(entity.getCourseName()).setCourseHourOnce(entity.getCourseHourOnce());
            course.setCourseHourTotal(entity.getCourseHourTotal()).setAmountOneHour(entity.getAmountOneHour());
            int h = course.getBuyHours() == null ? entity.getCourseHourTotal() : course.getBuyHours();
            course.setBuyHours(h).setTotalPrice(new BigDecimal(h).multiply(entity.getAmountOneHour()));
            totalPrice = totalPrice.add(course.getTotalPrice());
        }
        if (order.getDiscount() == null) {
            order.setDiscount(BigDecimal.ZERO);
        }
        if (order.getDiscount().compareTo(BigDecimal.ZERO) > 0 && StrUtil.isBlank(order.getDiscountReason())) {
            throw new ServerException("减免原因不能为空");
        }
        if (order.getDiscount().compareTo(totalPrice) > 0) {
            throw new ServerException("减免金额不能大于订单金额");
        }
        BigDecimal shouldPay = totalPrice.subtract(order.getDiscount());
        order.setTotalPrice(totalPrice).setShouldPay(shouldPay).setPaid(BigDecimal.ZERO)
                .setOrderStatus(TrainConstant.ORDER_STATUS_WAIT);

        OrderEntity entity = OrderConvert.INSTANCE.convert(order);
        entity.setCourseDetail(JsonUtils.toJsonString(order.getCourseList()));
        baseMapper.insert(entity);
        log.info("订单创建成功：{}", entity);
        order.setId(entity.getId());
        return order;
    }

    @Override
    public void pay(Long orderId, BigDecimal serviceFee) {
        OrderEntity entity = getById(orderId);
        if (entity == null) {
            throw new ServerException("订单不存在");
        }
        if (entity.getOrderStatus().equals(TrainConstant.ORDER_STATUS_PAID)) {
            throw new ServerException("订单已支付");
        }
        serviceFee = serviceFee == null || serviceFee.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : serviceFee;
        entity.setOrderStatus(TrainConstant.ORDER_STATUS_PAID);
        entity.setPaid(entity.getShouldPay());
        entity.setPaidTime(LocalDateTime.now());
        entity.setServiceFee(serviceFee);
        entity.setActuallyReceived(entity.getShouldPay().subtract(serviceFee));
        updateById(entity);
        log.info("订单支付成功：{}", entity);

        List<CoursePriceVO> courseList = JsonUtils.parseArray(entity.getCourseDetail(), CoursePriceVO.class);
        addCourseHourBuyLog(entity.getStudentId(), orderId, courseList);
    }

    @Override
    public void cancel(Long orderId) {
        OrderEntity entity = getById(orderId);
        if (entity == null) {
            throw new ServerException("订单不存在");
        }
        if (entity.getOrderStatus().equals(TrainConstant.ORDER_STATUS_PAID)) {
            throw new ServerException("订单已支付，不能取消");
        }
        entity.setOrderStatus(TrainConstant.ORDER_STATUS_CANCEL);
        updateById(entity);
    }

    private void addCourseHourBuyLog(Long studentId, Long orderId, List<CoursePriceVO> courseList) {
        if (CollUtil.isEmpty(courseList) || studentId == null || orderId == null) {
            return;
        }
        List<String> courseCodeList = courseList.stream().map(CoursePriceVO::getCourseCode).toList();
        Map<String, StudentCourseHourEntity> hourEntityMap = studentCourseHourDao.selectList(new LambdaQueryWrapper<StudentCourseHourEntity>().eq(StudentCourseHourEntity::getStudentId, studentId)
                .in(StudentCourseHourEntity::getCourseCode, courseCodeList)).stream().collect(Collectors.toMap(StudentCourseHourEntity::getCourseCode, a -> a));
        List<CourseHourBuyLogEntity> logList = new ArrayList<>();
        for (CoursePriceVO vo : courseList) {
            StudentCourseHourEntity hour = hourEntityMap.get(vo.getCourseCode());
            if (hour == null) {
                logList.add(new CourseHourBuyLogEntity().setStudentId(studentId).setOrderId(orderId).setCourseCode(vo.getCourseCode()).setAmountOneHour(vo.getAmountOneHour())
                        .setCourseHourBuy(vo.getBuyHours()).setOrderAmount(vo.getTotalPrice()).setCourseHourBefore(0).setCourseHourAfter(vo.getBuyHours()));
                hour = new StudentCourseHourEntity().setCourseCode(vo.getCourseCode()).setStudentId(studentId)
                        .setCourseHourDeplete(0).setCourseHourLeft(vo.getBuyHours());
                studentCourseHourDao.insert(hour);
            } else {
                logList.add(new CourseHourBuyLogEntity().setStudentId(studentId).setOrderId(orderId).setCourseCode(vo.getCourseCode()).setAmountOneHour(vo.getAmountOneHour())
                        .setCourseHourBuy(vo.getBuyHours()).setOrderAmount(vo.getTotalPrice()).setCourseHourBefore(hour.getCourseHourLeft()).setCourseHourAfter(hour.getCourseHourLeft() + vo.getBuyHours()));
                studentCourseHourDao.updateById(hour.setCourseHourLeft(hour.getCourseHourLeft() + vo.getBuyHours()));
            }
        }
        courseHourBuyLogDao.insert(logList);
    }
}
