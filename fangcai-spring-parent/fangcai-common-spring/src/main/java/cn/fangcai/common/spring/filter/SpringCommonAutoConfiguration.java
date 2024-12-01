package cn.fangcai.common.spring.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author MouFangCai
 * @date 2024/9/11 22:42
 * @description 注册过滤器
 */
@Configuration
public class SpringCommonAutoConfiguration {
    @Bean(name = "fcSpringCommonRequestCachingFilterRegistration")
    public FilterRegistrationBean<RequestCachingFilter> requestCachingFilterRegistration() {
        FilterRegistrationBean<RequestCachingFilter> registration = new FilterRegistrationBean<>(new RequestCachingFilter());
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}
