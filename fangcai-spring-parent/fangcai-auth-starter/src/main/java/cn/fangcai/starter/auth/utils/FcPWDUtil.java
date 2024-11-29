package cn.fangcai.starter.auth.utils;

import cn.fangcai.starter.auth.config.AuthProperties;
import cn.fangcai.common.model.enums.FcErrorCodeEnum;
import cn.fangcai.common.model.exception.FcException;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.SM3;
import cn.hutool.crypto.symmetric.SM4;

/**
 * @author MouFangCai
 * @date 2024/8/24 23:26
 * @description 密码工具类 slat+sm4+随机次数sm3
 */
public class FcPWDUtil {
    private static final SM4 SM4 = new SM4(AuthProperties.PWD_SM4_KEY.getBytes());

    private static final String SPLIT = ":";

    /**
     * 加密
     * 将 userId 作为固定盐值
     *
     * @param userId
     * @param pwd
     *
     * @return
     */
    public static String encrypt(String userId, String pwd) {
        if (StrUtil.isBlank(userId) || StrUtil.isBlank(pwd)) {
            throw new FcException(FcErrorCodeEnum.BAD_REQUEST, "UserId Or Pwd is Blank");
        }
        int sm3Ct = RandomUtil.randomInt(10, 50);
        String salt = RandomUtil.randomString(8);
        return encrypt(userId, salt, sm3Ct, pwd);
    }

    /**
     * 校验密码
     * 思路：先根据 userId + encryptPwd 解析得到盐值等信息，执行加密算法，然后将输入的密文与加密后的密码进行比较
     *
     * @param userId
     * @param pwd
     * @param encryptPwd
     *
     * @return
     */
    public static Boolean checkPwd(String userId, String pwd, String encryptPwd) {
        if (StrUtil.isBlank(userId) || StrUtil.isBlank(pwd) || StrUtil.isBlank(encryptPwd)) {
            throw new FcException(FcErrorCodeEnum.BAD_REQUEST, "UserId Or Pwd Or EncryptPwd is Blank");
        }
        String[] split = encryptPwd.split(SPLIT);
        if (split.length != 3) {
            throw new FcException(FcErrorCodeEnum.BAD_REQUEST, "EncryptPwd Format Error");
        }
        String salt = HexUtil.decodeHexStr(split[0]);
        int sm3Ct = Integer.parseInt(HexUtil.decodeHexStr(split[1]));
        String encrypted = encrypt(userId, salt, sm3Ct, pwd);
        return encrypted.equals(encryptPwd);
    }


    private static String encrypt(String userId, String salt, int sm3Ct, String pwd) {

        String tempPwd = SM4.encryptHex(pwd);
        String finalSalt = userId + salt;
        SM3 sm3 = SmUtil.sm3WithSalt(finalSalt.getBytes());
        for (int i = 0; i < sm3Ct; i++) {
            tempPwd = sm3.digestHex(tempPwd);
        }
        return HexUtil.encodeHexStr(salt) + SPLIT + HexUtil.encodeHexStr(sm3Ct + "") + SPLIT + tempPwd;
    }

    public static void main(String[] args) {
        System.out.println(encrypt("1", "123456"));
        System.out.println(checkPwd("2", "123456", encrypt("1", "123456")));
    }
}
