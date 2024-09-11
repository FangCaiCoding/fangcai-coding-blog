package cn.fangcai.common.spring.filter;

import cn.fangcai.common.spring.utils.TraceIdUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

/**
 * @author MouFangCai
 * @date 2024/9/11 22:41
 * @description
 */
public class RequestCachingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String traceId = request.getHeader(TraceIdUtil.TRACE_ID_HEADER_KEY);
            // 兼容从其他client调用的请求
            if (StrUtil.isNotBlank(traceId)) {
                TraceIdUtil.setTraceId(traceId);
            } else {
                TraceIdUtil.initTraceId();
            }
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
            filterChain.doFilter(wrappedRequest, response);
        } finally {
            TraceIdUtil.clearTraceId();
        }
    }
}