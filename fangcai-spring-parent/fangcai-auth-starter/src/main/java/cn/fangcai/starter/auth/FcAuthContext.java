package cn.fangcai.starter.auth;

import cn.fangcai.common.model.exception.FcBusinessException;
import cn.fangcai.common.model.uitls.SpringMVCUtil;
import cn.fangcai.starter.auth.dto.UserAuthInfo;
import cn.fangcai.starter.auth.dto.UserTokenDto;
import cn.fangcai.starter.auth.service.IAuthService;
import cn.fangcai.starter.auth.utils.LoginHttpUtil;
import cn.hutool.extra.spring.SpringUtil;

/**
 * @author MouFangCai
 * @date 2024/8/19 22:16
 * @description 授权相关上下文
 */
public class FcAuthContext {
    private static final ThreadLocal<Object> USER_INFO = new ThreadLocal<>();


    public static void initContext() throws FcBusinessException {
        UserTokenDto userToken = LoginHttpUtil.getUserToken();
        IAuthService authService = SpringUtil.getBean(IAuthService.class);
        Object authInfo = authService.getById(userToken.getUserId());
        USER_INFO.set(authInfo);
    }

    @SuppressWarnings("unchecked")
    public static <T extends UserAuthInfo> T getAuthInfo() {
        Object authInfo = USER_INFO.get();
        if (authInfo == null) {
            return null;
        }
        return (T) authInfo;
    }

    public static Object getUserId() {
        UserAuthInfo authInfo = getAuthInfo();
        return authInfo.getId();
    }

    public static String getUserIdAsStr() {
        UserAuthInfo authInfo = getAuthInfo();
        return String.valueOf(authInfo.getId());
    }

    public static Long getUserIdAsLong() {
        return Long.parseLong(getUserIdAsStr());
    }

    public static Integer getUserIdAsInt() {
        return Integer.parseInt(getUserIdAsStr());
    }

    public static void clearContext() {
        USER_INFO.remove();
    }


}
