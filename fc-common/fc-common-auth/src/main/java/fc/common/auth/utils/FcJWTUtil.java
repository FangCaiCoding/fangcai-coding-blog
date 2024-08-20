package fc.common.auth.utils;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import fc.common.auth.config.AuthProperties;
import fc.common.auth.dto.UserTokenDto;
import fc.common.model.enums.FcErrorCodeEnum;
import fc.common.model.exception.FcException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * @author MouFangCai
 * @date 2024/8/19 22:16
 * @description
 */
@Slf4j
public class FcJWTUtil {


    private final static String JWT_CLAIM_NAME = "UserToken";
    private static final JWTSigner jwtSigner = JWTSignerUtil.hs512(AuthProperties.JWT_SECRET_KEY.getBytes());
    private static final SM4 sm4 = SmUtil.sm4(AuthProperties.SM4_SECRET_KEY.getBytes());

    /**
     * 使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
     *
     * @param userToken
     *
     * @return token
     */
    public static String createToken(UserTokenDto userToken) {
        if (userToken == null) {
            throw new FcException(FcErrorCodeEnum.JWT_CLAIM_IS_EMPTY);
        }
        String tokenStr = sm4.encryptHex(JSONUtil.toJsonStr(userToken));
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(AuthProperties.JWT_EXP_DAYS);
        Date expiresAt = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return JWT.create()
                .setPayload(JWT_CLAIM_NAME, tokenStr)
                .setSigner(jwtSigner)
                .setExpiresAt(expiresAt)
                .setIssuedAt(new Date())
                .sign();
    }


    /**
     * 解析Token，同时也能验证Token，当验证失败返回null
     *
     * @param token
     *
     * @return UserTokenDto
     */
    public static UserTokenDto parserToken(String token) {
        boolean verify = JWTUtil.verify(token, jwtSigner);
        if (!verify) {
            throw new FcException(FcErrorCodeEnum.JWT_TOKEN_ERROR);
        }
        String tokenEncryptStr = (String) JWTUtil.parseToken(token).getPayload(JWT_CLAIM_NAME);
        String tokenStr = sm4.decryptStr(JSONUtil.toJsonStr(tokenEncryptStr), CharsetUtil.CHARSET_UTF_8);
        return JSONUtil.toBean(tokenStr, UserTokenDto.class);
    }


    /**
     * 根据 cookie  使 token 时效
     *
     * @param request
     */
    public static void invalidToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (AuthProperties.COOKIE_TOKEN_NAME.equals(cookie.getName())) {
                    // todo: 待实现
                }
            }
        }
    }


}
