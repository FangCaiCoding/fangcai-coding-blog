package cn.fangcai.blog.consts;

import lombok.Getter;

/**
 * @author MouFangCai
 * @date 2024/10/27 17:30
 * @description 文章阅读限制类型枚举
 */
@Getter
public enum ReadLimitTypeEnum {

    NO_LIMIT((byte) 0, "不限制"),
    
    LOGIN_REQUIRED((byte) 1, "需登录"),
    
    VIP_REQUIRED((byte) 2, "需vip"),
    ;

    private final Byte code;
    
    private final String desc;

    ReadLimitTypeEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}