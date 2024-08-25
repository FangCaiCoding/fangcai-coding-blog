package cn.fangcai.common.auth.config;

import cn.fangcai.common.auth.service.DefaultAuthService;
import cn.fangcai.common.auth.service.IAuthService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MouFangCai
 * @date 2024/8/25 8:59
 * @description
 */
@Configuration
public class AuthServiceConfig {

    @Bean
    @ConditionalOnMissingBean(value = IAuthService.class)
    public DefaultAuthService defaultAuthService() {
        return new DefaultAuthService();
    }
}
