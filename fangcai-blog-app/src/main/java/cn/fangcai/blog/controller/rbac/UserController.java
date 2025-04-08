package cn.fangcai.blog.controller.rbac;


import cn.fangcai.blog.core.model.req.UserEditReq;
import cn.fangcai.blog.core.model.req.UserPageReq;
import cn.fangcai.blog.core.model.res.UserRes;
import cn.fangcai.blog.core.service.IUserService;
import cn.fangcai.blog.core.service.IVipTokenService;
import cn.fangcai.common.model.dto.FcPageRes;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.starter.auth.FcAuthContext;
import cn.fangcai.starter.auth.ano.FcCheckAuth;
import cn.fangcai.starter.log.ano.FcLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author MouFangCai
 * @date 2024/12/1 23:05
 * @description
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户个人管理相关接口")
@ApiSort(value = 1)
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IVipTokenService vipTokenService;

    @ApiOperationSupport(order = 19)
    @Operation(summary = "修改自己的信息")
    @PutMapping("editUser")
    @FcLog(desc = "修改用户信息", actionType = FcLog.ActionType.UPDATE)
    public FcResult<Boolean> editUser(@RequestBody @Validated UserEditReq uptReq) {
        uptReq.setUserId(FcAuthContext.getUserIdAsInt());
        return FcResult.SUCCESS( userService.editUser(uptReq));
    }


    @ApiOperationSupport(order = 29)
    @Operation(summary = "兑换vip")
    @PutMapping("exchangeVip/{token}")
    @FcLog(desc = "兑换vip", actionType = FcLog.ActionType.UPDATE)
    public FcResult<Boolean> exchangeVip(@PathVariable String token) {
       return FcResult.SUCCESS(vipTokenService.exchangeVip(FcAuthContext.getUserIdAsInt(), token));
    }

    @ApiOperationSupport(order = 30)
    @Operation(summary = "分页查询用户")
    @PostMapping("/page")
    @FcCheckAuth(values = "user:page")
    @FcLog(desc = "分页查询用户", actionType = FcLog.ActionType.SELECT)
    public FcResult<FcPageRes<UserRes>> pageUser(@RequestBody @Validated UserPageReq pageReq) {
        return FcResult.SUCCESS(userService.pageUser(pageReq));
    }

    @ApiOperationSupport(order = 40)
    @Operation(summary = "启用/禁用用户")
    @FcCheckAuth(values = "user:edit")
    @PutMapping("/status/{userId}/{enabled}")
    @FcLog(desc = "修改用户状态", actionType = FcLog.ActionType.UPDATE)
    public FcResult<Boolean> uptUserStatus(@PathVariable Integer userId, @PathVariable Boolean enabled) {
        return FcResult.SUCCESS(userService.uptUserStatus(userId, enabled));
    }
}
