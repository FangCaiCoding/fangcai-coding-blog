package cn.fangcai.common.auth.utils;


import cn.fangcai.common.auth.config.AuthProperties;
import cn.fangcai.common.auth.dto.UserTokenDto;
import cn.fangcai.common.model.enums.AuthErrorCodeEnum;
import cn.fangcai.common.model.exception.FcException;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
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
    private static final SM4 sm4 = SmUtil.sm4(AuthProperties.JWT_SM4_KEY.getBytes());

    /**
     * 使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
     *
     * @param userToken
     *
     * @return token
     */
    public static String createToken(UserTokenDto userToken) {
        if (userToken == null) {
            throw new FcException(AuthErrorCodeEnum.JWT_CLAIM_IS_EMPTY);
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
            throw new FcException(AuthErrorCodeEnum.JWT_TOKEN_ERROR);
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
    public static void invalidToken(String token) {
        boolean verify = JWTUtil.verify(token, jwtSigner);
        if (!verify) {
            return;
        }
        String tokenEncryptStr = (String) JWTUtil.parseToken(token).getPayload(JWT_CLAIM_NAME);
        String tokenStr = sm4.decryptStr(JSONUtil.toJsonStr(tokenEncryptStr), CharsetUtil.CHARSET_UTF_8);
        UserTokenDto tokenDto = JSONUtil.toBean(tokenStr, UserTokenDto.class);
        //TODO : by mfc on 2024/8/21
    }


}
