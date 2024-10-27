package cn.fangcai.blog.consts;

import lombok.Getter;

/**
 * @author MouFangCai
 * @date 2024/10/27 16:52
 * @description
 */
@Getter
public enum WechatMsgTypeEnum {

    TEXT((byte) 1, "text"),

    IMAGE((byte) 2, "image"),
    ;

    private final Byte code;

    private final String wxFlag;

    WechatMsgTypeEnum(Byte code, String wxFlag) {
        this.code = code;
        this.wxFlag = wxFlag;
    }
}
