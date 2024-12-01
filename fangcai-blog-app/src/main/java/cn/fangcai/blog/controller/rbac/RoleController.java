package cn.fangcai.blog.controller.rbac;

import cn.fangcai.blog.service.IRoleService;
import cn.fangcai.common.model.dto.FcResult;
import cn.fangcai.starter.auth.ano.FcCheckAuth;
import cn.fangcai.starter.log.ano.FcLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色信息（带角色权限关系） 前端控制器
 * </p>
 *
 * @author MouFangCai
 * @since 2024-10-25
 */
@RestController
@RequestMapping("/role")
@Tag(name = "角色管理")
public class RoleController {


    @Autowired
    private IRoleService roleService;


    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    @FcCheckAuth(values = "role:del")
    @FcLog(desc = "删除角色", actionType = FcLog.ActionType.DELETE)
    public FcResult<Boolean> delById(@PathVariable Integer id) {
        return FcResult.SUCCESS(roleService.delById(id));
    }
}
