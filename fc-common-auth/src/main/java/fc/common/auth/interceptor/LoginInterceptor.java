package fc.common.auth.interceptor;



import fc.common.auth.ano.NotNeedLogin;
import fc.common.auth.service.ILoginService;
import fc.common.model.common.exception.FcException;
import fc.common.model.enums.FcErrorCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
   private ILoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NotNeedLogin notNeedLogin= handlerMethod.getMethodAnnotation(NotNeedLogin.class);
        if (notNeedLogin != null) {
            return true;
        }
        if (! loginService.isLogin(request)) {
            throw new FcException(FcErrorCodeEnum.USER_NOT_LOGIN);
        }
        return true;
    }
}
