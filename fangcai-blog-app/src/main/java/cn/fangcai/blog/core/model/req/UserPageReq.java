package cn.fangcai.blog.core.model.req;

import cn.fangcai.common.model.dto.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author MouFangCai
 * @date 2024/12/10 20:10
 * @description 用户分页查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户分页查询请求")
public class UserPageReq extends PageReq {

    @Schema(description = "用户名关键字")
    private String username;

    @Schema(description = "用户状态 1-启用 0-停用")
    private Boolean enabled;

    @Schema(description = "是否是VIP 1-是 0-否")
    private Boolean isVip;

}