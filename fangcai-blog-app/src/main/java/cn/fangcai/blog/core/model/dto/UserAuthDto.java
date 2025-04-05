package cn.fangcai.blog.core.model.dto;

import cn.fangcai.starter.auth.dto.UserAuthInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author MouFangCai
 * @date 2025/4/5 14:40
 * @description
 */
@Data
public class UserAuthDto extends UserAuthInfo {

    @Schema(description = "是否是VIP")
    private Boolean isVip;

    public UserAuthDto(Boolean isLogin, Object id, Boolean isVip) {
        super(isLogin, id);
        this.isVip = isVip;
    }

    public UserAuthDto() {
        super();
    }
}
