package cn.fangcai.starter.log.config;

import cn.fangcai.starter.log.service.DefaultLogService;
import cn.fangcai.starter.log.service.ILogService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MouFangCai
 * @date 2024/8/25 8:59
 * @description
 */
@Configuration
public class LogServiceConfig {

    @Bean
    @ConditionalOnMissingBean(value = ILogService.class)
    public DefaultLogService defaultLogService() {
        return new DefaultLogService();
    }
}
