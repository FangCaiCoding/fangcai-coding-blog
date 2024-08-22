package cn.fangcai.common.spring.utils;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.util.UUID;


/**
 * @author MouFangCai
 * @date 2024/8/22 23:09
 * @description 全局 log traceId 工具类
 */
public class TraceIdUtil {
    private static final String MDC_KEY = "traceId";

    public static final String TRACE_ID_HEADER_KEY = "fangcai-trace-id-header";

    public static String initTraceId() {
        String traceId = UUID.randomUUID().toString();
        MDC.put(MDC_KEY, traceId);
        return traceId;
    }

    public static void setTraceId(String traceId) {
        MDC.put(MDC_KEY, traceId);
    }

    public static String getOrInitTraceId() {
        String traceId = MDC.get(MDC_KEY);
        if (StringUtils.hasText(traceId)) {
            traceId = initTraceId();
        }
        return traceId;
    }

    public static void clearTraceId() {
        MDC.remove(MDC_KEY);
    }
}

