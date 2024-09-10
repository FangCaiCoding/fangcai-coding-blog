package cn.fangcai.blog.controller;


import cn.fangcai.blog.model.req.UserEmailRegisterReq;
import cn.fangcai.blog.model.req.UserLoginReq;
import cn.fangcai.blog.model.res.UserRes;
import cn.fangcai.blog.service.IUserService;
import cn.fangcai.common.auth.FcAuthContext;
import cn.fangcai.common.auth.FcAuthUtil;
import cn.fangcai.common.auth.ano.FcNotCheckLogin;
import cn.fangcai.common.model.dto.FcResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author MouFangCai
 * @since 2023-03-21
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {

    @Autowired
    private IUserService userService;


    @ApiOperationSupport(order = 10)
    @Operation(summary = "用户注册")
    @PostMapping("register")
    @FcNotCheckLogin
    private FcResult<UserRes> registerAndLogin(@RequestBody @Validated UserEmailRegisterReq registerReq) {
        // TODO : by mfc on 2024/8/24 邮件验证码验证
        UserRes userRes = userService.register(registerReq);
        FcAuthUtil.login(userRes.getId());
        return FcResult.SUCCESS(userRes);
    }


    @ApiOperationSupport(order = 20)
    @Operation(summary = "登录-通过账号")
    @PostMapping("loginByName")
    @FcNotCheckLogin
    private FcResult<UserRes> loginByName(@RequestBody @Validated UserLoginReq loginReq) {
        UserRes userRes = userService.loginByName(loginReq);
        FcAuthUtil.login(userRes.getId());
        return FcResult.SUCCESS(userRes);
    }


    @ApiOperationSupport(order = 30)
    @Operation(summary = "获取当前登录者用户信息")
    @GetMapping("getUser")
    private FcResult<UserRes> getUser() {
        return FcResult.SUCCESS(userService.getById(FcAuthContext.getUserIdAsInt()));
    }

    @ApiOperationSupport(order = 40)
    @Operation(summary = "退出登录")
    @PutMapping("logout")
    private FcResult<Boolean> logout() {
        FcAuthUtil.logout();
        return FcResult.SUCCESS(Boolean.TRUE);
    }


}
