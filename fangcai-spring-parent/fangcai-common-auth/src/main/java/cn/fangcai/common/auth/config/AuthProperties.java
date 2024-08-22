package cn.fangcai.common.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author MouFangCai
 * @date 2021/4/22 12:23
 * @description 全局变量 上下文
 */
@Component
public class AuthProperties {


    public static String SM4_SECRET_KEY;
    /**
     * JWT过期时间：多少天后的
     */
    public static Integer JWT_EXP_DAYS;

    /**
     * jwt 私钥
     */
    public static String JWT_SECRET_KEY;


    /**
     * cookie 的 主域名
     */
    public static String COOKIE_DOMAIN;

    /**
     * cookie 和 header 名
     */
    public static String COOKIE_TOKEN_NAME ;

    /**
     * cookie 过期时间：单位 s【根据 JWT_EXP_DAYS 计算得出】
     */
    public static Integer COOKIE_MAX_AGE;

    /**
     * cookie  SECURE 属性：设置为true，则只能在https中传递
     */
    public static Boolean COOKIE_SECURE;


    /**
     * 每日登录错误总次数限制
     */
    public static Integer LOGIN_ERROR_COUNT_LIMIT;

    /**
     * 环境标识
     */
    public static String ACTIVE;


    /**
     * @value 注解添加默认值
     * null值用#{}包裹，如果不用默认解析成字符串
     */
    @Value("${fc_config.auth.sm4.secret_key:@qwr0987+d23df89}")
    public void setSm4SecretKey(String sm4SecretKey) {
        SM4_SECRET_KEY = sm4SecretKey;
    }


    @Value("${fc_config.auth.jwt.exp_days:1}")
    public void setJwtExpDays(Integer jwtExpDays) {
        JWT_EXP_DAYS = jwtExpDays;
    }


    @Value("${fc_config.auth.jwt.secret_key:reqh@3$+f.167490dasfgc}")
    public void setJwtSecretKey(String jwtSecretKey) {
        JWT_SECRET_KEY = jwtSecretKey;
    }



    @Value("${fc_config.auth.cookie.domain:#{null}}")
    public void setCookieDomain(String cookieDomain) {
        COOKIE_DOMAIN = cookieDomain;
    }

    @Value("${fc_config.auth.cookie.secure:#{false}}")
    public void setCookieSecure(Boolean secure) {
        COOKIE_SECURE = secure;
    }

    @Value("${fc_config.auth.cookie.token_name:FC_USER_TOKEN}")
    public static void setCookieTokenName(String cookieTokenName) {
        COOKIE_TOKEN_NAME = cookieTokenName;
    }

    @Value("${fc_config.auth.jwt.exp_days:1}")
    public void setCookieMaxAge(Integer jwtExpDays) {
        COOKIE_MAX_AGE = (jwtExpDays + 1) * 24 * 60 * 60;
    }

    @Value("${fc_config.auth.jwt.login_error_count_limit:5}")
    public void setLoginErrorCountLimit(Integer loginErrorCountLimit) {
        LOGIN_ERROR_COUNT_LIMIT = loginErrorCountLimit;
    }

    @Value("${spring.profiles.active}")
    public void setACTIVE(String active) {
        ACTIVE = active;
    }
}

