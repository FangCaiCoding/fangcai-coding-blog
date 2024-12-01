package cn.fangcai.starter.log.config;

import cn.fangcai.starter.log.service.DefaultLogService;
import cn.fangcai.starter.log.service.IFcLogService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author MouFangCai
 * @date 2024/11/30 22:11
 * @description
 */
@Configuration
public class FcLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = IFcLogService.class)
    public DefaultLogService defaultLogService() {
        return new DefaultLogService();
    }
}
