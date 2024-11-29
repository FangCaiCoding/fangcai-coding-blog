package cn.fangcai.starter.auth.utils;

import cn.fangcai.common.model.uitls.SpringMVCUtil;
import cn.fangcai.starter.auth.config.AuthProperties;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author MouFangCai
 * @date 2024/11/29 23:57
 * @description
 */
@Slf4j
public class CookieUtil {


    public static String getCookie(String key) {
        HttpServletRequest request = SpringMVCUtil.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie.getValue();
            }
        }
        return request.getHeader(key);
    }


    public static Cookie createCookie(String cookieName,
                                      String token,
                                      Integer maxAge) {
        HttpServletRequest request = SpringMVCUtil.getRequest();
        HttpServletResponse response = SpringMVCUtil.getResponse();

        Cookie cookie = new Cookie(cookieName, token);
        cookie.setMaxAge(maxAge == null ? AuthProperties.COOKIE_MAX_AGE : maxAge);

        // 设置了HttpOnly属性，那么通过js脚本将无法读取到cookie信息，这样能有效的防止XSS攻击
        cookie.setHttpOnly(true);
        cookie.setSecure(AuthProperties.COOKIE_SECURE);
        String host = request.getServerName();
        log.debug("login------------host:" + host);
        cookie.setDomain(host);
        if (StrUtil.isNotEmpty(AuthProperties.COOKIE_DOMAIN)) {
            cookie.setDomain(AuthProperties.COOKIE_DOMAIN);
        }
        cookie.setPath("/");
        response.addCookie(cookie);
        /**
         * Chrome 51 开始，浏览器的 Cookie 新增加了一个SameSite属性，用来防止 CSRF 攻击和用户追踪。
         * Strict 完全禁止第三方 Cookie，跨站点时，任何情况下都不会发送 Cookie。
         * Lax 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
         * None 没有限制。前提是必须同时设置Secure属性（Cookie 只能通过 HTTPS 协议发送），否则无效。
         */
        response.setHeader("Set-Cookie", response.getHeader("Set-Cookie") + "; SameSite=Lax");

        return cookie;
    }

    public static int deleteCookieByName(String cookieName) {
        HttpServletRequest request = SpringMVCUtil.getRequest();
        HttpServletResponse response = SpringMVCUtil.getResponse();
        int delCount = 0;
        String host = request.getServerName();
        log.debug("logout------------host:" + host);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    FcJWTUtil.invalidToken(cookie.getValue());
                    cookie.setDomain(host);
                    cookie.setValue(null);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    delCount++;
                }
            }
        }
        return delCount;
    }

}
