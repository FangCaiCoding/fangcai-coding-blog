package cn.fangcai.blog.model.req;

import cn.fangcai.blog.consts.UserConst;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author MouFangCai
 * @date 2023/3/23 23:56
 * @description
 */
@Schema(description = "邮箱注册 req")
@Data
public class UserEmailRegisterReq {


    @Schema(description = "邮箱地址")
    @NotEmpty(message = "邮箱地址不能为空")
    @Pattern(regexp = UserConst.EMAIL_REGEX, message = "邮箱地址格式错误")
    @Length(max = 64, message = UserConst.ILLEGAL_PARAM_MSG)
    private String email;

    @Schema(description = "邮件验证码")
    @NotEmpty(message = "验证码不能为空")
    @Length(max = 16, message = UserConst.ILLEGAL_PARAM_MSG)
    private String code;
}
