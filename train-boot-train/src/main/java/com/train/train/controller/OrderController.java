package com.train.train.controller;

import com.train.framework.common.utils.ExcelUtils;
import com.train.framework.common.utils.PageResult;
import com.train.framework.common.utils.Result;
import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.framework.operatelog.annotations.OperateLog;
import com.train.framework.operatelog.enums.OperateTypeEnum;
import com.train.train.query.CourseQuery;
import com.train.train.query.OrderPriceQuery;
import com.train.train.query.OrderQuery;
import com.train.train.service.OrderService;
import com.train.train.vo.CourseVO;
import com.train.train.vo.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("train/order")
@Tag(name="订单")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('train:order:page')")
    public Result<PageResult<OrderVO>> page(@ParameterObject @Valid OrderQuery query){
        PageResult<OrderVO> page = orderService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('train:order:info')")
    public Result<OrderVO> get(@PathVariable("id") Long id){
        OrderVO orderVO = orderService.getVOById(id);

        return Result.ok(orderVO);
    }

    @RepeatRequestLimit
    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('train:order:save')")
    public Result<String> save(@RequestBody OrderVO vo){
        orderService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('train:order:update')")
    public Result<String> update(@RequestBody @Valid OrderVO vo){
        orderService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('train:order:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){

        return Result.error("订单不允许删除");
    }
    @RepeatRequestLimit
    @PostMapping("createOrder")
    @Operation(summary = "创建订单")
    public Result<OrderVO> createOrder(@RequestBody OrderVO order){
        OrderVO vo = orderService.createOrder(order);

        return Result.ok(vo);
    }
    @RepeatRequestLimit
    @PostMapping("/pay")
    @Operation(summary = "支付")
    public Result<String> pay(@RequestBody OrderVO order){
        orderService.pay(order.getId(), order.getServiceFee());

        return Result.ok();
    }
    @PostMapping("/cancel")
    @Operation(summary = "取消订单")
    public Result<String> cancel(@RequestBody OrderVO order){
        orderService.cancel(order.getId());

        return Result.ok();
    }

    @PostMapping("calcOrderPrice")
    @Operation(summary = "试算订单价格")
    public Result<OrderPriceQuery> getOrderPrice(@RequestBody OrderPriceQuery query){

        return Result.ok(orderService.calcOrderPrice(query));
    }
    @GetMapping("export")
    @Operation(summary = "导出订单")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void export(@ParameterObject OrderQuery query, HttpServletResponse response) {
        ExcelUtils.writeExcel(response, OrderVO.class, orderService, "page",OrderQuery.class, query,"订单", null,null);
    }
}
