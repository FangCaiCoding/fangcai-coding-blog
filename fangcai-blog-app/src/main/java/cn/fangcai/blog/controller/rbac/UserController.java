package cn.fangcai.blog.controller.rbac;


import cn.fangcai.blog.core.model.req.UserEditReq;
import cn.fangcai.blog.core.service.IUserService;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.starter.auth.FcAuthContext;
import cn.fangcai.starter.log.ano.FcLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MouFangCai
 * @date 2024/12/1 23:05
 * @description
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户相关")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperationSupport(order = 19)
    @Operation(summary = "修改自己的信息")
    @PutMapping("editUser")
    @FcLog(desc = "修改用户信息", actionType = FcLog.ActionType.UPDATE)
    public FcResult<Boolean> editUser(@RequestBody @Validated UserEditReq uptReq) {
        uptReq.setUserId(FcAuthContext.getUserIdAsInt());
        return FcResult.SUCCESS( userService.editUser(uptReq));
    }
}
