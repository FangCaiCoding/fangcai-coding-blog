package cn.fangcai.starter.auth;

import cn.fangcai.starter.auth.dto.UserAuthInfo;
import cn.fangcai.starter.auth.dto.UserTokenDto;
import cn.fangcai.starter.auth.utils.LoginHttpUtil;
import cn.hutool.core.lang.UUID;

/**
 * @author MouFangCai
 * @date 2024/8/22 22:57
 * @description 授权工具类
 */
public class FcAuthUtil {


    public static void login(Object userId) {
        LoginHttpUtil.setLoginSession(new UserTokenDto(userId, UUID.fastUUID().toString(true)));
    }

    public static void logout() {
        LoginHttpUtil.delLoginSession();
    }

    public static Boolean isLogin() {
        UserAuthInfo authInfo = FcAuthContext.getAuthInfo();
        return authInfo!= null;
    }


}
