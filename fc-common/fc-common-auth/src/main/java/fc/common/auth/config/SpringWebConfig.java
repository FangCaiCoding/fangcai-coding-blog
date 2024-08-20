package fc.common.auth.config;


import fc.common.auth.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Configuration
public class SpringWebConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${spring.profiles.active}")
    private String env;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        LoginInterceptor loginInterceptor = this.applicationContext.getBean(LoginInterceptor.class);
        // 排除swagger 拦截路径
        String[] excludePatterns = new String[]{"/swagger-resources/**", "/webjars/**", "/v2/**", "/v3/**", "/swagger-ui.html/**",
                "/api", "/api-docs", "/api-docs/**", "/doc.html", "/doc.html/**", "/swagger-ui/**"};

        List<String> swaggerExclude = Arrays.asList(excludePatterns);
        // 登录检查
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/error")
                .excludePathPatterns(swaggerExclude);
    }

    /**
     * 跨域設置
     *
     * @return
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        Set<String> allowedEnv = Set.of("dev", "test", "local");
        if (allowedEnv.contains(env)) {
            registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("*")
                    .allowCredentials(true)
                    .maxAge(3600)
                    .allowedHeaders("*");
        }
    }


}
