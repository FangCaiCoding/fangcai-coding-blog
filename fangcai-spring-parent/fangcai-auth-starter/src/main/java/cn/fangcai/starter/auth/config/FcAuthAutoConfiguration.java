package cn.fangcai.starter.auth.config;

import cn.fangcai.starter.auth.service.DefaultAuthService;
import cn.fangcai.starter.auth.service.IAuthService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MouFangCai
 * @date 2024/11/30 22:11
 * @description
 */
@Configuration
public class FcAuthAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = IAuthService.class)
    public DefaultAuthService defaultAuthService() {
        return new DefaultAuthService();
    }
}
