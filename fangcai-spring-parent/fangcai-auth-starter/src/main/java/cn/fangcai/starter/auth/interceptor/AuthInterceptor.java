package cn.fangcai.starter.auth.interceptor;


import cn.fangcai.starter.auth.FcAuthContext;
import cn.fangcai.starter.auth.FcAuthUtil;
import cn.fangcai.starter.auth.ano.FcCheckAuth;
import cn.fangcai.starter.auth.ano.FcNotCheckLogin;
import cn.fangcai.starter.auth.enums.AuthErrorCodeEnum;
import cn.fangcai.starter.auth.service.IAuthService;
import cn.fangcai.common.model.exception.FcBusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;


@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private IAuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        try {
            FcAuthContext.initContext();
        } catch (FcBusinessException exception) {
            log.debug("auth context init failed,{}", exception.toString());
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
        String[] needAuthCodeArr = authCheck.values();
        Set<String> needAuthCodeSet = Set.of(needAuthCodeArr);
        Set<String> userAllAuthCodes = authService.listAuthCodeById(FcAuthContext.getUserId());
        boolean isAuth;
        if (authCheck.type() == FcCheckAuth.Type.AND) {
            isAuth = userAllAuthCodes.containsAll(needAuthCodeSet);
        } else {
            isAuth = userAllAuthCodes.stream().anyMatch(needAuthCodeSet::contains);
        }
        if (!isAuth) {
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
        if (!FcAuthUtil.isLogin()) {
            throw new FcBusinessException(AuthErrorCodeEnum.USER_CONTEXT_IS_NULL);
        }
    }
}
