package fc.common.auth.utils;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.json.JSONUtil;
import fc.common.model.common.exception.FcException;
import fc.common.model.config.GlobalParamCtx;
import fc.common.model.dto.UserTokenDto;
import fc.common.model.enums.FcErrorCodeEnum;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


/**
 * @author MouFangCai
 * @date 2024/8/19 22:16
 * @description
 */
@Slf4j
public class JWTUtil {


    private final static String JWT_CLAIM_NAME = "UserTokenDto";

    /**
     * 使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
     *
     * @param tokenDto
     * @return token
     */
    public static String createToken(UserTokenDto tokenDto) {
        if (tokenDto == null) {
            throw new FcException(FcErrorCodeEnum.JWT_CLAIM_IS_EMPTY);
        }
        SymmetricCrypto sm4 = SmUtil.sm4(GlobalParamCtx.SM4_SECRET_KEY.getBytes());
        String tokenStr = sm4.encryptHex(JSONUtil.toJsonStr(tokenDto));

        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("MxFamily", "user")
                .claim(JWT_CLAIM_NAME, tokenStr)
                .setSubject("NewUserJWT")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, getKeyInstance());
        setExpiration(builder);
        return builder.compact();
    }



    /**
     * 解析Token，同时也能验证Token，当验证失败返回null
     *
     * @param token
     * @return UserTokenDto
     */
    public static UserTokenDto parserAndValidToken(String token) {
        UserTokenDto dto;
        try {
            final Claims claims = Jwts.parser().setSigningKey(GlobalParamCtx.JWT_SECRET_KEY).parseClaimsJws(token).getBody();
            Object tokenObj = claims.get(JWT_CLAIM_NAME);
            SymmetricCrypto sm4 = SmUtil.sm4(GlobalParamCtx.SM4_SECRET_KEY.getBytes());
            String tokenStr = sm4.decryptStr(JSONUtil.toJsonStr(tokenObj),CharsetUtil.CHARSET_UTF_8);
            dto = JSONUtil.toBean(tokenStr, UserTokenDto.class);
        } catch (Exception e) {
            log.info("json web token verify failed,msg= {}", e.getMessage());
            throw new FcException(FcErrorCodeEnum.JWT_TOKEN_ERROR);
        }
        return dto;
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
                if (GlobalParamCtx.COOKIE_TOKEN_NAME.equals(cookie.getName())) {
                   // todo: 待实现
                }
            }
        }
    }



    /**
     * 该方法使用HS256算法和Secret:bankgl生成signKey
     *
     * @return
     */
    private static Key getKeyInstance() {
        // 获取算法HS256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(GlobalParamCtx.JWT_SECRET_KEY);
        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }



    private static void setExpiration(JwtBuilder builder) {
        // 添加Token过期时间
        long expiration = getExpiration();
        if (expiration >= 0) {
            long nowMillis = System.currentTimeMillis();
            long expMillis = nowMillis + expiration;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
    }


    /**
     * JWT_EXP_DAYS 天后的 JWT_EXP_TIME  时候 - 当前时间 = 过期时间
     *
     * @return 过期时间的毫秒值
     */
    private static long getExpiration() {
        LocalDate now = LocalDate.now();
        LocalDate expiration = now.plusDays(GlobalParamCtx.JWT_EXP_DAYS);
        String dateString = expiration.getYear() + "-" + expiration.getMonth().getValue() + "-"
                + expiration.getDayOfMonth() + " " + GlobalParamCtx.JWT_EXP_TIME;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ParsePosition position = new ParsePosition(0);
        Date future = dateFormat.parse(dateString, position);
        return future.getTime() - System.currentTimeMillis();
    }


}
