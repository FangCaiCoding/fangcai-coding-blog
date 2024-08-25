package cn.fangcai.blog.model.req;

import cn.fangcai.blog.consts.UserConst;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author MouFangCai
 * @date 2023/3/23 23:56
 * @description
 */
@Schema(description = "用户登录 DTO")
@Data
public class UserLoginReq {


    @Schema(description = "登录名")
    @NotEmpty(message = "登录账号名称 不能为空")
    @Length(max = 64, message = UserConst.ILLEGAL_PARAM_MSG)
    private String loginName;


    @Schema(description = "密码")
    @NotEmpty(message = "密码 不能为空")
    @Length(max = 255, message = UserConst.ILLEGAL_PARAM_MSG)
    private String password;

}
