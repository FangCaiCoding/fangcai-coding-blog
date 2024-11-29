package cn.fangcai.starter.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MouFangCai
 * @date 2024/8/19 22:17
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "客户端信息")
public class ClientInfo {

    @Schema(description = "客户端ID，由服务端随机颁发")
    private String clientId;

    @Schema(description = "客户端IP")
    private String  clientIp;

}
