package com.train.system.controller;

import com.train.framework.limit.annotations.RepeatRequestLimit;
import com.train.system.service.SysMenuDataPermissionService;
import com.train.system.vo.SysMenuDataPermissionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import com.train.framework.common.utils.Result;
import com.train.framework.operatelog.annotations.OperateLog;
import com.train.framework.operatelog.enums.OperateTypeEnum;
import com.train.framework.security.user.SecurityUser;
import com.train.framework.security.user.UserDetail;
import com.train.system.convert.SysMenuConvert;
import com.train.system.entity.SysMenuEntity;
import com.train.system.enums.MenuTypeEnum;
import com.train.system.service.SysMenuService;
import com.train.system.vo.SysMenuVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 *
 */
@RestController
@RequestMapping("sys/menu")
@Tag(name = "菜单管理")
@AllArgsConstructor
public class SysMenuController {
    private final SysMenuService sysMenuService;
    private final SysMenuDataPermissionService sysMenuDataPermissionService;

    @GetMapping("nav")
    @Operation(summary = "菜单导航")
    public Result<List<SysMenuVO>> nav() {
        UserDetail user = SecurityUser.getUser();
        List<SysMenuVO> list = sysMenuService.getUserMenuList(user, MenuTypeEnum.MENU.getValue());

        return Result.ok(list);
    }

    @GetMapping("authority")
    @Operation(summary = "用户权限标识")
    public Result<Set<String>> authority() {
        UserDetail user = SecurityUser.getUser();
        Set<String> set = sysMenuService.getUserAuthority(user);

        return Result.ok(set);
    }

    @GetMapping("list")
    @Operation(summary = "菜单列表")
    @Parameter(name = "type", description = "菜单类型 0：菜单 1：按钮  2：接口  null：全部")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result<List<SysMenuVO>> list(Integer type) {
        List<SysMenuVO> list = sysMenuService.getMenuList(type);

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:menu:info')")
    public Result<SysMenuVO> get(@PathVariable("id") Long id) {
        SysMenuEntity entity = sysMenuService.getById(id);
        SysMenuVO vo = SysMenuConvert.INSTANCE.convert(entity);

        // 获取上级菜单名称
        if (entity.getPid() != null) {
            SysMenuEntity parentEntity = sysMenuService.getById(entity.getPid());
            vo.setParentName(parentEntity.getName());
        }

        return Result.ok(vo);
    }

    @PostMapping
    @Operation(summary = "保存")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result<String> save(@RequestBody @Valid SysMenuVO vo) {
        sysMenuService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result<String> update(@RequestBody @Valid SysMenuVO vo) {
        sysMenuService.update(vo);

        return Result.ok();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除")
    @OperateLog(type = OperateTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result<String> delete(@PathVariable("id") Long id) {
        // 判断是否有子菜单或按钮
        Long count = sysMenuService.getSubMenuCount(id);
        if (count > 0) {
            return Result.error("请先删除子菜单");
        }

        sysMenuService.delete(id);

        return Result.ok();
    }

    @GetMapping("dataPermissionList")
    @Parameter(name = "component", description = "前端组件，对应菜单的url")
    @Operation(summary = "数据权限列表")
    public Result<List<SysMenuDataPermissionVO>> dataPermissionList(String component) {

        return Result.ok(sysMenuDataPermissionService.getListByComponent(component));
    }
    @RepeatRequestLimit
    @PostMapping("dataPermission")
    @Operation(summary = "保存数据权限")
    @OperateLog(type = OperateTypeEnum.INSERT)
    public Result<String> save(@RequestBody @Valid SysMenuDataPermissionVO vo) {
        sysMenuDataPermissionService.save(vo);

        return Result.ok();
    }
    @RepeatRequestLimit
    @PutMapping("dataPermission")
    @Operation(summary = "修改数据权限")
    @OperateLog(type = OperateTypeEnum.UPDATE)
    public Result<String> update(@RequestBody @Valid SysMenuDataPermissionVO vo) {
        sysMenuDataPermissionService.update(vo);

        return Result.ok();
    }
    @DeleteMapping("dataPermission/{id}")
    @Operation(summary = "删除数据权限")
    @OperateLog(type = OperateTypeEnum.DELETE)
    public Result<String> deleteDataPermission(@PathVariable("id") Long id) {
        sysMenuDataPermissionService.removeById(id);
        return Result.ok();
    }
}