package cn.fangcai.common.auth.interceptor;


import cn.fangcai.common.auth.UserContext;
import cn.fangcai.common.auth.ano.NotNeedLogin;
import cn.fangcai.common.auth.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private IAuthService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        NotNeedLogin notNeedLogin = handlerMethod.getMethodAnnotation(NotNeedLogin.class);
        if (notNeedLogin != null) {
            return true;
        }

        this.assertIsLogin();


        return true;
    }

    private void assertIsLogin(){
        UserContext.initContext();
    }
}
