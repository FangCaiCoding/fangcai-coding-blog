package cn.fangcai.common.spring.utils;


import cn.fangcai.common.model.exception.FcException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
public class SpringMVCUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringMVCUtil.class);

    /**
     * 获取当前会话的 request
     *
     * @return javax.servlet.http.HttpServletRequest
     *
     * @author wangxin
     * @date 2022-03-24 3:07 PM
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new FcException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Non-web context cannot get Request");
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取当前会话的 response
     *
     * @return javax.servlet.http.HttpServletResponse
     *
     * @author wangxin
     * @date 2022-03-24 3:07 PM
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new CloudLizardRuntimeException(StatusCodeEnum.INTERNAL_ERROR,
                    "Non-web context cannot get Response");
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
    public static String getClientIp() {
        HttpServletRequest request = getRequest();
        String ipAddress = "127.0.0.1";


        try {
            /**
             * apisix 默认携带真实ip的header
             *   第一个 是原始的访问 IP  第二个 是 代理的 转发的 IP
             *  header=[x-original-forwarded-for],value= 113.204.230.202, 172.20.2.0
             */
            AppConfig appConfig = SpringContextUtil.INSTANCE.getBean(AppConfig.class);
            assert appConfig != null;
            ipAddress = request.getHeader(appConfig.getClientIpHeader());
            if (StrUtil.isBlank(ipAddress)) {
                return ipAddress;
            }
            if (ipAddress.contains(",")) {
                ipAddress = ipAddress.split(",")[0];
            }
            if (!isIpv4(ipAddress) || Ipv4Util.isInnerIP(ipAddress)) {
                ipAddress = "127.0.0.1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
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
