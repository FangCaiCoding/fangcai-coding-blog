package cn.fangcai.common.auth.interceptor;


import cn.fangcai.common.auth.FcAuthContext;
import cn.fangcai.common.auth.FcAuthUtil;
import cn.fangcai.common.auth.ano.FcCheckAuth;
import cn.fangcai.common.auth.ano.FcNotCheckLogin;
import cn.fangcai.common.auth.service.IAuthService;
import cn.fangcai.common.model.enums.AuthErrorCodeEnum;
import cn.fangcai.common.model.exception.FcBusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;


@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private IAuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        this.assertIsLogin(handlerMethod);
        this.assertAuthCode(handlerMethod);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        } finally {
            FcAuthContext.clearContext();
        }
    }

    private void assertAuthCode(HandlerMethod handlerMethod) {
        FcCheckAuth authCheck = handlerMethod.getBeanType().getAnnotation(FcCheckAuth.class);
        // 获取方法上的注解，方法优先级高于类
        FcCheckAuth methodAuthCheck = handlerMethod.getMethodAnnotation(FcCheckAuth.class);
        if (methodAuthCheck != null) {
            authCheck = methodAuthCheck;
        }
        if (authCheck == null) {
            return;
        }
        String needAuthCode = authCheck.value();
        Set<String> userAllAuthCodes = authService.listAuthCodeById(FcAuthContext.getUserId());
        if (!userAllAuthCodes.contains(needAuthCode)) {
            throw new FcBusinessException(AuthErrorCodeEnum.API_AUTH_ERROR);
        }
    }

    private void assertIsLogin(HandlerMethod handlerMethod) {
        // 获取类上的注解
        FcNotCheckLogin fcNotCheckLogin = handlerMethod.getBeanType().getAnnotation(FcNotCheckLogin.class);
        if (fcNotCheckLogin != null) {
            return;
        }
        // 获取方法上的注解
        fcNotCheckLogin = handlerMethod.getMethodAnnotation(FcNotCheckLogin.class);
        if (fcNotCheckLogin != null) {
            return;
        }
        FcAuthUtil.assertIsLogin();
    }
}
