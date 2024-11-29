package cn.fangcai.starter.auth.utils;


import cn.fangcai.starter.auth.config.AuthProperties;
import cn.fangcai.starter.auth.dto.UserTokenDto;
import cn.fangcai.starter.auth.enums.AuthErrorCodeEnum;
import cn.fangcai.common.model.exception.FcBusinessException;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * @author MouFangCai
 * @date 2024/8/19 22:16
 * @description
 */
@Slf4j
public class LoginHttpUtil {

    public static void setLoginSession(UserTokenDto userTokenDto) {
        setLoginCookie(userTokenDto);
    }

    public static UserTokenDto getUserToken() throws FcBusinessException {
        String token = CookieUtil.getCookie(AuthProperties.AUTH_TOKEN_NAME);
        if (StrUtil.isBlank(token)) {
            throw new FcBusinessException(AuthErrorCodeEnum.USER_NOT_LOGIN);
        }
        return FcJWTUtil.parserToken(token);
    }


    public static void delLoginSession() {
        CookieUtil.deleteCookieByName(AuthProperties.AUTH_TOKEN_NAME);
    }


    private static void setLoginCookie(UserTokenDto tokenDto) {
        String token = FcJWTUtil.createToken(tokenDto);
        CookieUtil.createCookie(AuthProperties.AUTH_TOKEN_NAME, token, AuthProperties.COOKIE_MAX_AGE);
    }


}
