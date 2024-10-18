package cn.fangcai.blog.consts;

import lombok.Getter;

/**
 * @author MouFangCai
 * @date 2024/8/25 14:34
 * @description
 */
@Getter
public enum StatusEnum {

    UN_PUBLISHED(0, "未发布"),
    PUBLISHED(1, "已发布"),


    ;

    private final Byte code;
    private final String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code.byteValue();
        this.desc = desc;
    }

}
