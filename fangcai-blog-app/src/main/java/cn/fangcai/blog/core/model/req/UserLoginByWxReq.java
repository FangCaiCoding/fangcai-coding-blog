package cn.fangcai.blog.core.model.req;

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
public class UserLoginByWxReq {


    @Schema(description = "验证码")
    @NotEmpty(message = "验证码 不能为空")
    @Length(max = 16, message = UserConst.ILLEGAL_PARAM_MSG)
    private String wxCode;

}
