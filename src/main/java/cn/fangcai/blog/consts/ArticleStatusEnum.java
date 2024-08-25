package cn.fangcai.blog.consts;

/**
 * @author MouFangCai
 * @date 2024/8/25 14:34
 * @description
 */
public enum ArticleStatusEnum {

    UN_PUBLISHED(0, "未发布"),
    PUBLISHED(1, "已发布"),


    ;

    private Byte code;
    private String desc;

    ArticleStatusEnum(Integer code, String desc) {
        this.code = code.byteValue();
        this.desc = desc;
    }

    public Byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
