package cn.fangcai.blog.core.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author MouFangCai
 * @date 2025/1/18 16:17
 * @description
 */
@Data
@Schema(description = "用户编辑对象")
public class UserEditReq {

    @Schema(description = "用户id，对个人用户的操作从上下文获取，管理员操作可以修改他人的")
    private Integer userId;

    @Schema(description = "头像文字")
    @NotEmpty(message = "头像文字不能为空")
    @Length(max = 7,message = "头像文字 不能超过 7 个字符")
    private String avatarStr;

    @Schema(description = "昵称")
    @NotEmpty(message = "昵称不能为空")
    @Length(max = 32,message = "昵称 不能超过 32 个字符")
    private String nickName;
}
