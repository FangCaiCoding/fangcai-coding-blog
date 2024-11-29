package cn.fangcai.starter.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author MouFangCai
 * @date 2021/4/22 12:23
 * @description 全局变量 上下文
 */
@Component
public class AuthProperties {


    /**
     * 客户端IP的 header
     */
    public static String CLIENT_IP_HEADER;


    /**
     * 客户端 cookie 名
     */
    public static String CLIENT_COOKIE_NAME;


    /**
     * 密码 加密的 sm4 key
     */
    public static String PWD_SM4_KEY;

    /**
     * 每日登录错误总次数限制
     */
    public static Integer LOGIN_ERROR_COUNT_LIMIT;

    /**
     * 鉴权的  cookie 和 header 名
     */
    public static String AUTH_TOKEN_NAME;



    public static String JWT_SM4_KEY;
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
     * cookie 过期时间：单位 s【根据 JWT_EXP_DAYS 计算得出】
     */
    public static Integer COOKIE_MAX_AGE;

    /**
     * cookie  SECURE 属性：设置为true，则只能在https中传递
     */
    public static Boolean COOKIE_SECURE;


    /**
     * 环境标识
     */
    public static String ACTIVE;


    /**
     * fangcai.auth 级别的配置
     */
    @Value("${fangcai.auth.clientIpHeader:X-Forwarded-For}")
    public void setClientIpHeader(String clientIpHeader) {
        CLIENT_IP_HEADER = clientIpHeader;
    }

    @Value("${fangcai.auth.clientCookieName:FC_CLIENT_ID}")
    public void setClientCookieName(String clientCookieName) {
        CLIENT_COOKIE_NAME = clientCookieName;
    }



    @Value("${fangcai.auth.pwd_sm4_key:@qwr0987+dMDdf89}")
    public void setPwdSm4Key(String pwdSm4Key) {
        PWD_SM4_KEY = pwdSm4Key;
    }

    @Value("${fangcai.auth.login_error_count_limit:5}")
    public void setLoginErrorCountLimit(Integer loginErrorCountLimit) {
        LOGIN_ERROR_COUNT_LIMIT = loginErrorCountLimit;
    }

    @Value("${fangcai.auth.token_name:FC_USER_TOKEN}")
    public void setTokenName(String tokenName) {
        AUTH_TOKEN_NAME = tokenName;
    }


    /**
     * fangcai.auth.jwt jwt相关的配置
     */
    /**
     * @value 注解添加默认值
     * null值用#{}包裹，如果不用默认解析成字符串
     */
    @Value("${fangcai.auth.jwt.sm4_key:@qwr0987+d23df89}")
    public void setSm4SecretKey(String sm4SecretKey) {
        JWT_SM4_KEY = sm4SecretKey;
    }

    @Value("${fangcai.auth.jwt.exp_days:30}")
    public void setJwtExpDays(Integer jwtExpDays) {
        JWT_EXP_DAYS = jwtExpDays;
    }


    @Value("${fangcai.auth.jwt.secret_key:reqh@3$+f.167490dasfgc}")
    public void setJwtSecretKey(String jwtSecretKey) {
        JWT_SECRET_KEY = jwtSecretKey;
    }


    @Value("${fangcai.auth.jwt.exp_days:30}")
    public void setCookieMaxAge(Integer jwtExpDays) {
        COOKIE_MAX_AGE = (jwtExpDays + 1) * 24 * 60 * 60;
    }

    /**
     * fangcai.auth.cookie cookie相关的配置
     */
    @Value("${fangcai.auth.cookie.domain:#{null}}")
    public void setCookieDomain(String cookieDomain) {
        COOKIE_DOMAIN = cookieDomain;
    }

    @Value("${fangcai.auth.cookie.secure:#{false}}")
    public void setCookieSecure(Boolean secure) {
        COOKIE_SECURE = secure;
    }


    @Value("${spring.profiles.active}")
    public void setACTIVE(String active) {
        ACTIVE = active;
    }
}

