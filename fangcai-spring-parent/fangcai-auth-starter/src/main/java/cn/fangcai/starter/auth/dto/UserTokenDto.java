package cn.fangcai.starter.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MouFangCai
 * @date 2023/3/24 22:42
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户Token中的信息")
public class UserTokenDto {

    @Schema(description = "userID")
    private Object userId;


    @Schema(description = "会话ID")
    private String sessionId;

    public UserTokenDto(Object userId) {
        this.userId = userId;
    }

}
