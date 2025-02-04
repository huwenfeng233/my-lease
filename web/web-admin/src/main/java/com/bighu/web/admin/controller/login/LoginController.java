package com.bighu.web.admin.controller.login;


import com.bighu.common.result.Result;
import com.bighu.web.admin.service.LoginService;
import com.bighu.web.admin.service.SystemUserService;
import com.bighu.web.admin.vo.login.CaptchaVo;
import com.bighu.web.admin.vo.login.LoginVo;
import com.bighu.web.admin.vo.system.user.SystemUserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@Tag(name = "后台管理系统登录管理")
@RestController
@RequestMapping("/admin")
public class LoginController {

    private final LoginService loginService;
    private final SystemUserService systemUserService;

    public LoginController(LoginService loginService, SystemUserService systemUserService) {
        this.loginService = loginService;
        this.systemUserService = systemUserService;
    }

    @Operation(summary = "获取图形验证码")
    @GetMapping("login/captcha")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo  captchaVo=  loginService.getCaptcha();
        return Result.ok(captchaVo);
    }

    @Operation(summary = "登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String jwt=loginService.login(loginVo);

        return Result.ok(jwt);
    }

    @Operation(summary = "获取登陆用户个人信息")
    @GetMapping("info")
    public Result<SystemUserInfoVo> info() {
        SystemUserInfoVo systemUserInfoVo=systemUserService.getLoginUser();
        return Result.ok(systemUserInfoVo);
    }
}