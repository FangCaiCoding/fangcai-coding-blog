package cn.fangcai.blog.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author MouFangCai
 * @date 2024/10/26 18:31
 * @description
 */
@Data
@Component
@ConfigurationProperties(prefix = "blog-app-config")
@Validated
public class BlogAppProperties {

    /**
     * 默认角色ID
     * 用于用户注册时，给用户分配默认角色
     */
    @NotNull(message = "默认角色ID不能为空")
    private Integer defaultRoleId;

    /**
     * 微信Token
     */
    @NotBlank(message = "微信Token不能为空")
    private String wxToken;
}
