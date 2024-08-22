package cn.fangcai.common.auth;

import cn.fangcai.common.auth.dto.UserAuthInfo;
import cn.fangcai.common.auth.dto.UserTokenDto;
import cn.fangcai.common.auth.service.IAuthService;
import cn.fangcai.common.auth.utils.LoginHttpUtil;
import cn.fangcai.common.model.enums.FcErrorCodeEnum;
import cn.fangcai.common.model.exception.FcException;

/**
 * @author MouFangCai
 * @date 2024/8/19 22:16
 * @description
 */
public class UserContext extends ThreadLocal<UserAuthInfo> {
    private static final ThreadLocal<UserAuthInfo> USER_INFO = new ThreadLocal<>();


    public static void initContext() {
        UserTokenDto userToken = LoginHttpUtil.getUserToken();
        //TODO : by mfc on 2024/8/21
        IAuthService loginService;
        UserAuthInfo authInfo = loginService.getById(userToken.getUserId());
        if(authInfo == null) {
            throw new FcException(FcErrorCodeEnum.AUTH_INFO_INIT_FAIL);
        }
        USER_INFO.set(authInfo);
    }

    public static void clearContext() {
        USER_INFO.remove();
    }

    public static UserAuthInfo getAuthInfo() {
        UserAuthInfo authInfo = USER_INFO.get();
        if (authInfo == null) {
            throw new FcException(FcErrorCodeEnum.USER_CONTEXT_IS_NULL);
        }
        return authInfo;
    }

    public static


}
