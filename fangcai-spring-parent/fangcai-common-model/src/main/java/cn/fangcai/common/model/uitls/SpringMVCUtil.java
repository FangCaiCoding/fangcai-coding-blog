package cn.fangcai.common.model.uitls;


import cn.fangcai.common.model.enums.FcErrorCodeEnum;
import cn.fangcai.common.model.exception.FcException;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author MouFangCai
 * @date 2024/8/22 23:09
 * @description
 */
@Slf4j
public class SpringMVCUtil {


    /**
     * 获取当前会话的 request
     *
     * @return javax.servlet.http.HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new FcException(FcErrorCodeEnum.INTERNAL_SERVER_ERROR, "Non-web context can‘t get Request");
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取当前会话的 response
     *
     * @return javax.servlet.http.HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new FcException(FcErrorCodeEnum.INTERNAL_SERVER_ERROR, "Non-web context can‘t get Response");
        }
        return servletRequestAttributes.getResponse();
    }


    /**
     * 判断当前是否处于 Web 上下文中
     *
     * @return request
     */
    public static boolean isWeb() {
        return RequestContextHolder.getRequestAttributes() != null;
    }


    /**
     * 获取 request header 的 origin
     *
     * @return
     */
    public static String getHeaderOrigin() {
        String origin = "";
        try {
            origin = getRequest().getHeader("Origin");
        } catch (Exception ignored) {

        }
        return origin;
    }

    /**
     * 获取用户真实ip
     *
     * @return
     */
    public static String getClientIp(String headerName) {
        HttpServletRequest request = getRequest();
        try {
            // 优先从指定的header头获取
            String ip = request.getHeader(headerName);
            if (StrUtil.isBlank(ip)) {
                ip = request.getHeader("x-forwarded-for");
            }
            if (StrUtil.isBlank(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StrUtil.isBlank(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (StrUtil.isBlank(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StrUtil.isBlank(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (StrUtil.isBlank(ip)) {
                ip = request.getRemoteAddr();
            }
            return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);
        } catch (Exception e) {
            log.error("getClientIp Error!", e);
        }
        return "unknown";
    }


    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     *
     * @return 第一个非unknown IP地址
     */
    private static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (isIpv4(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return StrUtil.subPre(ip, 256);
    }

    private static Boolean isIpv4(String strIp) {
        if (StrUtil.isBlank(strIp)) {
            return Boolean.FALSE;
        }
        final Matcher matcher = PatternPool.IPV4.matcher(strIp);
        return matcher.matches();
    }

    /**
     * 获取 Request body
     *
     * @return
     */
    public static String getRequestBody() {
        HttpServletRequest request = getRequest();
        String body = "";
        if (request instanceof ContentCachingRequestWrapper) {
            body = new String(((ContentCachingRequestWrapper) request).getContentAsByteArray(), StandardCharsets.UTF_8);
        }
        // Windows 环境获取的请求体换行符为 \r\n，与服务端生成的原文不一致，因此需要手动将 \r\n 替换为 \n
        return body.replaceAll("\r\n", "\n");
    }

    /**
     * 获取 Request body
     *
     * @return
     */
    public static String getRequestParam() {
        HttpServletRequest request = getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (CollUtil.isEmpty(parameterMap)) {
            return "";
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String paramName = entry.getKey();
            params.append(paramName).append("=");
            String[] paramValues = entry.getValue();
            for (String paramValue : paramValues) {
                params.append(paramValue).append(",");
            }
        }
        return params.toString();
    }

}
