package cn.fangcai.common.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2024/8/19 22:17
 * @description
 */
@Data
@Schema(description = "用户上下文信息")
public class UserAuthInfo<T> {

    @Schema(description = "用户ID")
    private T id;

    @Schema(description = "登录名")
    private String loginName;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "权限code集合")
    private List<String> authCodeList;
}
