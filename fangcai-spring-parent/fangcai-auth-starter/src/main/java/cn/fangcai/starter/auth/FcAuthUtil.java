package cn.fangcai.starter.auth;

import cn.fangcai.starter.auth.config.AuthProperties;
import cn.fangcai.starter.auth.dto.UserAuthInfo;
import cn.fangcai.starter.auth.dto.UserTokenDto;
import cn.fangcai.starter.auth.utils.CookieUtil;
import cn.fangcai.starter.auth.utils.FcJWTUtil;
import cn.fangcai.starter.auth.utils.LoginHttpUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

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
        return authInfo.getIsLogin();
    }

    /**
     * 初始化客户端，若不存在
     */
    public static void initClientIfAbsent() {
        String clientId = FcClientContext.getClientId();
        // 若不存在，则初始化一个长期有效的clientID
        if (StrUtil.isBlank(clientId)) {
            String clientIdToken = FcJWTUtil.createToken(IdUtil.getSnowflakeNextIdStr());
            CookieUtil.createCookie(AuthProperties.CLIENT_COOKIE_NAME, clientIdToken, Integer.MAX_VALUE);
        }
    }


}
