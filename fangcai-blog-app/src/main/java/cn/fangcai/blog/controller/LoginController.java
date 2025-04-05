package cn.fangcai.blog.controller;


import cn.fangcai.blog.core.model.dto.UserAuthDto;
import cn.fangcai.blog.core.model.req.UserLoginByWxReq;
import cn.fangcai.blog.core.model.req.UserLoginReq;
import cn.fangcai.blog.core.model.res.UserRes;
import cn.fangcai.blog.core.service.IUserService;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.starter.auth.FcAuthContext;
import cn.fangcai.starter.auth.FcAuthUtil;
import cn.fangcai.starter.auth.ano.FcNotCheckLogin;
import cn.fangcai.starter.auth.service.IAuthService;
import cn.fangcai.starter.log.ano.FcLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 登录相关 前端控制器
 * </p>
 *
 * @author MouFangCai
 * @since 2023-03-21
 */
@RestController
@RequestMapping("/login")
@Tag(name = "登录相关")
public class LoginController {

    @Autowired
    private IUserService userService;


    @ApiOperationSupport(order = 10)
    @Operation(summary = "登录-通过账号")
    @PostMapping("loginByName")
    @FcNotCheckLogin
    @FcLog(desc = "登录-通过账号")
    public FcResult<UserRes> loginByName(@RequestBody @Validated UserLoginReq loginReq) {
        UserRes userRes = userService.loginByName(loginReq);
        FcAuthUtil.login(userRes.getId());
        return FcResult.SUCCESS(userRes);
    }

    @ApiOperationSupport(order = 20)
    @Operation(summary = "登录-通过微信code")
    @PostMapping("loginByWxCode")
    @FcNotCheckLogin
    @FcLog(desc = "登录-通过微信code")
    public FcResult<UserRes> loginByWxCode(@RequestBody @Validated UserLoginByWxReq wxReq) {
        UserRes userRes = userService.loginByWxCode(wxReq.getWxCode());
        FcAuthUtil.login(userRes.getId());
        return FcResult.SUCCESS(userRes);
    }


    @ApiOperationSupport(order = 30)
    @Operation(summary = "获取当前登录者用户信息")
    @GetMapping("getUser")
    @FcNotCheckLogin
    public FcResult<UserRes> getUser() {
        FcAuthUtil.initClientIfAbsent();
        UserRes userInfo = null;
        if (FcAuthUtil.isLogin()) {
            userInfo = userService.getById(FcAuthContext.getUserIdAsInt());
        }
        return FcResult.SUCCESS(userInfo);
    }


    @ApiOperationSupport(order = 40)
    @Operation(summary = "退出登录")
    @PutMapping("logout")
    @FcLog(desc = "退出登录")
    public FcResult<Boolean> logout() {
        FcAuthUtil.logout();
        return FcResult.SUCCESS(Boolean.TRUE);
    }


}
