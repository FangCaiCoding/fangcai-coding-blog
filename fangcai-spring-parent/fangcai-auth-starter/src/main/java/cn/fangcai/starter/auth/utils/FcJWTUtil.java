package cn.fangcai.starter.auth.utils;


import cn.fangcai.common.model.exception.FcBusinessException;
import cn.fangcai.common.model.exception.FcException;
import cn.fangcai.starter.auth.config.AuthProperties;
import cn.fangcai.starter.auth.enums.AuthErrorCodeEnum;
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


    private final static String JWT_USER_CLAIM_NAME = "UserToken";

    private static final SM4 sm4 = SmUtil.sm4(AuthProperties.JWT_SM4_KEY.getBytes());

    /**
     * 使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
     *
     * @param jwtClaim 负载内容
     *
     * @return token
     */
    public static String createToken(Object jwtClaim) {
        if (jwtClaim == null) {
            throw new FcException(AuthErrorCodeEnum.JWT_CLAIM_IS_EMPTY);
        }
        String tokenStr = sm4.encryptHex(JSONUtil.toJsonStr(jwtClaim));
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(AuthProperties.JWT_EXP_DAYS);
        Date expiresAt = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        JWTSigner jwtSigner = JWTSignerUtil.hs512(AuthProperties.JWT_SECRET_KEY.getBytes());
        return JWT.create()
                .setPayload(JWT_USER_CLAIM_NAME, tokenStr)
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
    public static <T> T parseToken(String token, Class<T> targetClass) throws FcBusinessException {
        boolean verify = false;
        try {
            JWTSigner jwtSigner = JWTSignerUtil.hs512(AuthProperties.JWT_SECRET_KEY.getBytes());
            verify = JWTUtil.verify(token, jwtSigner);

        } catch (Exception e) {
            log.warn("JWTUtil.verify Exception,errorMsg:{}", e.getMessage());
        }
        if (!verify) {
            throw new FcBusinessException(AuthErrorCodeEnum.JWT_TOKEN_ERROR);
        }
        String tokenEncryptStr = (String) JWTUtil.parseToken(token).getPayload(JWT_USER_CLAIM_NAME);
        String tokenStr = sm4.decryptStr(JSONUtil.toJsonStr(tokenEncryptStr), CharsetUtil.CHARSET_UTF_8);
        if (JSONUtil.isTypeJSON(tokenStr)) {
            return JSONUtil.toBean(tokenStr, targetClass);
        }
        return (T) tokenStr;
    }


    /**
     * 根据 cookie  使 token 时效
     *
     * @param token
     */
    public static void invalidToken(String token) {
        // TODO : by mfc on 2024/8/21
    }


}
