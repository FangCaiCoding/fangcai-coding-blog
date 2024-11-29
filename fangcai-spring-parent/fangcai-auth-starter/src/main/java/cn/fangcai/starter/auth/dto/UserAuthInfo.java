package cn.fangcai.starter.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author MouFangCai
 * @date 2024/8/19 22:17
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户上下文信息")
public class UserAuthInfo {

    @Schema(description = "用户ID")
    private Object id;


}
