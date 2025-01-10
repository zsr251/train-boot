package com.train.student.controller;

import com.train.framework.common.utils.Result;
import com.train.framework.security.utils.TokenUtils;
import com.train.student.service.AuthService;
import com.train.student.vo.StudentChangePasswordVO;
import com.train.student.vo.StudentLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("student/auth")
@Tag(name = "学员")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("login")
    @Operation(summary = "手机号密码登录")
    public Result<String> login(@RequestBody StudentLoginVO login) {
        return Result.ok(authService.login(login));
    }

    @PostMapping("logout")
    @Operation(summary = "退出登录")
    public Result<String> logout(HttpServletRequest request) {
        authService.logout(TokenUtils.getAccessToken(request));
        return Result.ok("退出登录成功");
    }

    @PostMapping("changePassword")
    @Operation(summary = "修改密码")
    public Result<String> changePassword(@RequestBody StudentChangePasswordVO vo, HttpServletRequest request) {
        authService.changePassword(TokenUtils.getAccessToken(request), vo);
        return Result.ok("修改密码成功");
    }

}
